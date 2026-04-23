INSERT INTO collectivity (id, number, name, location, creation_date, specialization) VALUES
(1, 'col-1', 'Mpanorina', 'Ambatondrazaka', '2026-01-01', 'Riziculture'),
(2, 'col-2', 'Ambatondrazaka', 'Brickaville', '2026-01-01', 'Pisciculture'),
(3, 'col-3', 'Dobo voalohany', 'Ambatondrazaka', '2026-01-01', 'Apiculture');

INSERT INTO member (id, first_name, last_name, collectivity_id, birth_date, gender, address, profession, phone, email, occupation, membership_date) VALUES
(1, 'Nom membre 1', 'Prénom membre 1', 1, '1980-02-01', 'MALE', 'Lot II V M Ambato.', 'Riziculteur', '0341234567', 'member.1@fed-agri.mg', 'PRESIDENT', '2026-01-01'),
(2, 'Nom membre 2', 'Prénom membre 2', 1, '1982-03-05', 'MALE', 'Lot II F Ambato.', 'Agriculteur', '0321234567', 'member.2@fed-agri.mg', 'VICE_PRESIDENT', '2026-01-01'),
(3, 'Nom membre 3', 'Prénom membre 3', 1, '1992-03-10', 'MALE', 'Lot II J Ambato.', 'Collecteur', '0331234567', 'member.3@fed-agrimg', 'SECRETARY', '2026-01-01'),
(4, 'Nom membre 4', 'Prénom membre 4', 1, '1988-05-22', 'FEMALE', 'Lot A K 50 Ambato.', 'Distributeur', '0381234567', 'member.4@fed-agri.mg', 'TREASURER', '2026-01-01'),
(5, 'Nom membre 5', 'Prénom membre 5', 1, '1999-08-21', 'MALE', 'Lot UV 80 Ambato.', 'Riziculteur', '0373434567', 'member.5@fed-agri.mg', 'SENIOR', '2026-01-01'),
(6, 'Nom membre 6', 'Prénom membre 6', 1, '1998-08-22', 'FEMALE', 'Lot UV 6 Ambato.', 'Riziculteur', '0372234567', 'member.6@fed-agri.mg', 'SENIOR', '2026-01-01'),
(7, 'Nom membre 7', 'Prénom membre 7', 1, '1998-01-31', 'MALE', 'Lot UV 7 Ambato.', 'Riziculteur', '0374234567', 'member.7@fed-agri.mg', 'SENIOR', '2026-01-01'),
(8, 'Nom membre 8', 'Prénom membre 6', 1, '1975-08-20', 'MALE', 'Lot UV 8 Ambato.', 'Riziculteur', '0370234567', 'member.8@fed-agri.mg', 'SENIOR', '2026-01-01'),
(9, 'Nom membre 1', 'Prénom membre 1', 2, '1980-02-01', 'MALE', 'Lot II V M Ambato.', 'Riziculteur', '0341234567', 'member.1@fed-agri.mg', 'SENIOR', '2026-01-01'),
(10, 'Nom membre 2', 'Prénom membre 2', 2, '1982-03-05', 'MALE', 'Lot II F Ambato.', 'Agriculteur', '0321234567', 'member.2@fed-agri.mg', 'SENIOR', '2026-01-01'),
(11, 'Nom membre 3', 'Prénom membre 3', 2, '1992-03-10', 'MALE', 'Lot II J Ambato.', 'Collecteur', '0331234567', 'member.3@fed-agrimg', 'SENIOR', '2026-01-01'),
(12, 'Nom membre 4', 'Prénom membre 4', 2, '1988-05-22', 'FEMALE', 'Lot A K 50 Ambato.', 'Distributeur', '0381234567', 'member.4@fed-agri.mg', 'SENIOR', '2026-01-01'),
(13, 'Nom membre 5', 'Prénom membre 5', 2, '1999-08-21', 'MALE', 'Lot UV 80 Ambato.', 'Riziculteur', '0373434567', 'member.5@fed-agri.mg', 'PRESIDENT', '2026-01-01'),
(14, 'Nom membre 6', 'Prénom membre 6', 2, '1998-08-22', 'FEMALE', 'Lot UV 6 Ambato.', 'Riziculteur', '0372234567', 'member.6@fed-agri.mg', 'VICE_PRESIDENT', '2026-01-01'),
(15, 'Nom membre 7', 'Prénom membre 7', 2, '1998-01-31', 'MALE', 'Lot UV 7 Ambato.', 'Riziculteur', '0374234567', 'member.7@fed-agri.mg', 'SECRETARY', '2026-01-01'),
(16, 'Nom membre 8', 'Prénom membre 6', 2, '1975-08-20', 'MALE', 'Lot UV 8 Ambato.', 'Riziculteur', '0370234567', 'member.8@fed-agri.mg', 'TREASURER', '2026-01-01'),
(17, 'Nom membre 9', 'Prénom membre 9', 3, '1988-01-02', 'MALE', 'Lot 33 J Antsirabe', 'Apiculteur', '034034567', 'member.9@fed-agri.mg', 'PRESIDENT', '2026-01-01'),
(18, 'Nom membre 10', 'Prénom membre 10', 3, '1982-03-05', 'MALE', 'Lot 2 J Antsirabe', 'Agriculteur', '0338634567', 'member.10@fed-agri.mg', 'VICE_PRESIDENT', '2026-01-01'),
(19, 'Nom membre 11', 'Prénom membre 11', 3, '1992-03-12', 'MALE', 'Lot 8 KM Antsirabe', 'Collecteur', '0338234567', 'member.11@fed-agrimg', 'SECRETARY', '2026-01-01'),
(20, 'Nom membre 12', 'Prénom membre 12', 3, '1988-05-10', 'FEMALE', 'Lot A K 50 Antsirabe', 'Distributeur', '0382334567', 'member.12@fed-agri.mg', 'TREASURER', '2026-01-01'),
(21, 'Nom membre 13', 'Prénom membre 13', 3, '1999-08-11', 'MALE', 'Lot UV 80 Antsirabe.', 'Apiculteur', '0373365567', 'member.13@fed-agri.mg', 'SENIOR', '2026-01-01'),
(22, 'Nom membre 14', 'Prénom membre 14', 3, '1998-08-09', 'FEMALE', 'Lot UV 6 Antsirabe.', 'Apiculteur', '0378234567', 'member.14@fed-agri.mg', 'SENIOR', '2026-01-01'),
(23, 'Nom membre 15', 'Prénom membre 15', 3, '1998-01-13', 'MALE', 'Lot UV 7 Antsirabe', 'Apiculteur', '0374914567', 'member.15@fed-agri.mg', 'SENIOR', '2026-01-01'),
(24, 'Nom membre 16', 'Prénom membre 16', 3, '1975-08-02', 'MALE', 'Lot UV 8 Antsirabe', 'Apiculteur', '0370634567', 'member.16@fed-agri.mg', 'SENIOR', '2026-01-01');

