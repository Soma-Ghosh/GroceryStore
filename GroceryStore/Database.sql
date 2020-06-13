drop database if exists grocery_store;

create database grocery_store;

use grocery_store;

drop table if exists admin_details;

create table admin_details(
admin_id int not null auto_increment,
username varchar(30) unique not null, 
email varchar(60) not null unique, 
password varchar(15) not null, 
primary key(admin_id));

drop table if exists customers;

create table customers (
customer_id int not null auto_increment,
customer_name varchar(50) not null,
username varchar(55) not null unique,
email varchar(60) not null unique,
contact varchar(10) not null unique,
password varchar(15) not null,
primary key(customer_id));

drop table if exists customer_address;

create table customer_address(
customer_id int not null,
location text(200) not null,
city varchar(30) not null,
pincode int(7) not null,
_state varchar(30) not null,
foreign key(customer_id) references customers(customer_id));

drop table if exists orders;

create table orders(
order_id int not null auto_increment,
ordered_by varchar(55) not null,
ordered_on date not null,
order_status varchar(20),
primary key(order_id),
foreign key(ordered_by) references customers (username));

drop table if exists ordered_items;

create table ordered_items(
order_id int not null,
product_id int not null,
quantity int not null,
foreign key(order_id) references orders(order_id));

drop table if exists products;

create table products(
product_id int not null auto_increment,
product_name varchar(30) not null,
category varchar(20),
description text(250),
price int not null,
quantity_in_stock int,
image_location varchar(250),
primary key(product_id));

drop table if exists wishlists;

create table wishlists(
username varchar(55) not null,
product_id int not null,
foreign key(username) references customers(username));

drop table if exists cart;

create table cart(
username varchar(55) not null,
product_id int not null,
quantity int not null,
foreign key(username) references customers(username));


insert into admin_details  (username,email,password)  values  
('ajeet','ajeet@gmail.com', '123'),  
('deepika','deepika@gmail.com', '123'),  
('vimal','vimal@gmail.com', '123'); 

insert into customers  (customer_name,username,email,contact,password)  values  
('Piyush','kumarpiyush','kumarpiyush@gmail.com','8059767734','abc'),  
('Alisha','kumarialisha','kumarialisha@gmail.com','1234567890','abc'),  
('Shrey','shrey393','shrey393@gmail.com','9123045189','abc');

insert into customer_address (customer_id, location, city, pincode, _state) values
(1,'Boston','New York',121212,'California'),
(2,'Queens','New York',121212,'California'),
(3,'Long Island','New York',121212,'California');


insert into products (product_name, category, description, price, quantity_in_stock, image_location) values
('Osmania Biscuits','BAKERY','Osmania Biscuits are known for thier delicious taste. This is a delightful snacks for kids',100,100,'products/biscuit.jpg'),
('Fresh White Bread','BAKERY','Fresh white bread are known for thier softness and freshness',25,100,'products/bread.png'),
('Fair cape Milk','DAIRY',' 1L pack of 2Fresh healthy cow milk',120,100,'products/milk.jpg'),
('Kraft Cheese','DAIRY','60 % less fat 12 slices',180,100,'products/cheese.jpg'),
('Sprite','BEVERAGES','1L, Sprite, clear hai!',65,100,'products/sprite.jpg'),
('Cocacola','BEVERAGES','500ML chilled fresh canned bottle',55,100,'products/cocacola.jpg'),
('Harpic','TOILETRIES','Saver Pack. Kills 99 percent germs and leaves a fresh smell. Best for toilet use.',70,100,'products/harpic.jpg'),
('Lizol','TOILETRIES','Pack of 2. Disinfectents for floor. Most trusted brand in India',150,100,'products/lizol.jpg');