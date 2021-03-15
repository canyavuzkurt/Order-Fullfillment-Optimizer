insert into product (created_at, name) VALUES (current_time, 'Refrigerator');
insert into product (created_at, name) VALUES (current_time, 'Oven');
insert into product (created_at, name) VALUES (current_time, 'Microwave');

insert into location (created_at, alias) VALUES (current_time, 'Bayraklı');
insert into location (created_at, alias) VALUES (current_time, 'Menemen');
insert into location (created_at, alias) VALUES (current_time, 'Güzelbahçe');

insert into stock (product_id, location_id, amount) VALUES (1, 1, 4);
insert into stock (product_id, location_id, amount) VALUES (1, 3, 5);
insert into stock (product_id, location_id, amount) VALUES (2, 3, 1);
insert into stock (product_id, location_id, amount) VALUES (2, 2, 3);
insert into stock (product_id, location_id, amount) VALUES (3, 1, 6);