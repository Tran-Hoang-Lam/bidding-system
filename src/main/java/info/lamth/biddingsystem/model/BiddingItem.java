package info.lamth.biddingsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bidding_items")
public class BiddingItem {
    @Id
    private String id;
    private String name;
    private String description;
    private Long initialPrice;
    private Long currentBidPrice;
    private String state;
}
