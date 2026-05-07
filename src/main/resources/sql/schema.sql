DROP TABLE IF EXISTS attendance CASCADE;
DROP TABLE IF EXISTS activity CASCADE;
DROP TABLE IF EXISTS collectivity_transaction CASCADE;
DROP TABLE IF EXISTS member_payment CASCADE;
DROP TABLE IF EXISTS membership_fee CASCADE;
DROP TABLE IF EXISTS financial_account CASCADE;
DROP TABLE IF EXISTS member CASCADE;
DROP TABLE IF EXISTS collectivity CASCADE;

CREATE TABLE collectivity (
    id VARCHAR(50) PRIMARY KEY,
    number VARCHAR(50),
    name VARCHAR(255),
    location VARCHAR(255),
    creation_date DATE,
    specialization VARCHAR(255)
);

CREATE TABLE member (
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

CREATE TABLE membership_fee (
    id VARCHAR(50) PRIMARY KEY,
    collectivity_id VARCHAR(50),
    eligible_from DATE,
    frequency VARCHAR(20),
    amount DOUBLE PRECISION,
    label VARCHAR(255),
    status VARCHAR(20)
);

CREATE TABLE financial_account (
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

CREATE TABLE member_payment (
    id VARCHAR(50) PRIMARY KEY,
    member_id VARCHAR(50),
    amount INT,
    payment_mode VARCHAR(20),
    membership_fee_id VARCHAR(50),
    creation_date DATE
);

CREATE TABLE collectivity_transaction (
    id VARCHAR(50) PRIMARY KEY,
    collectivity_id VARCHAR(50),
    creation_date DATE,
    amount DOUBLE PRECISION,
    payment_mode VARCHAR(20),
    account_credited_id VARCHAR(50),
    member_debited_id VARCHAR(50)
);

CREATE TABLE activity (
    id VARCHAR(50) PRIMARY KEY,
    collectivity_id VARCHAR(50) NOT NULL,
    label VARCHAR(255) NOT NULL,
    activity_type VARCHAR(20) NOT NULL,
    member_occupation_concerned TEXT[],
    executive_date DATE NOT NULL
);

CREATE TABLE attendance (
    id VARCHAR(50) PRIMARY KEY,
    activity_id VARCHAR(50) NOT NULL,
    member_id VARCHAR(50) NOT NULL,
    attendance_status VARCHAR(20) NOT NULL
);

INSERT INTO collectivity (id, number, name, location, creation_date, specialization) VALUES
('col-1', '1', 'Mpanorina', 'Ambatondrazaka', '2026-01-01', 'Riziculture'),
('col-2', '2', 'Ambatondrazaka', 'Brickaville', '2026-01-01', 'Pisciculture'),
('col-3', '3', 'Dobo voalohany', 'Ambatondrazaka', '2026-01-01', 'Apiculture');

INSERT INTO member (id, first_name, last_name, collectivity_id, birth_date, gender, address, profession, phone, email, occupation, membership_date) VALUES
('C1-M1', 'Nom membre 1', 'Prénom membre 1', 'col-1', '1980-02-01', 'MALE', 'Lot II V M Ambato.', 'Riziculteur', '0341234567', 'member.1@fed-agri.mg', 'PRESIDENT', '2026-01-01'),
('C1-M2', 'Nom membre 2', 'Prénom membre 2', 'col-1', '1982-03-05', 'MALE', 'Lot II F Ambato.', 'Agriculteur', '0321234567', 'member.2@fed-agri.mg', 'VICE_PRESIDENT', '2026-01-01'),
('C1-M3', 'Nom membre 3', 'Prénom membre 3', 'col-1', '1992-03-10', 'MALE', 'Lot II J Ambato.', 'Collecteur', '0331234567', 'member.3@fed-agrimg', 'SECRETARY', '2026-01-01'),
('C1-M4', 'Nom membre 4', 'Prénom membre 4', 'col-1', '1988-05-22', 'FEMALE', 'Lot A K 50 Ambato.', 'Distributeur', '0381234567', 'member.4@fed-agri.mg', 'TREASURER', '2026-01-01'),
('C1-M5', 'Nom membre 5', 'Prénom membre 5', 'col-1', '1999-08-21', 'MALE', 'Lot UV 80 Ambato.', 'Riziculteur', '0373434567', 'member.5@fed-agri.mg', 'SENIOR', '2026-01-01'),
('C1-M6', 'Nom membre 6', 'Prénom membre 6', 'col-1', '1998-08-22', 'FEMALE', 'Lot UV 6 Ambato.', 'Riziculteur', '0372234567', 'member.6@fed-agri.mg', 'SENIOR', '2026-01-01'),
('C1-M7', 'Nom membre 7', 'Prénom membre 7', 'col-1', '1998-01-31', 'MALE', 'Lot UV 7 Ambato.', 'Riziculteur', '0374234567', 'member.7@fed-agri.mg', 'SENIOR', '2026-01-01'),
('C1-M8', 'Nom membre 8', 'Prénom membre 6', 'col-1', '1975-08-20', 'MALE', 'Lot UV 8 Ambato.', 'Riziculteur', '0370234567', 'member.8@fed-agri.mg', 'SENIOR', '2026-01-01'),
('C2-M1', 'Nom membre 1', 'Prénom membre 1', 'col-2', '1980-02-01', 'MALE', 'Lot II V M Ambato.', 'Riziculteur', '0341234567', 'member.1@fed-agri.mg', 'SENIOR', '2026-01-01'),
('C2-M2', 'Nom membre 2', 'Prénom membre 2', 'col-2', '1982-03-05', 'MALE', 'Lot II F Ambato.', 'Agriculteur', '0321234567', 'member.2@fed-agri.mg', 'SENIOR', '2026-01-01'),
('C2-M3', 'Nom membre 3', 'Prénom membre 3', 'col-2', '1992-03-10', 'MALE', 'Lot II J Ambato.', 'Collecteur', '0331234567', 'member.3@fed-agrimg', 'SENIOR', '2026-01-01'),
('C2-M4', 'Nom membre 4', 'Prénom membre 4', 'col-2', '1988-05-22', 'FEMALE', 'Lot A K 50 Ambato.', 'Distributeur', '0381234567', 'member.4@fed-agri.mg', 'SENIOR', '2026-01-01'),
('C2-M5', 'Nom membre 5', 'Prénom membre 5', 'col-2', '1999-08-21', 'MALE', 'Lot UV 80 Ambato.', 'Riziculteur', '0373434567', 'member.5@fed-agri.mg', 'PRESIDENT', '2026-01-01'),
('C2-M6', 'Nom membre 6', 'Prénom membre 6', 'col-2', '1998-08-22', 'FEMALE', 'Lot UV 6 Ambato.', 'Riziculteur', '0372234567', 'member.6@fed-agri.mg', 'VICE_PRESIDENT', '2026-01-01'),
('C2-M7', 'Nom membre 7', 'Prénom membre 7', 'col-2', '1998-01-31', 'MALE', 'Lot UV 7 Ambato.', 'Riziculteur', '0374234567', 'member.7@fed-agri.mg', 'SECRETARY', '2026-01-01'),
('C2-M8', 'Nom membre 8', 'Prénom membre 6', 'col-2', '1975-08-20', 'MALE', 'Lot UV 8 Ambato.', 'Riziculteur', '0370234567', 'member.8@fed-agri.mg', 'TREASURER', '2026-01-01'),
('C3-M1', 'Nom membre 9', 'Prénom membre 9', 'col-3', '1988-01-02', 'MALE', 'Lot 33 J Antsirabe', 'Apiculteur', '034034567', 'member.9@fed-agri.mg', 'PRESIDENT', '2026-01-01'),
('C3-M2', 'Nom membre 10', 'Prénom membre 10', 'col-3', '1982-03-05', 'MALE', 'Lot 2 J Antsirabe', 'Agriculteur', '0338634567', 'member.10@fed-agri.mg', 'VICE_PRESIDENT', '2026-01-01'),
('C3-M3', 'Nom membre 11', 'Prénom membre 11', 'col-3', '1992-03-12', 'MALE', 'Lot 8 KM Antsirabe', 'Collecteur', '0338234567', 'member.11@fed-agrimg', 'SECRETARY', '2026-01-01'),
('C3-M4', 'Nom membre 12', 'Prénom membre 12', 'col-3', '1988-05-10', 'FEMALE', 'Lot A K 50 Antsirabe', 'Distributeur', '0382334567', 'member.12@fed-agri.mg', 'TREASURER', '2026-01-01'),
('C3-M5', 'Nom membre 13', 'Prénom membre 13', 'col-3', '1999-08-11', 'MALE', 'Lot UV 80 Antsirabe.', 'Apiculteur', '0373365567', 'member.13@fed-agri.mg', 'SENIOR', '2026-01-01'),
('C3-M6', 'Nom membre 14', 'Prénom membre 14', 'col-3', '1998-08-09', 'FEMALE', 'Lot UV 6 Antsirabe.', 'Apiculteur', '0378234567', 'member.14@fed-agri.mg', 'SENIOR', '2026-01-01'),
('C3-M7', 'Nom membre 15', 'Prénom membre 15', 'col-3', '1998-01-13', 'MALE', 'Lot UV 7 Antsirabe', 'Apiculteur', '0374914567', 'member.15@fed-agri.mg', 'SENIOR', '2026-01-01'),
('C3-M8', 'Nom membre 16', 'Prénom membre 16', 'col-3', '1975-08-02', 'MALE', 'Lot UV 8 Antsirabe', 'Apiculteur', '0370634567', 'member.16@fed-agri.mg', 'SENIOR', '2026-01-01');

INSERT INTO financial_account (id, collectivity_id, type, amount, holder_name, mobile_service, mobile_number, bank_name, bank_code, branch_code, account_number, rib_key) VALUES
('C1-A-CASH', 'col-1', 'CASH', 0, 'Mpanorina', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
('C1-A-MOBILE-1', 'col-1', 'MOBILE', 0, 'Mpanorina', 'ORANGE_MONEY', '0370489612', NULL, NULL, NULL, NULL, NULL),
('C2-A-CASH', 'col-2', 'CASH', 0, 'Dobo voalohany', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
('C2-A-MOBILE-1', 'col-2', 'MOBILE', 0, 'Dobo voalohany', 'ORANGE_MONEY', '0320489612', NULL, NULL, NULL, NULL, NULL),
('C3-A-CASH', 'col-3', 'CASH', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

INSERT INTO financial_account (id, collectivity_id, type, amount, holder_name, mobile_service, mobile_number, bank_name, bank_code, branch_code, account_number, rib_key) VALUES
('C3-A-BANK-1', 'col-3', 'BANK', 0, 'Koto', NULL, NULL, 'BMOI', 4, 1, '1234567890', 12),
('C3-A-BANK-2', 'col-3', 'BANK', 0, 'Naivo', NULL, NULL, 'BRED', 8, 3, '4567890123', 58),
('C3-A-MOBILE-1', 'col-3', 'MOBILE', 0, NULL, 'MVOLA', '0341889612', NULL, NULL, NULL, NULL, NULL);

INSERT INTO membership_fee (id, collectivity_id, eligible_from, frequency, amount, label, status) VALUES
('cot-1', 'col-1', '2026-01-01', 'ANNUALLY', 200000, 'Cotisation annuelle', 'ACTIVE'),
('cot-2', 'col-1', '2026-04-30', 'PUNCTUALLY', 20000, 'Famangiana', 'ACTIVE'),
('cot-3', 'col-2', '2026-01-01', 'ANNUALLY', 200000, 'Cotisation annuelle', 'ACTIVE'),
('cot-4', 'col-2', '2025-01-01', 'ANNUALLY', 100000, 'Cotisation 2025', 'INACTIVE'),
('cot-5', 'col-3', '2026-04-01', 'MONTHLY', 25000, 'Cotisation mensuelle', 'ACTIVE');

INSERT INTO member_payment (id, member_id, amount, payment_mode, membership_fee_id, creation_date) VALUES
(gen_random_uuid()::text, 'C1-M1', 200000, 'CASH', 'cot-1', '2026-01-01'),
(gen_random_uuid()::text, 'C1-M2', 200000, 'CASH', 'cot-1', '2026-01-01'),
(gen_random_uuid()::text, 'C1-M3', 200000, 'MOBILE_BANKING', 'cot-1', '2026-01-01'),
(gen_random_uuid()::text, 'C1-M4', 200000, 'MOBILE_BANKING', 'cot-1', '2026-01-01'),
(gen_random_uuid()::text, 'C1-M5', 150000, 'MOBILE_BANKING', 'cot-1', '2026-01-01'),
(gen_random_uuid()::text, 'C1-M6', 100000, 'CASH', 'cot-1', '2026-05-01'),
(gen_random_uuid()::text, 'C1-M7', 60000, 'CASH', 'cot-1', '2026-05-01'),
(gen_random_uuid()::text, 'C1-M8', 90000, 'CASH', 'cot-1', '2026-05-01'),
(gen_random_uuid()::text, 'C2-M1', 120000, 'CASH', 'cot-3', '2026-01-01'),
(gen_random_uuid()::text, 'C2-M2', 180000, 'CASH', 'cot-3', '2026-01-01'),
(gen_random_uuid()::text, 'C2-M3', 200000, 'CASH', 'cot-3', '2026-01-01'),
(gen_random_uuid()::text, 'C2-M4', 200000, 'CASH', 'cot-3', '2026-01-01'),
(gen_random_uuid()::text, 'C2-M5', 200000, 'CASH', 'cot-3', '2026-01-01'),
(gen_random_uuid()::text, 'C2-M6', 200000, 'CASH', 'cot-3', '2026-01-01'),
(gen_random_uuid()::text, 'C2-M7', 80000, 'MOBILE_BANKING', 'cot-3', '2026-01-01'),
(gen_random_uuid()::text, 'C2-M8', 120000, 'MOBILE_BANKING', 'cot-3', '2026-01-01'),
(gen_random_uuid()::text, 'C3-M1', 25000, 'BANK_TRANSFER', 'cot-5', '2026-04-01'),
(gen_random_uuid()::text, 'C3-M2', 25000, 'BANK_TRANSFER', 'cot-5', '2026-04-01'),
(gen_random_uuid()::text, 'C3-M3', 25000, 'BANK_TRANSFER', 'cot-5', '2026-04-01'),
(gen_random_uuid()::text, 'C3-M4', 25000, 'BANK_TRANSFER', 'cot-5', '2026-04-01'),
(gen_random_uuid()::text, 'C3-M5', 25000, 'BANK_TRANSFER', 'cot-5', '2026-04-01'),
(gen_random_uuid()::text, 'C3-M6', 25000, 'BANK_TRANSFER', 'cot-5', '2026-04-01'),
(gen_random_uuid()::text, 'C3-M7', 25000, 'CASH', 'cot-5', '2026-04-01'),
(gen_random_uuid()::text, 'C3-M8', 25000, 'CASH', 'cot-5', '2026-04-01'),
(gen_random_uuid()::text, 'C3-M1', 25000, 'BANK_TRANSFER', 'cot-5', '2026-05-01'),
(gen_random_uuid()::text, 'C3-M2', 25000, 'BANK_TRANSFER', 'cot-5', '2026-05-01'),
(gen_random_uuid()::text, 'C3-M3', 15000, 'BANK_TRANSFER', 'cot-5', '2026-05-01'),
(gen_random_uuid()::text, 'C3-M4', 15000, 'BANK_TRANSFER', 'cot-5', '2026-05-01'),
(gen_random_uuid()::text, 'C3-M5', 20000, 'BANK_TRANSFER', 'cot-5', '2026-05-01'),
(gen_random_uuid()::text, 'C3-M6', 25000, 'BANK_TRANSFER', 'cot-5', '2026-05-01'),
(gen_random_uuid()::text, 'C3-M7', 5000, 'CASH', 'cot-5', '2026-05-01'),
(gen_random_uuid()::text, 'C3-M8', 5000, 'CASH', 'cot-5', '2026-05-01');

INSERT INTO collectivity_transaction (id, collectivity_id, creation_date, amount, payment_mode, account_credited_id, member_debited_id) VALUES
(gen_random_uuid()::text, 'col-1', '2026-01-01', 200000, 'CASH', 'C1-A-CASH', 'C1-M1'),
(gen_random_uuid()::text, 'col-1', '2026-01-01', 200000, 'CASH', 'C1-A-CASH', 'C1-M2'),
(gen_random_uuid()::text, 'col-1', '2026-01-01', 200000, 'MOBILE_BANKING', 'C1-A-MOBILE-1', 'C1-M3'),
(gen_random_uuid()::text, 'col-1', '2026-01-01', 200000, 'MOBILE_BANKING', 'C1-A-MOBILE-1', 'C1-M4'),
(gen_random_uuid()::text, 'col-1', '2026-01-01', 150000, 'MOBILE_BANKING', 'C1-A-MOBILE-1', 'C1-M5'),
(gen_random_uuid()::text, 'col-1', '2026-05-01', 100000, 'CASH', 'C1-A-CASH', 'C1-M6'),
(gen_random_uuid()::text, 'col-1', '2026-05-01', 60000, 'CASH', 'C1-A-CASH', 'C1-M7'),
(gen_random_uuid()::text, 'col-1', '2026-05-01', 90000, 'CASH', 'C1-A-CASH', 'C1-M8'),
(gen_random_uuid()::text, 'col-2', '2026-01-01', 120000, 'CASH', 'C2-A-CASH', 'C2-M1'),
(gen_random_uuid()::text, 'col-2', '2026-01-01', 180000, 'CASH', 'C2-A-CASH', 'C2-M2'),
(gen_random_uuid()::text, 'col-2', '2026-01-01', 200000, 'CASH', 'C2-A-CASH', 'C2-M3'),
(gen_random_uuid()::text, 'col-2', '2026-01-01', 200000, 'CASH', 'C2-A-CASH', 'C2-M4'),
(gen_random_uuid()::text, 'col-2', '2026-01-01', 200000, 'CASH', 'C2-A-CASH', 'C2-M5'),
(gen_random_uuid()::text, 'col-2', '2026-01-01', 200000, 'CASH', 'C2-A-CASH', 'C2-M6'),
(gen_random_uuid()::text, 'col-2', '2026-01-01', 80000, 'MOBILE_BANKING', 'C2-A-MOBILE-1', 'C2-M7'),
(gen_random_uuid()::text, 'col-2', '2026-01-01', 120000, 'MOBILE_BANKING', 'C2-A-MOBILE-1', 'C2-M8'),
(gen_random_uuid()::text, 'col-3', '2026-04-01', 25000, 'BANK_TRANSFER', 'C3-A-BANK-1', 'C3-M1'),
(gen_random_uuid()::text, 'col-3', '2026-04-01', 25000, 'BANK_TRANSFER', 'C3-A-BANK-1', 'C3-M2'),
(gen_random_uuid()::text, 'col-3', '2026-04-01', 25000, 'BANK_TRANSFER', 'C3-A-BANK-1', 'C3-M3'),
(gen_random_uuid()::text, 'col-3', '2026-04-01', 25000, 'BANK_TRANSFER', 'C3-A-BANK-1', 'C3-M4'),
(gen_random_uuid()::text, 'col-3', '2026-04-01', 25000, 'BANK_TRANSFER', 'C3-A-BANK-2', 'C3-M5'),
(gen_random_uuid()::text, 'col-3', '2026-04-01', 25000, 'BANK_TRANSFER', 'C3-A-BANK-2', 'C3-M6'),
(gen_random_uuid()::text, 'col-3', '2026-04-01', 25000, 'CASH', 'C3-A-CASH', 'C3-M7'),
(gen_random_uuid()::text, 'col-3', '2026-04-01', 25000, 'CASH', 'C3-A-CASH', 'C3-M8'),
(gen_random_uuid()::text, 'col-3', '2026-05-01', 25000, 'BANK_TRANSFER', 'C3-A-BANK-1', 'C3-M1'),
(gen_random_uuid()::text, 'col-3', '2026-05-01', 25000, 'BANK_TRANSFER', 'C3-A-BANK-1', 'C3-M2'),
(gen_random_uuid()::text, 'col-3', '2026-05-01', 15000, 'BANK_TRANSFER', 'C3-A-MOBILE-1', 'C3-M3'),
(gen_random_uuid()::text, 'col-3', '2026-05-01', 15000, 'BANK_TRANSFER', 'C3-A-MOBILE-1', 'C3-M4'),
(gen_random_uuid()::text, 'col-3', '2026-05-01', 20000, 'BANK_TRANSFER', 'C3-A-BANK-2', 'C3-M5'),
(gen_random_uuid()::text, 'col-3', '2026-05-01', 25000, 'BANK_TRANSFER', 'C3-A-BANK-2', 'C3-M6'),
(gen_random_uuid()::text, 'col-3', '2026-05-01', 5000, 'CASH', 'C3-A-CASH', 'C3-M7'),
(gen_random_uuid()::text, 'col-3', '2026-05-01', 5000, 'CASH', 'C3-A-CASH', 'C3-M8');

INSERT INTO member (id, first_name, last_name, collectivity_id, birth_date, gender, address, profession, phone, email, occupation, membership_date) VALUES
('NEW-C1-1', 'Nouveau1', 'Adhérent1', 'col-1', '2000-01-01', 'MALE', 'Adresse', 'Métier', '034000001', 'new1@ex.com', 'JUNIOR', '2026-04-01'),
('NEW-C1-2', 'Nouveau2', 'Adhérent2', 'col-1', '2000-01-02', 'FEMALE', 'Adresse', 'Métier', '034000002', 'new2@ex.com', 'JUNIOR', '2026-04-01'),
('NEW-C1-3', 'Nouveau3', 'Adhérent3', 'col-1', '2000-01-03', 'MALE', 'Adresse', 'Métier', '034000003', 'new3@ex.com', 'JUNIOR', '2026-05-01'),
('NEW-C1-4', 'Nouveau4', 'Adhérent4', 'col-1', '2000-01-04', 'FEMALE', 'Adresse', 'Métier', '034000004', 'new4@ex.com', 'JUNIOR', '2026-06-01'),
('NEW-C2-1', 'NouveauC2-1', 'AdhérentC2-1', 'col-2', '2000-01-01', 'MALE', 'Adresse', 'Métier', '034000101', 'newc21@ex.com', 'JUNIOR', '2026-03-01'),
('NEW-C2-2', 'NouveauC2-2', 'AdhérentC2-2', 'col-2', '2000-01-02', 'FEMALE', 'Adresse', 'Métier', '034000102', 'newc22@ex.com', 'JUNIOR', '2026-03-01'),
('NEW-C2-3', 'NouveauC2-3', 'AdhérentC2-3', 'col-2', '2000-01-03', 'MALE', 'Adresse', 'Métier', '034000103', 'newc23@ex.com', 'JUNIOR', '2026-03-01'),
('NEW-C3-1', 'NouveauC3-1', 'AdhérentC3-1', 'col-3', '2000-01-01', 'MALE', 'Adresse', 'Métier', '034000201', 'newc31@ex.com', 'JUNIOR', '2026-01-01'),
('NEW-C3-2', 'NouveauC3-2', 'AdhérentC3-2', 'col-3', '2000-01-02', 'FEMALE', 'Adresse', 'Métier', '034000202', 'newc32@ex.com', 'JUNIOR', '2026-02-01'),
('NEW-C3-3', 'NouveauC3-3', 'AdhérentC3-3', 'col-3', '2000-01-03', 'MALE', 'Adresse', 'Métier', '034000203', 'newc33@ex.com', 'JUNIOR', '2026-02-01'),
('NEW-C3-4', 'NouveauC3-4', 'AdhérentC3-4', 'col-3', '2000-01-04', 'FEMALE', 'Adresse', 'Métier', '034000204', 'newc34@ex.com', 'JUNIOR', '2026-03-01'),
('NEW-C3-5', 'NouveauC3-5', 'AdhérentC3-5', 'col-3', '2000-01-05', 'MALE', 'Adresse', 'Métier', '034000205', 'newc35@ex.com', 'JUNIOR', '2026-03-01'),
('NEW-C3-6', 'NouveauC3-6', 'AdhérentC3-6', 'col-3', '2000-01-06', 'FEMALE', 'Adresse', 'Métier', '034000206', 'newc36@ex.com', 'JUNIOR', '2026-03-01');

INSERT INTO activity (id, collectivity_id, label, activity_type, member_occupation_concerned, executive_date) VALUES
(gen_random_uuid()::text, 'col-1', 'Assemblée générale', 'MEETING', ARRAY['PRESIDENT','SECRETARY','TREASURER'], '2026-05-10'),
(gen_random_uuid()::text, 'col-1', 'Formation juniors', 'TRAINING', ARRAY['JUNIOR'], '2026-05-15'),
(gen_random_uuid()::text, 'col-2', 'Réunion de collectivité 2', 'MEETING', ARRAY['ALL'], '2026-05-12');