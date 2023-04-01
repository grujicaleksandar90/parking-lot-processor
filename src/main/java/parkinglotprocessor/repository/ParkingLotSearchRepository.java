package parkinglotprocessor.repository;

import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingLotSearchRepository {
    /**
     * counts all parking lots that are within a given distance of a point
     *
     * @param geoPoint
     *     the center point
     * @param distance
     *     the distance
     * @param unit
     *     the distance unit
     * @return the number of parking lots found
     */
    long countWithin(GeoPoint geoPoint, Double distance, String unit);
}
