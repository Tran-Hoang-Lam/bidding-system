package info.lamth.biddingsystem.aop;

import info.lamth.biddingsystem.event.BiddingItemPriceEvent;
import info.lamth.biddingsystem.model.BiddingItem;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Aspect
@Log4j2
public class BiddingPriceAspect {
    private final SubscribableChannel newBiddingPrice;

    public BiddingPriceAspect(SubscribableChannel newBiddingPrice) {
        this.newBiddingPrice = newBiddingPrice;
    }

    @AfterReturning(value = "execution(* info.lamth.biddingsystem.service.BiddingServiceImpl.bidPrice(..))" +
            " || execution(* info.lamth.biddingsystem.service.BiddingServiceImpl.createBiddingItem(..))", returning = "newItem")
    public void fireNewBiddingEvent(Mono<BiddingItem> newItem) {
        newItem.subscribe(item -> {
            log.info("Fire new item " + item.getName());
            newBiddingPrice.send(MessageBuilder.withPayload(
                    BiddingItemPriceEvent.builder()
                            .id(item.getId())
                            .name(item.getName())
                            .description(item.getDescription())
                            .price(item.getCurrentBidPrice())
                            .build()
            ).build());
        });
    }
}
