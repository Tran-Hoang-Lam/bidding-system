<template>
    <header class="masthead" :style="{ backgroundImage: `url(${image})` }">
        <div class="overlay"></div>
        <div class="container">
            <div class="row">
                <div class="col-lg-8 col-md-10 mx-auto">
                    <div class="site-heading">
                        <h1>Bidding System</h1>
                    </div>
                </div>
            </div>
        </div>
    </header>
</template>

<script>
    export default {
        name: "Header",
        props: {
            imageName: String
        },
        data() {
            return {
                image: require(`../assets/img/${this.imageName}`)
            }
        }
    }
</script>

<style scoped>

</style>