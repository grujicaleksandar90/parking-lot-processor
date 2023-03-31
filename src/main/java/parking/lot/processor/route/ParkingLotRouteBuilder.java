package parking.lot.processor.route;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;

import org.springframework.stereotype.Component;

import parking.lot.processor.config.ParkingLotProcessorConfigurationProperties;
import parking.lot.processor.config.RoutingConfig;
import parking.lot.processor.model.ParkingLotCsvRows;
import parking.lot.processor.processor.UniqueParkingLotsProcessor;
import parking.lot.processor.service.ParkingLotService;

@Component
public class ParkingLotRouteBuilder extends RouteBuilder {

    private final ParkingLotService service;
    private final RoutingConfig routingConfig;

    public ParkingLotRouteBuilder(ParkingLotService service, ParkingLotProcessorConfigurationProperties properties) {
        this.service = service;
        this.routingConfig = properties.getRouting();
    }

    @Override
    public void configure() {
        final BindyCsvDataFormat parkingLotDataFormat = new BindyCsvDataFormat(ParkingLotCsvRows.class);

        from(String.format("file:%s?moveFailed=%s", routingConfig.getInputDir(), routingConfig.getMoveFailed())).routeId("load-parking-lots")
                .log(LoggingLevel.INFO, "Loading file '${header.CamelFileName}' for processing.")
                .unmarshal(parkingLotDataFormat)
                .bean(new UniqueParkingLotsProcessor())
                .bean(service, "saveAll(${body})");

    }
}
