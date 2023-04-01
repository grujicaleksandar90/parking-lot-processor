package parkinglotprocessor.config;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RoutingProperties {

    @NotNull
    private String inputDir;

    @NotNull
    private String moveFailed;
}