INSERT INTO membership_fee (id, collectivity_id, eligible_from, frequency, amount, label, status) VALUES
(1, 1, '2026-01-01', 'ANNUALLY', 100000, 'Cotisation annuelle', 'ACTIVE'),
(2, 2, '2026-01-01', 'ANNUALLY', 100000, 'Cotisation annuelle', 'ACTIVE'),
(3, 3, '2026-01-01', 'ANNUALLY', 50000, 'Cotisation annuelle', 'ACTIVE');

INSERT INTO financial_account (id, collectivity_id, type, amount, holder_name, mobile_service, mobile_number) VALUES
(1, 1, 'CASH', 0, 'Mpanorina', NULL, NULL),
(2, 1, 'MOBILE', 0, 'Mpanorina', 'ORANGE_MONEY', '0370489612'),
(3, 2, 'CASH', 0, 'Dobo voalohany', NULL, NULL),
(4, 2, 'MOBILE', 0, 'Dobo voalohany', 'ORANGE_MONEY', '0320489612'),
(5, 3, 'CASH', 0, 'Dobo voalohany', NULL, NULL);

INSERT INTO member_payment (id, member_id, amount, payment_mode, membership_fee_id, creation_date) VALUES
(1, 1, 100000, 'CASH', 1, '2026-01-01'),
(2, 2, 100000, 'CASH', 1, '2026-01-01'),
(3, 3, 100000, 'CASH', 1, '2026-01-01'),
(4, 4, 100000, 'CASH', 1, '2026-01-01'),
(5, 5, 100000, 'CASH', 1, '2026-01-01'),
(6, 6, 100000, 'CASH', 1, '2026-01-01'),
(7, 7, 60000, 'CASH', 1, '2026-01-01'),
(8, 8, 90000, 'CASH', 1, '2026-01-01'),
(9, 9, 60000, 'CASH', 2, '2026-01-01'),
(10, 10, 90000, 'CASH', 2, '2026-01-01'),
(11, 11, 100000, 'CASH', 2, '2026-01-01'),
(12, 12, 100000, 'CASH', 2, '2026-01-01'),
(13, 13, 100000, 'CASH', 2, '2026-01-01'),
(14, 14, 100000, 'CASH', 2, '2026-01-01'),
(15, 15, 40000, 'MOBILE_BANKING', 2, '2026-01-01'),
(16, 16, 60000, 'MOBILE_BANKING', 2, '2026-01-01');

INSERT INTO collectivity_transaction (id, collectivity_id, creation_date, amount, payment_mode, account_credited_id, member_debited_id) VALUES
(1, 1, '2026-01-01', 100000, 'CASH', 1, 1),
(2, 1, '2026-01-01', 100000, 'CASH', 1, 2),
(3, 1, '2026-01-01', 100000, 'CASH', 1, 3),
(4, 1, '2026-01-01', 100000, 'CASH', 1, 4),
(5, 1, '2026-01-01', 100000, 'CASH', 1, 5),
(6, 1, '2026-01-01', 100000, 'CASH', 1, 6),
(7, 1, '2026-01-01', 60000, 'CASH', 1, 7),
(8, 1, '2026-01-01', 90000, 'CASH', 1, 8),
(9, 2, '2026-01-01', 60000, 'CASH', 3, 9),
(10, 2, '2026-01-01', 90000, 'CASH', 3, 10),
(11, 2, '2026-01-01', 100000, 'CASH', 3, 11),
(12, 2, '2026-01-01', 100000, 'CASH', 3, 12),
(13, 2, '2026-01-01', 100000, 'CASH', 3, 13),
(14, 2, '2026-01-01', 100000, 'CASH', 3, 14),
(15, 2, '2026-01-01', 40000, 'MOBILE_BANKING', 4, 15),
(16, 2, '2026-01-01', 60000, 'MOBILE_BANKING', 4, 16);

UPDATE financial_account SET amount = (
    SELECT COALESCE(SUM(amount), 0) FROM collectivity_transaction WHERE account_credited_id = financial_account.id
);