package info.lamth.biddingsystem.controller;

import info.lamth.biddingsystem.model.BiddingItem;
import info.lamth.biddingsystem.service.BiddingService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/bidding-item")
public class BidderController {
    private final BiddingService biddingService;

    public BidderController(BiddingService biddingService) {
        this.biddingService = biddingService;
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
}
