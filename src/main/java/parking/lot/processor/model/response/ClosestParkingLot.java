package parking.lot.processor.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import parking.lot.processor.model.dao.ParkingLot;

@Data
@AllArgsConstructor
public class ClosestParkingLot {

    private double distanceInKm;
    private ParkingLot parkingLot;
}
