<template>
    <div class="container">
        <div class="row">
            <div class="col-lg-8 col-md-10 mx-auto">
                <blockquote class="blockquote" v-for="item in biddingItemArray" :id="item.id" :key="item.id">ID: {{item.id}} - Name: {{item.name}} - Initial: {{item.initialPrice}} - Current: {{item.currentBidPrice}}</blockquote>
            </div>
        </div>
    </div>
</template>

<script>
    import axios from 'axios';

    export default {
        name: "ViewAllBiddingItems",
        data() {
          return {
              biddingItemArray: []
          }
        },
        mounted() {
            let self = this;
            const eventSource = new EventSource('/api/v1/bidding-item/stream');
            eventSource.addEventListener('newPrice', function(e) {
                let biddingItem = JSON.parse(e.data);
                let idSet = self.biddingItemArray.map(item => item.id);
                if (idSet.includes(biddingItem.id)) {
                    self.biddingItemArray.map(item => {
                        if ((item.id).trim() === (biddingItem.id).trim()) {
                            item.currentBidPrice = biddingItem.currentBidPrice;
                        }
                    });
                } else {
                    self.biddingItemArray.push(biddingItem);
                }
            }, false);
            axios.get('/api/v1/bidding-item')
                .then(function (response) {
                    response.data.forEach(function (biddingItem) {
                        if (biddingItem.state !== 'PAUSE' || biddingItem.state !== 'END') {
                            self.biddingItemArray.push(biddingItem);
                        }
                    });
                });
        }
    }
</script>

<style scoped>

</style>