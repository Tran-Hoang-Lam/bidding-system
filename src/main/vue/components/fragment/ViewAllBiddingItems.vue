<template>
    <div class="container">

    </div>
</template>

<script>
    import axios from 'axios';

    export default {
        name: "ViewAllBiddingItems",
        mounted() {
            let socket = new WebSocket('ws://localhost:8080/ws/bidding-price');
            socket.addEventListener('open', function () {
                axios.get('/api/v1/bidding-item')
                    .then(function (response) {
                        console.log(response.data);
                    })
            });
        }
    }
</script>

<style scoped>

</style>