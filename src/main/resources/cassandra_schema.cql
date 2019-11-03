CREATE KEYSPACE IF NOT EXISTS product WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };

CREATE TABLE IF NOT EXISTS product.currentPrice (
  id int primary key,
  value double,
  currencyCode varchar
);

INSERT INTO product.current_price (id, value, currency_code) VALUES (13860428, 13.49, 'USD');