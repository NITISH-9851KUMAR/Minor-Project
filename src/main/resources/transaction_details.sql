CREATE TABLE transaction
# transaction table Name: Nitish31004, follow this format
# AC/H First Name + List Five Digit of PRN
(
    date_time   VARCHAR(20),
    details     VARCHAR(20),
    debit       FLOAT(10) DEFAULT 0,
    credit      FLOAT(10) DEFAULT 0,
    balance     FLOAT(10)
);