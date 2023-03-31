package parking.lot.processor.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import parking.lot.processor.model.dao.ParkingLot;

import java.util.List;

@Repository
public interface ParkingLotRepository extends ElasticsearchRepository<ParkingLot, String>, ParkingLotSearchRepository {

    /**
     * finds the closest parking lot based on the geoPoint contained inside the sort
     *
     * @param sort
     *     the sorting logic
     * @return the closest parking lot contained in the SearchHit object
     */
    List<SearchHit<ParkingLot>> findTopBy(Sort sort);
}
