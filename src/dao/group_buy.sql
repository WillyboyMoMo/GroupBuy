drop table if exists cartitem;
drop table if exists cart;
drop table if exists user;
drop table if exists product;

create table if not exists product(
	productId int auto_increment primary key,
	productName varchar(50) not null,
	price decimal(10,2),
	unit varchar(10),
	isLaunch boolean
);

alter table product auto_increment = 501;

create table if not exists user(
	userId int auto_increment primary key,
	username varchar(50) not null,
	password varchar(50) not null,
	level int
);

alter table useId auto_increment = 101;

create table if not exists cart(
	cartId int auto_increment primary key,
	userId int not null,
	isCheckout boolean default false,
	checkoutTime datetime default current_timestamp,
	foreign key (userId) references user(userId)
);

alter table cart auto_incremet = 201;

create table if not exists cartItem(
	itemId int auto_increment primary key,
	cartId int not null,
	productId int not null,
	quantity int default 0,
	foreign key (cartId) references cart(cartId),
	foreign key (productId) references product(productId)
);

alter table cartitem auto_increment = 1;

-- 預設資料
INSERT INTO product (productId, productName, price, unit, isLaunch) VALUES
(501, 'Coffee', 300.00, 'Pack', true),
(502, 'Green Tea', 150.00, 'Box', false),
(503, 'Honey', 200.00, 'Bottle', false),
(504, 'Sugar', 100.00, 'Bag', true),
(505, 'Milk', 450.00, 'Dozen', true);


INSERT INTO user (userId, username, password, level) VALUES
(101, 'user123', 'pass123', 1),
(102, 'user456', 'pass456', 2),
(103, 'user789', 'pass789', 1);

INSERT INTO cart (cartId, userId, isCheckout, checkoutTime) VALUES
(201, 101, true, current_timestamp),
(202, 102, false, NULL),
(203, 103, true, current_timestamp),
(204, 103, false, NULL),
(205, 101, true, current_timestamp);

INSERT INTO cartitem (itemId, cartId, productId, quantity) VALUES
(1, 201, 501, 10),
(2, 201, 502, 8),
(3, 202, 503, 5),
(4, 203, 502, 8),
(5, 203, 504, 20),
(6, 205, 505, 15);
