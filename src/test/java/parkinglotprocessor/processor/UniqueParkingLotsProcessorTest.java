package parkinglotprocessor.processor;

import org.junit.Test;

import parkinglotprocessor.model.ParkingLotCsvRows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UniqueParkingLotsProcessorTest {

    @Test
    public void processTest() {
        final List<ParkingLotCsvRows> parkingLotCsvRows = new ArrayList<>(new UniqueParkingLotsProcessor().process(buildListOfParkingLotCsvRows()));

        assertEquals(2, parkingLotCsvRows.size());
    }

    private List<ParkingLotCsvRows> buildListOfParkingLotCsvRows() {
        final ParkingLotCsvRows parkingLotCsvRows1 = new ParkingLotCsvRows();
        parkingLotCsvRows1.setLatitude(34.1022682);
        parkingLotCsvRows1.setLongitude(-118.3403664);
        parkingLotCsvRows1.setName("Parking Lot 1");

        final ParkingLotCsvRows parkingLotCsvRows2 = new ParkingLotCsvRows();
        parkingLotCsvRows2.setLatitude(34.1022682);
        parkingLotCsvRows2.setLongitude(-118.3403664);
        parkingLotCsvRows2.setName("Parking Lot 1");

        final ParkingLotCsvRows parkingLotCsvRows3 = new ParkingLotCsvRows();
        parkingLotCsvRows3.setLatitude(39.8972682);
        parkingLotCsvRows3.setLongitude(-104.1907664);
        parkingLotCsvRows3.setName("Parking Lot 2");

        return Arrays.asList(parkingLotCsvRows1, parkingLotCsvRows2, parkingLotCsvRows3);
    }

}
