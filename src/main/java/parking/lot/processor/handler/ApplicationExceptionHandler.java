package parking.lot.processor.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import parking.lot.processor.exception.InvalidCoordinatesException;
import parking.lot.processor.model.response.InvalidCoordinatesResponse;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(InvalidCoordinatesException.class)
    public ResponseEntity<InvalidCoordinatesResponse> handleApplicationException(InvalidCoordinatesException exception) {
        InvalidCoordinatesResponse response = InvalidCoordinatesResponse.builder()
                .error(String.valueOf(HttpStatus.BAD_REQUEST))
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
