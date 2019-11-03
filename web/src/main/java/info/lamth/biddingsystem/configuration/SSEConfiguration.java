package info.lamth.biddingsystem.configuration;

import info.lamth.biddingsystem.event.BiddingItemPriceEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.SubscribableChannel;
import reactor.core.publisher.Flux;

@Configuration
public class SSEConfiguration {
    @Bean
    public Flux<BiddingItemPriceEvent> biddingItemStream(SubscribableChannel newBiddingPrice) {
        return getItemPriceFlux(newBiddingPrice).share();
    }

    /**
     * A flux contain bidding item event
     *
     * @param newBiddingPrice channel
     * @return a Flux contains new bidding item
     */
    private Flux<BiddingItemPriceEvent> getItemPriceFlux(SubscribableChannel newBiddingPrice) {
        return Flux.create(sink -> {
            MessageHandler handler = message -> {
                BiddingItemPriceEvent event = (BiddingItemPriceEvent) message.getPayload();
                sink.next(event);
            };
            sink.onCancel(() -> newBiddingPrice.unsubscribe(handler));
            sink.onDispose(() -> newBiddingPrice.unsubscribe(handler));
            newBiddingPrice.subscribe(handler);
        });
    }
}
