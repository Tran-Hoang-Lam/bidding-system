create table bidding_items (
    id varchar(255) primary key,
    name varchar(255) not null,
    description varchar(255),
    initial_price bigint,
    current_bid_price bigint,
    state varchar(10)
);
