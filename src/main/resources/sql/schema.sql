CREATE TABLE IF NOT EXISTS collectivity (
    id SERIAL PRIMARY KEY,
    number VARCHAR(50),
    name VARCHAR(255),
    location VARCHAR(255),
    creation_date DATE,
    specialization VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS member (
    id VARCHAR(50) PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    collectivity_id VARCHAR(50),
    birth_date DATE,
    gender VARCHAR(10),
    address TEXT,
    profession VARCHAR(255),
    phone VARCHAR(20),
    email VARCHAR(255),
    occupation VARCHAR(30),
    membership_date DATE
);

CREATE TABLE IF NOT EXISTS membership_fee (
    id VARCHAR(50) PRIMARY KEY,
    collectivity_id VARCHAR(50),
    eligible_from DATE,
    frequency VARCHAR(20),
    amount DOUBLE PRECISION,
    label VARCHAR(255),
    status VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS financial_account (
    id VARCHAR(50) PRIMARY KEY,
    collectivity_id VARCHAR(50),
    type VARCHAR(10),
    amount DOUBLE PRECISION DEFAULT 0,
    holder_name VARCHAR(255),
    mobile_service VARCHAR(20),
    mobile_number VARCHAR(20),
    bank_name VARCHAR(20),
    bank_code INT,
    branch_code INT,
    account_number VARCHAR(50),
    rib_key INT
);

CREATE TABLE IF NOT EXISTS member_payment (
    id VARCHAR(50) PRIMARY KEY,
    member_id VARCHAR(50),
    amount INT,
    payment_mode VARCHAR(20),
    membership_fee_id VARCHAR(50),
    creation_date DATE
);

CREATE TABLE IF NOT EXISTS collectivity_transaction (
    id VARCHAR(50) PRIMARY KEY,
    collectivity_id VARCHAR(50),
    creation_date DATE,
    amount DOUBLE PRECISION,
    payment_mode VARCHAR(20),
    account_credited_id VARCHAR(50),
    member_debited_id VARCHAR(50)
);