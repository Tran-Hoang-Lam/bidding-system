package info.lamth.biddingsystem.repository;

import info.lamth.biddingsystem.model.BiddingItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BiddingItemRepository extends CrudRepository<BiddingItem, String> {

}
