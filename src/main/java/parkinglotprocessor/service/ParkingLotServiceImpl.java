package parkinglotprocessor.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.elasticsearch.core.query.GeoDistanceOrder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import parkinglotprocessor.config.ParkingLotProcessorConfigurationProperties;
import parkinglotprocessor.exception.InvalidCoordinatesException;
import parkinglotprocessor.exception.ParkingLotNotFoundException;
import parkinglotprocessor.model.ParkingLotCsvRows;
import parkinglotprocessor.model.dao.ParkingLot;
import parkinglotprocessor.model.response.ClosestParkingLot;
import parkinglotprocessor.model.response.ParkingLotScore;
import parkinglotprocessor.repository.ParkingLotRepository;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParkingLotServiceImpl implements ParkingLotService {

    private static final String SCORING_MESSAGE = "Geo location entered has a score od %s, meaning there are %s parking lots available in the 1km radius";

    private final ParkingLotRepository repository;
    private final ParkingLotProcessorConfigurationProperties properties;

    @Override
    public void saveAll(List<ParkingLotCsvRows> parkingLotCsvRows) {
        log.info("Saving parking lot records into repository.");

        repository.saveAll(parkingLotCsvRows.stream()
                .map(this::transformToDao)
                .toList());
    }

    @Override
    public ClosestParkingLot findClosest(double latitude, double longitude) {
        validateCoordinates(latitude, longitude);
        log.info("Finding closest parking lot for latitude ({}) and longitude ({})", latitude, longitude);
        final List<SearchHit<ParkingLot>> closestParkingLots = repository.findTopBy(
                Sort.by(new GeoDistanceOrder("location", new GeoPoint(latitude, longitude)).withUnit("km")));

        if (!CollectionUtils.isEmpty(closestParkingLots)) {
            return new ClosestParkingLot(Double.parseDouble((String) closestParkingLots.get(0).getSortValues().get(0)),
                    closestParkingLots.get(0).getContent());
        } else {
            throw new ParkingLotNotFoundException(
                    String.format("Closest Parking Lot not found for the given latitude (%s) and longitude (%s)", latitude, longitude));
        }
    }

    @Override
    public ParkingLotScore calculateScore(double latitude, double longitude) {
        validateCoordinates(latitude, longitude);
        final int score = calculateScore(getNumberOfNearByParkingLots(latitude, longitude), properties.getParkingLotScoring());

        return new ParkingLotScore(score, String.format(SCORING_MESSAGE, score, properties.getParkingLotScoring().get(score)));
    }

    private long getNumberOfNearByParkingLots(double latitude, double longitude) {
        log.info("Searching for number of parking lots withing 1km radius, based on the coordinates (latitude = {}, longitude = {})",
                latitude, longitude);

        return repository.countWithin(new GeoPoint(latitude, longitude), 1.0, "km");
    }

    private int calculateScore(Long numberOfParkingLots, Map<Integer, List<Integer>> parkingLotScoring) {
        return parkingLotScoring.entrySet().stream()
                .filter(entry -> entry.getValue().contains(numberOfParkingLots.intValue()))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(5);
    }

    private ParkingLot transformToDao(ParkingLotCsvRows parkingLotCsvRows) {
        return ParkingLot.builder()
                .name(parkingLotCsvRows.getName())
                .location(new GeoPoint(parkingLotCsvRows.getLatitude(), parkingLotCsvRows.getLongitude()))
                .year(parkingLotCsvRows.getYear())
                .type(parkingLotCsvRows.getType())
                .build();
    }

    private void validateCoordinates(double latitude, double longitude) {
        if((latitude <= -90 || latitude >= 90) || (longitude <= -180 || longitude >= 180)) {
            throw new InvalidCoordinatesException("Coordinates entered are invalid!");
        }
    }
}
