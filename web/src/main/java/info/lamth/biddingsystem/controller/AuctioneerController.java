package info.lamth.biddingsystem.controller;

import info.lamth.biddingsystem.constant.BidState;
import info.lamth.biddingsystem.model.BiddingItem;
import info.lamth.biddingsystem.service.BiddingService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/bidding-item")
public class AuctioneerController {
    private final BiddingService biddingService;

    public AuctioneerController(BiddingService biddingService) {
        this.biddingService = biddingService;
    }

    @PostMapping
    public Mono<BiddingItem> createBiddingItem(@RequestBody BiddingItem biddingItem) {
        return biddingService.createBiddingItem(biddingItem);
    }

    @PatchMapping("price")
    public Mono<BiddingItem> updateBiddingPrice(@RequestParam("id") String itemId,
                                                @RequestParam("price") Long price) {
        return biddingService.updateBiddingPrice(itemId, price);
    }

    @PatchMapping("state")
    public Mono<BiddingItem> updateBiddingState(@RequestParam("id") String itemId,
                                                @RequestParam("state") String state) {
        return biddingService.updateItemState(itemId, BidState.valueOf(state));
    }
}
