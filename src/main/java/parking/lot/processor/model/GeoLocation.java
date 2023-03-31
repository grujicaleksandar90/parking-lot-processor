package parking.lot.processor.model;

import lombok.*;

@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public final class GeoLocation {

    private double latitude;
    private double longitude;
}
