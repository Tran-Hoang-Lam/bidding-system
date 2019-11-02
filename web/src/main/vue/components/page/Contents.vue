<template>
    <div class="container">
        <div class="row">
            <div class="col-lg-8 col-md-10 mx-auto">
                <div class="post-preview">
                    <router-link to="/bidder">
                        <h2 class="post-title">Bidder Page</h2>
                        <h3 class="post-subtitle">Can bid a new price</h3>
                    </router-link>
                    <router-link to="/auctioneer">
                        <h2 class="post-title">Auctioneer Page</h2>
                        <h3 class="post-subtitle">Can add item and change initial price</h3>
                    </router-link>
                </div>
                <hr>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Contents"
    }
</script>

<style scoped>

</style>