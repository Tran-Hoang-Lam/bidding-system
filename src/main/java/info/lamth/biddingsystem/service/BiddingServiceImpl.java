package info.lamth.biddingsystem.service;

import info.lamth.biddingsystem.constant.BidState;
import info.lamth.biddingsystem.model.BiddingItem;
import info.lamth.biddingsystem.repository.BiddingItemRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service("biddingService")
public class BiddingServiceImpl implements BiddingService {
    private final BiddingItemRepository biddingItemRepository;

    public BiddingServiceImpl(BiddingItemRepository biddingItemRepository) {
        this.biddingItemRepository = biddingItemRepository;
    }

    @Override
    public Mono<BiddingItem> createBiddingItem(BiddingItem biddingItem) {
        BiddingItem savedItem = biddingItemRepository.save(biddingItem);
        return Mono.just(savedItem);
    }

    @Override
    public Mono<BiddingItem> bidPrice(String id, Long price) {
        BiddingItem currentItem = getBiddingItem(id);
        if (currentItem.getCurrentBidPrice() > price) {
            throw new IllegalArgumentException("Price is too low, current is " + currentItem.getCurrentBidPrice());
        }

        currentItem.setCurrentBidPrice(price);
        biddingItemRepository.save(currentItem);
        return Mono.just(currentItem);
    }

    @Override
    public Mono<BiddingItem> updateItemState(String id, BidState newState) {
        BiddingItem currentItem = getBiddingItem(id);
        currentItem.setState(newState.name());
        return Mono.just(biddingItemRepository.save(currentItem));
    }

    @Override
    public Mono<BiddingItem> updateInitialPrice(String id, Long newPrice) {
        BiddingItem currentItem = getBiddingItem(id);
        if (!currentItem.getState().equals(BidState.NEW.name()) || currentItem.getState().equals(BidState.PAUSE.name())) {
            throw new IllegalArgumentException("Current state " + currentItem.getState() + " not allow to update initial price");
        }

        currentItem.setInitialPrice(newPrice);
        return Mono.just(biddingItemRepository.save(currentItem));
    }

    @Override
    public Flux<BiddingItem> getAllCurrentBiddingItems() {
        return Flux.fromIterable(biddingItemRepository.findAll());
    }

    private BiddingItem getBiddingItem(String id) {
        return biddingItemRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Item ID not exist"));
    }
}
