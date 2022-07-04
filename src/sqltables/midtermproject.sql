USE midtermproject;

CREATE TABLE users_table (
id INT NOT NULL AUTO_INCREMENT,
    pOwner VARCHAR(255),
    name_user VARCHAR(255),
    username_user VARCHAR(255),
    password_user VARCHAR(255),
    PRIMARY KEY (id),
);

CREATE TABLE role_table (
    id_role BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    name VARCHAR(255),
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES user_table (id)
);

CREATE TABLE admin_table (
id_admin INT,
name_admin VARCHAR (255),
PRIMARY KEY (id),
FOREIGN KEY (id_admin) REFERENCES user_table (id)
);

CREATE TABLE account_holder_table (
id_account_holder INT,
name_account_holder VARCHAR (255),
date_of_birth DATE,
mailing_address VARCHAR (255),
PRIMARY KEY (id),
FOREIGN KEY (id) REFERENCES users_table (id)
);

CREATE TABLE account_table (
id_account INT NOT NULL AUTO_INCREMENT,
pOwner_account VARCHAR(255),
sOwner_account VARCHAR(255),
createdDate_account DATE,
balance_account DECIMAL,
penaltyFee_account DECIMAL,
monthlyMaintenanceFee_account DECIMAL,
status ENUM ("FROZEN", "ACTIVE"),
PRIMARY KEY (id_account)
FOREIGN KEY (pOwner_account) REFERENCES account_holder_table (id_account),
FOREIGN KEY (sOwner_account) REFERENCES account_holder_table (id_account)
);

CREATE TABLE checking_table (
id_checking BIGINT,
date_maintenance_fee DATE,
PRIMARY KEY (id_checking),
FOREIGN KEY (id_checking) REFERENCES account_table (id_account)
);

CREATE TABLE student_checking (
id_student_checking INT,
PRIMARY KEY (id_student_checking),
FOREIGN KEY (id_student_checking) REFERENCES account_table (id_account)
);

CREATE TABLE saving_table (
id_saving_table INT,
interest_rate DECIMAL (19,6),
interest_add_date DATE,
PRIMARY KEY (id_saving_table),
FOREIGN KEY (id_saving_table) REFERENCES account_table (id_account)
);

CREATE TABLE credit_card_table (
id_credit_card INT,
credit_limit_amount DECIMAL,
credit_limit_currency VARCHAR(255),
interest_rate DECIMAL,
interest_add_date DATE,
PRIMARY KEY (id_credit_card),
FOREIGN KEY (id_credit_card) REFERENCES account_table (id_account)
);
