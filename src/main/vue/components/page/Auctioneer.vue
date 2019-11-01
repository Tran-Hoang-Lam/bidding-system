<template>
    <div class="container">
        <div class="row">
            <div class="col-lg-8 col-md-10 mx-auto">
                <form id="biddingItem">
                    <div class="control-group">
                        <div class="form-group floating-label-form-group controls">
                            <label>ID</label>
                            <input type="text" v-model="id" class="form-control" placeholder="ID" id="id">
                        </div>
                    </div>
                    <div class="control-group">
                        <div class="form-group floating-label-form-group controls">
                            <label>Name</label>
                            <input type="text" v-model="name" class="form-control" placeholder="Name" id="name">
                        </div>
                    </div>
                    <div class="control-group">
                        <div class="form-group floating-label-form-group controls">
                            <label>Description</label>
                            <input type="text" v-model="description" class="form-control" placeholder="Description" id="description">
                        </div>
                    </div>
                    <div class="control-group">
                        <div class="form-group floating-label-form-group controls">
                            <label>Initial Price</label>
                            <input type="number" v-model="initialPrice" class="form-control" placeholder="Initial Price" id="initialPrice">
                        </div>
                    </div>
                    <br/>
                    <div class="form-group">
                        <button class="btn btn-primary" id="submitButton" @click.prevent="create">Create</button>
                    </div>
                </form>
                <hr>
            </div>
        </div>
    </div>
</template>

<script>
    import axios from "axios";

    export default {
        name: "Auctioneer",
        data() {
            return {
                id: '',
                name: '',
                description: '',
                initialPrice: 10000,
                currentBidPrice: 0,
                state: 'NEW'
            }
        },
        methods: {
            create() {
                axios.post('http://localhost:8080/api/v1/bidding-item', {
                    id: this.id,
                    name: this.name,
                    description: this.description,
                    initialPrice: this.initialPrice,
                    currentBidPrice: this.currentBidPrice,
                    state: this.state
                }).then(function (response) {
                    console.log(response);
                })
            }
        }
    }
</script>

<style scoped>

</style>