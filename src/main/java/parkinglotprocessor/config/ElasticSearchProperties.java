package parkinglotprocessor.config;

import lombok.Data;

@Data
public class ElasticSearchProperties {

    private String username;
    private String password;
    private int port;
}
