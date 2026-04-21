CREATE TYPE gender_enum AS ENUM ('MALE', 'FEMALE');
CREATE TYPE profession_enum AS ENUM ('PRODUCTEUR', 'COLLECTEUR', 'AUTRE');
CREATE TYPE occupation_enum AS ENUM ('JUNIOR','SENIOR','SECRETARY','TREASURER','VICE_PRESIDENT','PRESIDENT');

CREATE TABLE city (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE sector (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL DEFAULT 'Agricultural'
);

CREATE TABLE domain (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE federation (
    id SERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL
);

-- =========================
-- COLLECTIVITY
-- =========================

CREATE TABLE collectivity (
    id SERIAL PRIMARY KEY,
    number INT UNIQUE NOT NULL,
    name VARCHAR(150) UNIQUE NOT NULL,
    creation_date DATE NOT NULL,
    city_id INT NOT NULL REFERENCES city(id),
    domain_id INT NOT NULL REFERENCES domain(id),
    federation_id INT REFERENCES federation(id),
    sector_id INT REFERENCES sector(id),
    is_authorized BOOLEAN DEFAULT FALSE
);

-- =========================
-- MEMBER
-- =========================

CREATE TABLE member (
    id SERIAL PRIMARY KEY,
    last_name VARCHAR(100),
    first_name VARCHAR(100),
    birth_date DATE,
    gender gender_enum,
    address VARCHAR(255),
    phone VARCHAR(20) UNIQUE,
    email VARCHAR(100) UNIQUE,
    membership_date DATE,
    occupation member_occupation
);

-- =========================
-- PARENTING / REFERRAL
-- =========================

CREATE TABLE referral (
    id SERIAL PRIMARY KEY,
    referrer_id INT NOT NULL REFERENCES member(id),
    referred_id INT NOT NULL REFERENCES member(id),
    referral_date DATE,
    CONSTRAINT chk_referral_diff CHECK (referrer_id <> referred_id)
);

-- =========================
-- MEMBERSHIP
-- =========================

CREATE TABLE membership (
    id SERIAL PRIMARY KEY,
    member_id INT NOT NULL REFERENCES member(id),
    collectivity_id INT NOT NULL REFERENCES collectivity(id),
    entry_date DATE NOT NULL,
    exit_date DATE,
    position VARCHAR(30)
);

-- =========================
-- MANDATE
-- =========================

CREATE TABLE mandate (
    id SERIAL PRIMARY KEY,
    year INT NOT NULL,
    duration INT DEFAULT 1
);

CREATE TABLE mandate_position (
    id SERIAL PRIMARY KEY,
    mandate_id INT NOT NULL REFERENCES mandate(id),
    member_id INT NOT NULL REFERENCES member(id),
    collectivity_id INT NOT NULL REFERENCES collectivity(id),
    position VARCHAR(30),
    UNIQUE (collectivity_id, mandate_id, position)
);

-- =========================
-- ACTIVITIES
-- =========================

CREATE TABLE activity (
    id SERIAL PRIMARY KEY,
    title VARCHAR(150),
    type VARCHAR(50),
    activity_date DATE,
    mandatory BOOLEAN DEFAULT FALSE,
    collectivity_id INT REFERENCES collectivity(id),
    federation_id INT REFERENCES federation(id)
);

CREATE TABLE attendance (
    id SERIAL PRIMARY KEY,
    member_id INT NOT NULL REFERENCES member(id),
    activity_id INT NOT NULL REFERENCES activity(id),
    present BOOLEAN DEFAULT TRUE,
    excused BOOLEAN DEFAULT FALSE
);

-- =========================
-- FINANCE
-- =========================

CREATE TABLE contribution (
    id SERIAL PRIMARY KEY,
    member_id INT NOT NULL REFERENCES member(id),
    collectivity_id INT NOT NULL REFERENCES collectivity(id),
    amount DECIMAL(15,2) NOT NULL,
    payment_date DATE,
    payment_method VARCHAR(30),
    type VARCHAR(30)
);

-- =========================
-- ACCOUNTS
-- =========================

CREATE TABLE account (
    id SERIAL PRIMARY KEY,
    type VARCHAR(30),
    balance DECIMAL(15,2) DEFAULT 0,
    collectivity_id INT REFERENCES collectivity(id),
    federation_id INT REFERENCES federation(id)
);

CREATE TABLE bank_account (
    id INT PRIMARY KEY REFERENCES account(id),
    holder VARCHAR(150),
    bank VARCHAR(50),
    account_number VARCHAR(30)
);

CREATE TABLE mobile_money_account (
    id INT PRIMARY KEY REFERENCES account(id),
    holder VARCHAR(150),
    provider VARCHAR(50),
    phone_number VARCHAR(20)
);