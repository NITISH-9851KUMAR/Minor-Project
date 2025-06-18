CREATE TABLE transaction
(
    sr_no       INT(3),
    date_time   VARCHAR(20),
    details     VARCHAR(20),
    debit       FLOAT(10) DEFAULT 0,
    credit      FLOAT(10) DEFAULT 0,
    balance     FLOAT(10),
    fine_charge FLOAT(10) DEFAULT 0,
    CONSTRAINT transaction_pk PRIMARY KEY (sr_no)
);