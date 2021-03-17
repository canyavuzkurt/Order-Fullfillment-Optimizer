insert into product (created_at, name) VALUES (current_time, 'Refrigerator');
insert into product (created_at, name) VALUES (current_time, 'Oven');
insert into product (created_at, name) VALUES (current_time, 'Microwave');
insert into product (created_at, name) VALUES (current_time, 'Dishwasher');
insert into product (created_at, name) VALUES (current_time, 'Kettle');

insert into location (created_at, alias) VALUES (current_time, 'New York');
insert into location (created_at, alias) VALUES (current_time, 'Boston');
insert into location (created_at, alias) VALUES (current_time, 'Florida');
insert into location (created_at, alias) VALUES (current_time, 'Portland');
insert into location (created_at, alias) VALUES (current_time, 'Atlanta');

insert into stock (product_id, location_id, amount) VALUES (1, 1, 4);
insert into stock (product_id, location_id, amount) VALUES (1, 2, 6);
insert into stock (product_id, location_id, amount) VALUES (1, 3, 2);
insert into stock (product_id, location_id, amount) VALUES (2, 3, 1);
insert into stock (product_id, location_id, amount) VALUES (2, 4, 3);
insert into stock (product_id, location_id, amount) VALUES (3, 4, 8);
insert into stock (product_id, location_id, amount) VALUES (3, 5, 2);
insert into stock (product_id, location_id, amount) VALUES (4, 1, 9);
insert into stock (product_id, location_id, amount) VALUES (4, 3, 4);
insert into stock (product_id, location_id, amount) VALUES (5, 2, 2);
insert into stock (product_id, location_id, amount) VALUES (5, 5, 3);