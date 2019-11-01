<template>
    <div class="container">

    </div>
</template>

<script>
    export default {
        name: "ViewAllBiddingItems",
        mounted() {
            let socket = new WebSocket('ws://localhost:8080/ws/bidding-price');
            socket.addEventListener('open', function () {
                fetch('http://localhost:8080/api/v1/bidding-item', {method: 'GET'})
                    .then((response) => {

                    })
            });
        }
    }
</script>

<style scoped>

</style>