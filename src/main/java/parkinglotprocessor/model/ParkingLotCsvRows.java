package parkinglotprocessor.model;

import lombok.Data;
import lombok.ToString;
import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@Data
@ToString
@CsvRecord(separator = ",", skipFirstLine = true)
public class ParkingLotCsvRows {

    @DataField(pos = 1)
    private double latitude;

    @DataField(pos = 2)
    private double longitude;

    @DataField(pos = 3)
    private String name;

    @DataField(pos = 4)
    private String year;

    @DataField(pos = 5)
    private String type;

}
