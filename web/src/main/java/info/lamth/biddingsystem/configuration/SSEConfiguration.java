package info.lamth.biddingsystem.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.lamth.biddingsystem.event.BiddingItemPriceEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.SubscribableChannel;
import reactor.core.publisher.Flux;

@Configuration
public class SSEConfiguration {
    @Bean
    public Flux<BiddingItemPriceEvent> biddingItemStream(SubscribableChannel newBiddingPrice, ObjectMapper objectMapper) {
        return getItemPriceFlux(newBiddingPrice, objectMapper).share();
    }

    /**
     * Convert item from channel to json
     *
     * @param newBiddingPrice channel
     * @param objectMapper    jackson converter
     * @return a Flux contains json of new bidding item
     */
    private Flux<BiddingItemPriceEvent> getItemPriceFlux(SubscribableChannel newBiddingPrice, ObjectMapper objectMapper) {
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
