package info.lamth.biddingsystem.service;

import info.lamth.biddingsystem.constant.BidState;
import info.lamth.biddingsystem.model.BiddingItem;
import info.lamth.biddingsystem.queue.BiddingQueue;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service("biddingService")
public class BiddingServiceImpl implements BiddingService {
    private final BiddingQueue biddingQueue;

    public BiddingServiceImpl(BiddingQueue biddingQueue) {
        this.biddingQueue = biddingQueue;
    }

    @Override
    public Mono<BiddingItem> createBiddingItem(BiddingItem biddingItem) {
        biddingQueue.queueBiddingItem(biddingItem);
        return Mono.just(biddingItem);
    }

    @Override
    public Mono<BiddingItem> bidPrice(String id, Long price) {
        BiddingItem currentItem = getBiddingItem(id);
        if (currentItem.getCurrentBidPrice() > price) {
            throw new IllegalArgumentException("Price is too low, current is " + currentItem.getCurrentBidPrice());
        }

        currentItem.setCurrentBidPrice(price);
        currentItem.setState(BidState.BIDDING.name());
        biddingQueue.queueBiddingItem(currentItem);
        return Mono.just(currentItem);
    }

    @Override
    public Mono<BiddingItem> updateItemState(String id, BidState newState) {
        BiddingItem currentItem = getBiddingItem(id);
        currentItem.setState(newState.name());
        biddingQueue.queueBiddingItem(currentItem);
        return Mono.just(currentItem);
    }

    @Override
    public Mono<BiddingItem> updateBiddingPrice(String id, Long newPrice) {
        BiddingItem currentItem = getBiddingItem(id);
        if (!currentItem.getState().equals(BidState.NEW.name()) || currentItem.getState().equals(BidState.PAUSE.name())) {
            throw new IllegalArgumentException("Current state " + currentItem.getState() + " not allow to update price");
        }

        currentItem.setCurrentBidPrice(newPrice);
        biddingQueue.queueBiddingItem(currentItem);
        return Mono.just(currentItem);
    }

    @Override
    public Flux<BiddingItem> getAllCurrentBiddingItems() {
        return Flux.fromIterable(biddingQueue.getAll());
    }

    private BiddingItem getBiddingItem(String id) {
        return Optional.ofNullable(biddingQueue.getItemById(id))
                .orElseThrow(() -> new IllegalArgumentException("Id " + id + " not exist"));
    }
}
