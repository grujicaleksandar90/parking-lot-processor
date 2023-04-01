package parkinglotprocessor.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import parkinglotprocessor.config.ParkingLotProcessorConfigurationProperties;
import parkinglotprocessor.exception.InvalidCoordinatesException;
import parkinglotprocessor.exception.ParkingLotNotFoundException;
import parkinglotprocessor.model.dao.ParkingLot;
import parkinglotprocessor.model.response.ClosestParkingLot;
import parkinglotprocessor.model.response.ParkingLotScore;
import parkinglotprocessor.repository.ParkingLotRepository;

import java.util.*;

public class ParkingLotServiceImplTest {

    private final ParkingLotRepository repository = mock(ParkingLotRepository.class);
    private final ParkingLotProcessorConfigurationProperties properties = mock(ParkingLotProcessorConfigurationProperties.class);

    private final ParkingLotServiceImpl parkingLotService = new ParkingLotServiceImpl(repository, properties);

    @Test
    public void saveAllTest() {
        parkingLotService.saveAll(new ArrayList<>());

        when(repository.saveAll(any())).thenReturn(null);
        verify(repository, times(1)).saveAll(any());
    }

    @Test
    public void findClosest() {
        final SearchHit<ParkingLot> searchHit = mock(SearchHit.class);
        final ArrayList<Object> sortValues = mock(ArrayList.class);
        final List<SearchHit<ParkingLot>> parkingLots = Collections.singletonList(searchHit);
        final ClosestParkingLot expectedResult = new ClosestParkingLot(5.1920931, buildParkingLot());

        when(searchHit.getSortValues()).thenReturn(sortValues);
        when(sortValues.get(0)).thenReturn("5.1920931");
        when(searchHit.getContent()).thenReturn(buildParkingLot());
        when(repository.findTopBy(any())).thenReturn(parkingLots);

        final ClosestParkingLot actualResult = parkingLotService.findClosest(44.9871, 20.9087);

        verify(repository, times(1)).findTopBy(any());
        assertEquals(expectedResult, actualResult);
    }

    @Test(expected = ParkingLotNotFoundException.class)
    public void findClosestWithNotFoundException() {
        when(repository.findTopBy(any())).thenReturn(Collections.emptyList());
        parkingLotService.findClosest(44.9871, 20.9087);
    }

    @Test(expected = InvalidCoordinatesException.class)
    public void findClosestWithInvalidCoordinatesException() {
        parkingLotService.findClosest(-91.8125, 20.4612);
    }

    @Test
    public void calculateScore() {
        final Map<Integer, List<Integer>> parkingLotScoring = new HashMap<>();
        parkingLotScoring.put(0, List.of(0));
        parkingLotScoring.put(1, Arrays.asList(1,2));
        parkingLotScoring.put(2, Arrays.asList(3,4));

        when(repository.countWithin(any(), any(), any())).thenReturn(2L);
        when(properties.getParkingLotScoring()).thenReturn(parkingLotScoring);

        ParkingLotScore parkingLotScore = parkingLotService.calculateScore(-89.8125, 20.4612);

        assertEquals(1, parkingLotScore.getScore());
    }

    @Test(expected = InvalidCoordinatesException.class)
    public void calculateScoreWithInvalidCoordinatesException() {
        parkingLotService.calculateScore(-89.8125, 181.4612);
    }

    private ParkingLot buildParkingLot() {
        return ParkingLot.builder()
                .name("Parking Lot Empire")
                .location(new GeoPoint(44.9871, 20.9087))
                .year("2023")
                .type("Parking")
                .build();
    }
}