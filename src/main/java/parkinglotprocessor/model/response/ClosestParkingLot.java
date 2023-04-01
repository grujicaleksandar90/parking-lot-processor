package parkinglotprocessor.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import parkinglotprocessor.model.dao.ParkingLot;

@Data
@AllArgsConstructor
public class ClosestParkingLot {

    private double distanceInKm;
    private ParkingLot parkingLot;
}
