USE midtermproject;

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
);

CREATE TABLE users_table (
	id INT NOT NULL AUTO_INCREMENT,
    pOwner VARCHAR(255),
    name_user VARCHAR(255),
    username_user VARCHAR(255),
    password_user VARCHAR(255),
    PRIMARY KEY (id),
    FOREIGN KEY (pOwner) REFERENCES account_table (pOwner_account)
);

