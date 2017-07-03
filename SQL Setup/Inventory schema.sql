-- create database Inventory;

-- use Inventory;

create table products(
barcode 		varchar(30) not null primary key,
category 		varchar(30) not null,
product_name 	varchar(100) not null,
kr				int,
ore				int,
has_discount	bool default false 
);


create table discounts(
id 				int primary key auto_increment,
product_limit	int default 0,
kr				double not null,
ore				double not null,
barcode 		varchar(30),

foreign key(barcode) references products(barcode)
);
