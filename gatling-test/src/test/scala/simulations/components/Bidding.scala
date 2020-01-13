package simulations.components

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._

object Bidding {
  var initialBidPrice = 90000
  private def increasePrice(): Int = {
    initialBidPrice += 10000
    initialBidPrice
  }

  private def getBidPrice(id: String) = Map("bidPrice" -> increasePrice(), "id" -> id)

  private def feeder(id:String): Iterator[Map[String, Any]] = Iterator.continually(getBidPrice(id))

  def bid(id: String): ChainBuilder = {
    feed(feeder(id))
      .exec(
        http(s"Bid ${id}")
          .post("/api/v1/bidding-item/bid")
          .header("Content-Type", "application/x-www-form-urlencoded")
          .queryParam("id", "${id}")
          .queryParam("price", "${bidPrice}")
      )
      .pause(1)
  }
}
