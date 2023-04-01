package parkinglotprocessor.route;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;

import org.springframework.stereotype.Component;

import parkinglotprocessor.config.ParkingLotProcessorConfigurationProperties;
import parkinglotprocessor.config.RoutingProperties;
import parkinglotprocessor.model.ParkingLotCsvRows;
import parkinglotprocessor.processor.UniqueParkingLotsProcessor;
import parkinglotprocessor.service.ParkingLotService;

@Component
public class ParkingLotRouteBuilder extends RouteBuilder {

    private final ParkingLotService service;
    private final RoutingProperties routingProperties;

    public ParkingLotRouteBuilder(ParkingLotService service, ParkingLotProcessorConfigurationProperties properties) {
        this.service = service;
        this.routingProperties = properties.getRouting();
    }

    @Override
    public void configure() {
        final BindyCsvDataFormat parkingLotDataFormat = new BindyCsvDataFormat(ParkingLotCsvRows.class);

        from(String.format("file:%s?moveFailed=%s", routingProperties.getInputDir(), routingProperties.getMoveFailed())).routeId("load-parking-lots")
                .log(LoggingLevel.INFO, "Loading file '${header.CamelFileName}' for processing.")
                .unmarshal(parkingLotDataFormat)
                .bean(new UniqueParkingLotsProcessor())
                .bean(service, "saveAll(${body})");

    }
}
