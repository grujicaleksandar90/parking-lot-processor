package parking.lot.processor.processor;

import parking.lot.processor.model.GeoLocation;
import parking.lot.processor.model.ParkingLotCsvRows;

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
