package info.lamth.biddingsystem.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BiddingItemPriceEvent {
    private String id;
    private String name;
    private String description;
    private Long initialPrice;
    private Long currentBidPrice;
}
