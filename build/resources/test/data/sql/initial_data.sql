-- Insert basic products
insert into product(id, product_name, product_code, price, enabled) values (1,'Black Coffee', 'BLACK_COFFEE', 4.0, true);
insert into product(id, product_name, product_code, price, enabled) values (2,'Latte', 'LATTE', 5.0, true);
insert into product(id, product_name, product_code, price, enabled) values (3,'Mocha', 'MOCHA', 6.0, false);
insert into product(id, product_name, product_code, price, enabled) values (4,'Tea', 'TEA', 3.0, true);
-- Insert basic toppings
insert into topping(id, topping_name, topping_code, price, enabled) values (1,'Milk', 'MILK', 2.0, true);
insert into topping(id, topping_name, topping_code, price, enabled) values (2,'Hazelnut syrup', 'HAZELNUT_SYRUP', 3.0, true);
insert into topping(id, topping_name, topping_code, price, enabled) values (3,'Chocolate sauce', 'CHOCOLATE_SAUCE', 5.0, false);
insert into topping(id, topping_name, topping_code, price, enabled) values (4,'Lemon', 'LEMON', 2.0, true);