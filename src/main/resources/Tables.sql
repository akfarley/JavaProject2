--h2 is typically used to setup a test database, not a prod database.
--first, drop your tables (to reset your database for testing)
--then create your tables

DROP TABLE PRODUCT if exists;
DROP TABLE SELLER if exists;

CREATE TABLE SELLER (
seller_id BIGINT PRIMARY KEY,
seller_name varchar(255)
);

CREATE TABLE PRODUCT (
product_id BIGINT AUTO_INCREMENT PRIMARY KEY,
product_name varchar(255) NOT NULL,
seller_id BIGINT NOT NULL references SELLER(seller_id),
price decimal
);

INSERT INTO SELLER (seller_id, seller_name)
VALUES
(1, 'Chuck'),
(2, 'Mary'),
(3, 'Susan');
INSERT INTO PRODUCT (product_id, product_name, seller_id, price)
VALUES
(1, 'widget', 1, 1.99),
(2, 'bobber', 2, 2.50),
(3, 'keychain', 3, 5.00);