package parkinglotprocessor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import parkinglotprocessor.model.response.ClosestParkingLot;
import parkinglotprocessor.model.response.ParkingLotScore;
import parkinglotprocessor.service.ParkingLotService;

@RestController
@RequiredArgsConstructor
@RequestMapping("parking-lot")
public class ParkingLotController {

    private final ParkingLotService parkingLotService;

    @GetMapping("closest")
    public ResponseEntity<ClosestParkingLot> findClosest(@RequestParam double latitude, @RequestParam double longitude) {
        return ResponseEntity.ok(parkingLotService.findClosest(latitude, longitude));
    }

    @GetMapping("score")
    public ResponseEntity<ParkingLotScore> findScore(@RequestParam double latitude, @RequestParam double longitude) {
        return ResponseEntity.ok(parkingLotService.calculateScore(latitude, longitude));
    }

}
