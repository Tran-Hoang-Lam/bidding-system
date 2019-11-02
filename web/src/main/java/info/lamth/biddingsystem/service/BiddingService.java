package info.lamth.biddingsystem.service;

import info.lamth.biddingsystem.constant.BidState;
import info.lamth.biddingsystem.model.BiddingItem;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BiddingService {
    Mono<BiddingItem> createBiddingItem(BiddingItem biddingItem);
    Mono<BiddingItem> bidPrice (String id, Long price);
    Mono<BiddingItem> updateItemState(String id, BidState newState);
    Mono<BiddingItem> updateBiddingPrice(String id, Long newPrice);
    Flux<BiddingItem> getAllCurrentBiddingItems();
}
