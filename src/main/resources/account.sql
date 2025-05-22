USE nitiyabank;
CREATE TABLE accountDetails(
                    name VARCHAR(60),
                    mob_number VARCHAR(10),
                    prn_number VARCHAR(12) UNIQUE,
                    pass VARCHAR(20),
                    acc_number VARCHAR(15) UNIQUE,
                    acc_balance FLOAT(10),
                    ifsc_code VARCHAR(11) DEFAULT 'PRSH0009851',
                    bank_name VARCHAR(12) DEFAULT 'NITIYA BANK',
                    freeze_info VARCHAR(3) DEFAULT 'no',
                    date_time VARCHAR(20)
                    );