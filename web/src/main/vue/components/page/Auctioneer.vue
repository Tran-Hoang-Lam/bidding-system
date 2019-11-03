<template>
    <div class="container">
        <div class="row">
            <div class="col-lg-8 col-md-10 mx-auto">
                <h2 class="post-title">Add new item</h2>
                <form id="biddingItem" novalidate>
                    <div class="control-group">
                        <div class="form-group floating-label-form-group controls">
                            <label>ID</label>
                            <input type="text" v-model="id" class="form-control" placeholder="ID" id="id" required
                                   data-validation-required-message="Please enter ID.">
                            <p class="help-block text-danger"></p>
                        </div>
                    </div>
                    <div class="control-group">
                        <div class="form-group floating-label-form-group controls">
                            <label>Name</label>
                            <input type="text" v-model="name" class="form-control" placeholder="Name" id="name" required
                                   data-validation-required-message="Please enter name.">
                            <p class="help-block text-danger"></p>
                        </div>
                    </div>
                    <div class="control-group">
                        <div class="form-group floating-label-form-group controls">
                            <label>Description</label>
                            <input type="text" v-model="description" class="form-control" placeholder="Description"
                                   id="description">
                        </div>
                    </div>
                    <div class="control-group">
                        <div class="form-group floating-label-form-group controls">
                            <label>Initial Price</label>
                            <input type="number" v-model="initialPrice" class="form-control" placeholder="Initial Price"
                                   id="initialPrice">
                        </div>
                    </div>
                    <br/>
                    <div class="form-group">
                        <button class="btn btn-primary" id="submitButton" @click.prevent="create">Create</button>
                    </div>
                </form>
                <hr>
                <h2 class="post-title">Reset item state</h2>
                <form id="resetItemState" novalidate>
                    <div class="control-group">
                        <div class="form-group floating-label-form-group controls">
                            <label>ID</label>
                            <input type="text" v-model="resetItemId" class="form-control" placeholder="ID" id="reset-item-id" required
                                   data-validation-required-message="Please enter ID.">
                            <p class="help-block text-danger"></p>
                        </div>
                    </div>
                    <br/>
                    <div class="form-group">
                        <button class="btn btn-primary" id="resetItemSubmitButton" @click.prevent="resetItem">Reset</button>
                    </div>
                </form>
                <hr/>
                <h2 class="post-title">Change Bid Price</h2>
                <form id="changeItemPrice" novalidate>
                    <div class="control-group">
                        <div class="form-group floating-label-form-group controls">
                            <label>ID</label>
                            <input type="text" v-model="changeBidPriceItemId" class="form-control" placeholder="ID" id="change-bid-item-id" required
                                   data-validation-required-message="Please enter ID.">
                            <p class="help-block text-danger"></p>
                        </div>
                    </div>
                    <div class="control-group">
                        <div class="form-group floating-label-form-group controls">
                            <label>New Bid Price</label>
                            <input type="text" v-model="changeBidPriceItemCurrentPrice" class="form-control" placeholder="ID" id="change-bid-item-price" required
                                   data-validation-required-message="Please enter Price.">
                            <p class="help-block text-danger"></p>
                        </div>
                    </div>
                    <br/>
                    <div class="form-group">
                        <button class="btn btn-primary" id="changeItemPriceSubmitButton" @click.prevent="changeBidPrice">Change</button>
                    </div>
                </form>
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
                state: 'NEW',
                resetItemId: '',
                changeBidPriceItemId: '',
                changeBidPriceItemCurrentPrice: 10000
            }
        },
        methods: {
            create() {
                if (this.id === '') {
                    alert('Please input id');
                    return;
                }
                if (this.name === '') {
                    alert('Please input name');
                    return;
                }
                if (this.initialPrice === '' || this.initialPrice < 0) {
                    alert('Price too low!!');
                    return;
                }
                let self = this;
                axios.post('/api/v1/bidding-item', {
                    id: this.id,
                    name: this.name,
                    description: this.description,
                    initialPrice: this.initialPrice,
                    currentBidPrice: this.initialPrice,
                    state: this.state
                }).then(function () {
                    self.id = '';
                    self.name = '';
                    self.description = '';
                    self.initialPrice = 10000;
                    self.state = 'NEW';
                }).catch(function (error) {
                    alert(error.response.data.message);
                })
            },
            resetItem() {
                if (this.resetItemId === '') {
                    alert('Please input id');
                    return;
                }
                let self = this;
                axios.patch('/api/v1/bidding-item/reset', null, {
                    params: {id: self.resetItemId}
                }).then(function () {
                    self.resetItemId = ''
                }).catch(function (error) {
                    alert(error.response.data.message);
                })
            },
            changeBidPrice() {
                if (this.changeBidPriceItemId === '') {
                    alert('Please input id');
                    return;
                }
                if (this.changeBidPriceItemCurrentPrice === '' || this.changeBidPriceItemCurrentPrice < 0) {
                    alert('Price too low!!');
                    return;
                }
                let self = this;
                axios.patch('/api/v1/bidding-item/price', null, {
                    params: {id: self.changeBidPriceItemId, price: self.changeBidPriceItemCurrentPrice}
                }).then(function () {
                    self.changeBidPriceItemId = '';
                    self.changeBidPriceItemCurrentPrice = 10000;
                }).catch(function (error) {
                    alert(error.response.data.message);
                })
            }
        }
    }
</script>

<style scoped>

</style>