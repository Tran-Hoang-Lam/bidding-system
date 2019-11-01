package info.lamth.biddingsystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BiddingItem {
    @Id
    private String id;
    private String name;
    private String description;
    private Long initialPrice;
    private Long currentBidPrice;
    private String state;
}
