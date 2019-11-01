package info.lamth.biddingsystem.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.lamth.biddingsystem.event.BiddingItemPriceEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import reactor.core.publisher.Flux;

import java.util.Collections;

@Configuration
public class WebSocketConfiguration {
    @Bean
    HandlerMapping handlerMapping(WebSocketHandler webSocketHandler) {
        return new SimpleUrlHandlerMapping() {
            {
                setUrlMap(Collections.singletonMap("/ws/bidding-price", webSocketHandler));
                setOrder(10);
            }
        };
    }

    @Bean
    WebSocketHandlerAdapter webSocketHandlerAdapter() {
        return new WebSocketHandlerAdapter();
    }

    @Bean
    WebSocketHandler webSocketHandler(SubscribableChannel newBiddingPrice, ObjectMapper objectMapper) {
        Flux<String> itemPriceFlux = Flux.create(sink -> {
            MessageHandler handler = message -> {
                BiddingItemPriceEvent event = (BiddingItemPriceEvent) message.getPayload();
                sink.next(event);
            };
            sink.onCancel(() -> newBiddingPrice.unsubscribe(handler));
            sink.onDispose(() -> newBiddingPrice.unsubscribe(handler));
            newBiddingPrice.subscribe(handler);
        }).map(event -> {
            try {
                return objectMapper.writeValueAsString(event);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });

        return session -> session.send(itemPriceFlux.map(session::textMessage).share());
    }
}
