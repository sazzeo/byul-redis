drop table if exists concert;
drop table if exists customer;
drop table if exists test;
drop table if exists ticket;

create table concert
(
    concert_id      bigint not null auto_increment,
    ticket_quantity bigint,
    has_quantity boolean,
    primary key (concert_id)
);

create table customer
(
    customer_id bigint not null auto_increment,
    name        varchar(255),
    primary key (customer_id)
);

create table ticket
(
    ticket_id   bigint not null auto_increment,
    concert_id  bigint,
    customer_id bigint,
    vip         bit    not null,
    issue_date  datetime(6),
    primary key (ticket_id)
);

create table test
(
    test_id bigint not null auto_increment,
    user_id bigint,
    at      datetime(6),
    primary key (test_id)
);