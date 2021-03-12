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