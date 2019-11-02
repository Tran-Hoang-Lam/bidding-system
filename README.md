# Bidding System

## Overview
There is 2 sub project
* web: contains vue component and api
* gatling-test: load test written with gatling

## Web Functionality
Provide 2 view for Bidder and Auctioneer
* Both bidder and auctioneer can view item price in realtime
* Auctioneer can create new item, it will automatically show on the view
* Auctioneer can change the price of item that not in state BIDDING (currently only api, no screen for it yet)
* Bidder can bid a new price for selected item, but cannot bid a price lower than current price

## Gatling test Functionality
Provide a simple load test that simulate 1000 user bid 2 item in 1 minute.
The report should be in the build folder.

## Project setup
```
gradlew clean build
```

### Run web server
```
gradlew bootRun
```

### Run load test
```
gradlew runSimulationTest
```
