<template>
    <div id="app">
        <Navigator/>
        <Header image-name="home-bg.jpg"/>
        <ViewAllBiddingItems/>
        <hr/>
        <router-view/>
        <Footer/>
    </div>
</template>

<script>
    import jquery from "jquery/dist/jquery.min";
    import bootstrapJs from "bootstrap/dist/js/bootstrap.bundle";
    import Navigator from "./components/fragment/Navigator";
    import Header from "./components/fragment/Header";
    import Footer from "./components/fragment/Footer";
    import ViewAllBiddingItems from "./components/fragment/ViewAllBiddingItems";

    export default {
        name: 'app',
        components: {ViewAllBiddingItems, Header, Navigator, Footer},
        comments: {
            Navigator,
            Header
        },
        data() {
            return {
                jquery,
                bootstrapJs
            }
        }
    }
</script>

<style>
    @import "~bootstrap/dist/css/bootstrap.min.css";
    @import "~@fortawesome/fontawesome-free/css/all.min.css";
    @import "assets/css/clean-blog.min.css";
</style>
