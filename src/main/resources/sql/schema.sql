CREATE TABLE collectivity (
    id SERIAL PRIMARY KEY,
    number VARCHAR(50),
    name VARCHAR(255),
    location VARCHAR(255),
    creation_date DATE,
    specialization VARCHAR(255)
);

CREATE TABLE member (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    collectivity_id INTEGER,
    birth_date DATE,
    gender VARCHAR(10),
    address TEXT,
    profession VARCHAR(255),
    phone VARCHAR(20),
    email VARCHAR(255),
    occupation VARCHAR(30),
    membership_date DATE
);

CREATE TABLE membership_fee (
    id SERIAL PRIMARY KEY,
    collectivity_id INTEGER,
    eligible_from DATE,
    frequency VARCHAR(20),
    amount DOUBLE PRECISION,
    label VARCHAR(255),
    status VARCHAR(20)
);

CREATE TABLE financial_account (
    id SERIAL PRIMARY KEY,
    collectivity_id INTEGER,
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

CREATE TABLE member_payment (
    id SERIAL PRIMARY KEY,
    member_id INTEGER,
    amount INT,
    payment_mode VARCHAR(20),
    membership_fee_id INTEGER,
    creation_date DATE
);

CREATE TABLE collectivity_transaction (
    id SERIAL PRIMARY KEY,
    collectivity_id INTEGER,
    creation_date DATE,
    amount DOUBLE PRECISION,
    payment_mode VARCHAR(20),
    account_credited_id INTEGER,
    member_debited_id INTEGER
);

DROP TABLE IF EXISTS attendance CASCADE;
DROP TABLE IF EXISTS activity CASCADE;

CREATE TABLE activity (
    id SERIAL PRIMARY KEY,
    collectivity_id INTEGER NOT NULL,
    label VARCHAR(255) NOT NULL,
    activity_type VARCHAR(20) NOT NULL,
    member_occupation_concerned TEXT[],
    executive_date DATE NOT NULL
);

CREATE TABLE attendance (
    id SERIAL PRIMARY KEY,
    activity_id INTEGER NOT NULL,
    member_id INTEGER NOT NULL,
    attendance_status VARCHAR(20) NOT NULL,
    CONSTRAINT fk_attendance_activity FOREIGN KEY (activity_id) REFERENCES activity(id),
    CONSTRAINT fk_attendance_member FOREIGN KEY (member_id) REFERENCES member(id)
);