create table if not exists users
(
    id       long auto_increment not null,
    username varchar(20)         not null,
    password varchar(100)        not null,
    enabled  bit                 not null,
    primary key (id)
);

create table if not exists authorities
(
    username varchar(20) not null,
    authority varchar(20) not null
);

create table if not exists wallet(
    id  long auto_increment not null,
    name varchar(255) not null,
    create_date date not null,
    username varchar(20) not null,
    primary key(id)
);

create table if not exists transaction(
    id long auto_increment not null,
    name varchar(255) not null,
    price decimal(6,2) not null,
    transaction_category varchar(20) not null,
    create_date date not null,
    wallet_id long not null,
    transaction_type varchar(20) not null,
    primary key(id),
    foreign key(wallet_id) references wallet(id)
);
