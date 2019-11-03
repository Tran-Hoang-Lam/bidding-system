package simulations

import io.gatling.core.Predef._
import io.gatling.core.controller.inject.RampInjection
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef.http
import simulations.components.Bidding

import scala.concurrent.duration._

class BiddingSimulation extends Simulation {
  val biddingItem1: ScenarioBuilder = scenario("Bidding Item1")
    .group("Bidding Item1 with new price") {
      exec(Bidding.bid("item1"))
    }

  val biddingItem2: ScenarioBuilder = scenario("Bidding Item2")
    .group("Bidding Item2 with new price") {
      exec(Bidding.bid("item2"))
    }

  val steps: RampInjection = rampUsers(1000) over (1 minutes)

  private def env = http
    .baseURL("http://localhost:8080/api/v1")
    .header("Accept", "application/json")
    .header("Content-Type", "application/json")
    .silentResources.inferHtmlResources()
    .maxConnectionsPerHostLikeChrome

  setUp(
    biddingItem1.inject(steps).protocols(env),
    biddingItem2.inject(steps).protocols(env)
  )
}