create table product
(
	id serial not null
		constraint product_pk
			primary key,
	product_name varchar not null,
	product_code varchar not null,
	price numeric not null,
	enabled boolean default true not null
);

create unique index product_id_uindex
	on product (id);

create unique index product_product_code_uindex
	on product (product_code);

create table topping
(
	id serial not null
		constraint topping_pk
			primary key,
	topping_name varchar not null,
	topping_code varchar not null,
	price numeric not null,
	enabled boolean default true not null
);

create unique index topping_id_uindex
	on topping (id);

create unique index topping_topping_code_uindex
	on topping (topping_code);


create table star_bux_customer
(
	id serial not null
		constraint user_pk
			primary key,
	first_name varchar default 'user'::character varying,
	last_name varchar default 'anonymous'::character varying,
	email varchar default 'test@starbux.com'::character varying,
	mobile numeric default 000000000
);

create unique index user_id_uindex
	on star_bux_customer (id);

create unique index user_id_uindex
	on star_bux_customer (id);

create table order_cart
(
	id serial not null
		constraint order_cart_pk
			primary key,
	star_bux_customer_id integer not null
		constraint order_cart_star_bux_customer_id_fk
			references star_bux_customer,
	is_active boolean not null,
	amount numeric,
	discount numeric,
	date timestamp with time zone
);

create unique index order_cart_id_uindex
	on order_cart (id);

create table order_product
(
	id serial not null
		constraint order_product_pk
			primary key,
	order_cart_id integer not null
		constraint order_product_order_cart_id_fk
			references order_cart,
	product_id integer not null
);

create unique index order_product_id_uindex
	on order_product (id);

create table order_topping
(
	id serial not null
		constraint order_topping_pk
			primary key,
	topping_id integer not null
		constraint order_topping_topping_id_fk
			references topping,
	order_product_id integer not null
		constraint order_topping_order_product_id_fk
			references order_product
);

create unique index order_topping_id_uindex
	on order_topping (id);


-- Insert basic products
insert into product(product_name, product_code, price, enabled) values ('Black Coffee', 'BLACK_COFFEE', 4.0, true);
insert into product(product_name, product_code, price, enabled) values ('Latte', 'LATTE', 5.0, true);
insert into product(product_name, product_code, price, enabled) values ('Mocha', 'MOCHA', 6.0, true);
insert into product(product_name, product_code, price, enabled) values ('Tea', 'TEA', 3.0, true);
-- Insert basic toppings
insert into topping(topping_name, topping_code, price, enabled) values ('Milk', 'MILK', 2.0, true);
insert into topping(topping_name, topping_code, price, enabled) values ('Hazelnut syrup', 'HAZELNUT_SYRUP', 3.0, true);
insert into topping(topping_name, topping_code, price, enabled) values ('Chocolate sauce', 'CHOCOLATE_SAUCE', 5.0, true);
insert into topping(topping_name, topping_code, price, enabled) values ('Lemon', 'LEMON', 2.0, true);