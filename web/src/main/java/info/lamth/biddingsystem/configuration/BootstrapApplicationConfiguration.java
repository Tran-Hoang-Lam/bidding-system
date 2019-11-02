package info.lamth.biddingsystem.configuration;

import info.lamth.biddingsystem.queue.BiddingQueue;
import info.lamth.biddingsystem.repository.BiddingItemRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class BootstrapApplicationConfiguration implements ApplicationListener<ApplicationReadyEvent> {
    private final BiddingItemRepository biddingItemRepository;
    private final BiddingQueue biddingQueue;

    public BootstrapApplicationConfiguration(BiddingItemRepository biddingItemRepository, BiddingQueue biddingQueue) {
        this.biddingItemRepository = biddingItemRepository;
        this.biddingQueue = biddingQueue;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        biddingItemRepository.findAll().forEach(biddingQueue::fillBiddingItemMap);
        biddingQueue.activeQueue();
    }
}
