package parkinglotprocessor.model.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

@Data
@Builder
@ToString
@Document(indexName = "parking_lot")
public class ParkingLot {

    @Id
    @JsonIgnore
    private String id;

    @Field(type = FieldType.Text, name = "a_name")
    private String name;

    @GeoPointField
    private GeoPoint location;

    @Field(type = FieldType.Text, name = "year")
    private String year;

    @Field(type = FieldType.Text, name = "type")
    private String type;

}
