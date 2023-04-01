package parkinglotprocessor.service;

import parkinglotprocessor.model.ParkingLotCsvRows;
import parkinglotprocessor.model.response.ClosestParkingLot;
import parkinglotprocessor.model.response.ParkingLotScore;

import java.util.List;

public interface ParkingLotService {

    /**
     * saves all parking lot records
     *
     * @param parkingLotCsvRows
     *      records from the ingested file
     */
    void saveAll(List<ParkingLotCsvRows> parkingLotCsvRows);

    /**
     * finds the closest parking lot based on the given coordinates
     *
     * @param latitude
     *      north or south location
     * @param longitude
     *      east or west location
     * @return the closest parking lot
     */
    ClosestParkingLot findClosest(double latitude, double longitude);

    /**
     * calculates score of the given coordinates based on how many parking lots are in the 1km radius
     *
     * @param latitude
     *      north or south location
     * @param longitude
     *      east or west location
     * @return the ParkingLotScore which contains score number and scoring message
     */
    ParkingLotScore calculateScore(double latitude, double longitude);
}
