package info.lamth.biddingsystem.controller;

import info.lamth.biddingsystem.event.BiddingItemPriceEvent;
import info.lamth.biddingsystem.model.BiddingItem;
import info.lamth.biddingsystem.service.BiddingService;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/bidding-item")
public class BidderController {
    private final BiddingService biddingService;
    private final Flux<BiddingItemPriceEvent> biddingItemStream;

    public BidderController(BiddingService biddingService, Flux<BiddingItemPriceEvent> biddingItemStream) {
        this.biddingService = biddingService;
        this.biddingItemStream = biddingItemStream;
    }

    @PostMapping("bid")
    public Mono<BiddingItem> bidNewPrice(@RequestParam("id") String itemId,
                                         @RequestParam("price") Long bidPrice) {
        return biddingService.bidPrice(itemId, bidPrice);
    }

    @GetMapping
    public Flux<BiddingItem> getAllItems() {
        return biddingService.getAllCurrentBiddingItems();
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<BiddingItemPriceEvent>> streamBiddingItem() {
        return biddingItemStream.map(item ->
                ServerSentEvent.<BiddingItemPriceEvent>builder().event("newPrice").data(item).build()
        );
    }
}
