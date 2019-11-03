package info.lamth.biddingsystem.aop;

import info.lamth.biddingsystem.event.BiddingItemPriceEvent;
import info.lamth.biddingsystem.model.BiddingItem;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Log4j2
public class BiddingPriceAspect {
    private final SubscribableChannel newBiddingPrice;

    public BiddingPriceAspect(SubscribableChannel newBiddingPrice) {
        this.newBiddingPrice = newBiddingPrice;
    }

    @AfterReturning(value = "execution(* info.lamth.biddingsystem.repository.BiddingItemRepository.save(..))", returning = "newItem")
    public void fireNewBiddingEvent(BiddingItem newItem) {
        log.info("Send item " + newItem.getName() + " to stream");
        newBiddingPrice.send(MessageBuilder.withPayload(
                BiddingItemPriceEvent.builder()
                        .id(newItem.getId())
                        .name(newItem.getName())
                        .description(newItem.getDescription())
                        .currentBidPrice(newItem.getCurrentBidPrice())
                        .initialPrice(newItem.getInitialPrice())
                        .build()
        ).build());
    }
}
