insert into product (created_at, name) VALUES (current_time, 'Prod1');
insert into product (created_at, name) VALUES (current_time, 'Prod2');
insert into product (created_at, name) VALUES (current_time, 'Prod3');

insert into stock (product_id, amount) VALUES (1, 4);
insert into stock (product_id, amount) VALUES (2, 1);
insert into stock (product_id, amount) VALUES (1, 5);
insert into stock (product_id, amount) VALUES (3, 6);
insert into stock (product_id, amount) VALUES (2, 3);