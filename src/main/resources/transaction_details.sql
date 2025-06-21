CREATE TABLE transaction
(
    date_time   VARCHAR(20),
    details     VARCHAR(20),
    debit       FLOAT(10) DEFAULT 0,
    credit      FLOAT(10) DEFAULT 0,
    balance     FLOAT(10)
);