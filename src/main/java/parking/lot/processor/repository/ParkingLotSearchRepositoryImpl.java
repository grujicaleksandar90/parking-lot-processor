package parking.lot.processor.repository;

import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import parking.lot.processor.model.dao.ParkingLot;

public class ParkingLotSearchRepositoryImpl implements ParkingLotSearchRepository {

    private final ElasticsearchOperations operations;

    public ParkingLotSearchRepositoryImpl(ElasticsearchOperations operations) {
        this.operations = operations;
    }

    @Override
    public long countWithin(GeoPoint geoPoint, Double distance, String unit) {
        return operations.count(new CriteriaQuery(new Criteria("location")
                                .within(geoPoint, distance.toString() + unit)), ParkingLot.class);
    }
}
