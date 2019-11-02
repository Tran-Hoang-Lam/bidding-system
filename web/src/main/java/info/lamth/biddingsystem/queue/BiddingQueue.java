package info.lamth.biddingsystem.queue;

import info.lamth.biddingsystem.model.BiddingItem;
import info.lamth.biddingsystem.repository.BiddingItemRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;

@Component
@Log4j2
public class BiddingQueue {
    private static final BlockingQueue<BiddingItem> BIDDING_ITEM_BLOCKING_QUEUE = new LinkedBlockingDeque<>();
    private static final ConcurrentHashMap<String, BiddingItem> BIDDING_ITEM_CONCURRENT_HASH_MAP = new ConcurrentHashMap<>();

    private final BiddingItemRepository biddingItemRepository;
    private final Executor executor;

    public BiddingQueue(BiddingItemRepository biddingItemRepository, Executor executor) {
        this.biddingItemRepository = biddingItemRepository;
        this.executor = executor;
    }

    public void fillBiddingItemMap(BiddingItem biddingItem) {
        log.info("Saving " + biddingItem.getName());
        BIDDING_ITEM_CONCURRENT_HASH_MAP.put(biddingItem.getId(), biddingItem);
    }

    public BiddingItem getItemById(String id) {
        log.info("Getting item " + id);
        return BIDDING_ITEM_CONCURRENT_HASH_MAP.get(id);
    }

    public List<BiddingItem> getAll() {
        return new ArrayList<>(BIDDING_ITEM_CONCURRENT_HASH_MAP.values());
    }

    public void queueBiddingItem(BiddingItem biddingItem) {
        log.info("Queuing item " + biddingItem.getId());
        fillBiddingItemMap(biddingItem);
        BIDDING_ITEM_BLOCKING_QUEUE.offer(biddingItem);
    }

    public void activeQueue() {
        this.executor.execute(() -> {
            while (true) {
                try {
                    BiddingItem biddingItem = BIDDING_ITEM_BLOCKING_QUEUE.take();
                    biddingItemRepository.save(biddingItem);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
