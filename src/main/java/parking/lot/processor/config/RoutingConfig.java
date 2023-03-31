package parking.lot.processor.config;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RoutingConfig {

    @NotNull
    private String inputDir;

    @NotNull
    private String moveFailed;
}
