package parkinglotprocessor.processor;

import parkinglotprocessor.model.GeoLocation;
import parkinglotprocessor.model.ParkingLotCsvRows;

import java.util.*;

public class UniqueParkingLotsProcessor {

    public List<ParkingLotCsvRows> process(List<ParkingLotCsvRows> parkingLotCsvRows) {
        Map<GeoLocation, ParkingLotCsvRows> parkingLotsByGeoLocation = new HashMap<>();

        parkingLotCsvRows.forEach(parkingLotCsvRow ->
                parkingLotsByGeoLocation.put(new GeoLocation(parkingLotCsvRow.getLatitude(),
                        parkingLotCsvRow.getLongitude()), parkingLotCsvRow));

        return new ArrayList<>(parkingLotsByGeoLocation.values());
    }
}
