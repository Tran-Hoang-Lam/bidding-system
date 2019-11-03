<template>
    <div class="container">
        <div class="row">
            <div class="col-lg-8 col-md-10 mx-auto">
                <h2 class="post-title">Bid</h2>
                <form id="bidNewPrice">
                    <div class="control-group">
                        <div class="form-group floating-label-form-group controls">
                            <label>ID</label>
                            <input type="text" v-model="id" class="form-control" placeholder="ID" id="id">
                        </div>
                    </div>
                    <div class="control-group">
                        <div class="form-group floating-label-form-group controls">
                            <label>New Price</label>
                            <input type="number" v-model="price" class="form-control" placeholder="New Price"
                                   id="newPrice">
                        </div>
                    </div>
                    <br/>
                    <div class="form-group">
                        <button class="btn btn-primary" id="submitButton" @click.prevent="bid">Bid</button>
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
        name: "Bidder",
        data() {
            return {
                id: '',
                price: 50000
            }
        },
        methods: {
            bid() {
                if (this.id.isEmpty) {
                    alert('Please input id');
                    return;
                }
                if (this.price.isEmpty || this.price < 0) {
                    alert('Price not valid');
                    return;
                }
                let self = this;
                axios.post('/api/v1/bidding-item/bid', null, {
                    params: {id: self.id, price: self.price}
                }).then(function () {
                    self.price = (Number(self.price) + 10000);
                }).catch(function (error) {
                    alert(error.response.data.message);
                })
            }
        }
    }
</script>

<style scoped>

</style>