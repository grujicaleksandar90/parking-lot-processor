package parkinglotprocessor.config;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Map;

@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "parkinglotprocessor")
public class ParkingLotProcessorConfigurationProperties {

    @NotNull
    private RoutingProperties routing;

    @NotNull
    private ElasticSearchProperties elasticSearch;

    private Map<Integer, List<Integer>> parkingLotScoring;
}
