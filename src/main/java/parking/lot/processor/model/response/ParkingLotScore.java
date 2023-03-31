package parking.lot.processor.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParkingLotScore {

    private int score;
    private String message;
}
