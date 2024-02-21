--h2 is typically used to setup a test database, not a prod database.
--first, drop your tables (to reset your database for testing)
--then create your tables

DROP TABLE PRODUCT if exists;
DROP TABLE SELLER if exists;

CREATE TABLE SELLER (
seller_id int,
seller_name varchar(255),
PRIMARY KEY (seller_id)
);

CREATE TABLE PRODUCT (
product_id int PRIMARY KEY AUTO_INCREMENT,
product_name varchar(255),
seller_name varchar(255),
price decimal
);

INSERT INTO SELLER (seller_id, seller_name)
VALUES
(1, 'Chuck'),
(2, 'Mary'),
(3, 'Susan');
INSERT INTO PRODUCT (product_name, seller_name, price)
VALUES
('widget', 'Chuck', 1.99),
('bobber', 'Susan', 2.50),
('daisy', 'Mary', 5.00);