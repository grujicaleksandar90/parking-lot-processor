package parkinglotprocessor.model.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class InvalidCoordinatesResponse {

    private String error;
    private String message;
    private LocalDateTime timestamp;
}
