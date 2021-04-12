delete from transaction;
delete from wallet;
delete from authorities;
delete from users;

insert into users (username, password, enabled) values ('admin', '$2a$10$eG28hqAjihXGfSyrNUji9OZEGnMNh66uQUjjIBU0OaaE4Os4u1tom', 1);
insert into users (username, password, enabled) values ('user', '$2a$10$9P8MUV6cFuWyeys48sQMSe99vu5C1GzwMvsvKvjuvyTCl69p0KsrO', 1);


insert into authorities (username, authority) values ('admin', 'ROLE_ADMIN');
insert into authorities (username, authority) values ('admin', 'ROLE_USER');
insert into authorities (username, authority) values ('user', 'ROLE_USER');

insert into wallet(name,create_date,username) values('adminWallet', curdate(), 'admin');
insert into wallet(name,create_date,username) values('userWallet', curdate(), 'user');
