-- Cities
INSERT INTO city (name) VALUES
('Antananarivo'),
('Toamasina'),
('Mahajanga');

-- Sectors
INSERT INTO sector (name) VALUES
('Agricultural');

-- Domains
INSERT INTO domain (name) VALUES
('Rice Farming'),
('Livestock'),
('Vegetable Farming');

-- Federation
INSERT INTO federation (name) VALUES
('National Agricultural Federation');

INSERT INTO collectivity (
    number, name, creation_date,
    city_id, domain_id, federation_id, sector_id,
    is_authorized
)
VALUES
(1, 'Coop Alpha', '2025-01-10', 1, 1, 1, 1, TRUE);

-- Members
INSERT INTO member (
    last_name, first_name, birth_date, gender, address,
    phone, email, membership_date, profession
) VALUES
('Rakoto', 'Jean', '1995-05-10', 'MALE', 'Antananarivo', '0341234567', 'jean@example.com', '2025-01-15', 'Farmer'),

('Rabe', 'Marie', '1998-08-20', 'FEMALE', 'Toamasina', '0347654321', 'marie@example.com', '2025-02-01', 'Agricultural Engineer');

INSERT INTO membership (member_id, collectivity_id, entry_date, position)
VALUES (1, 1, '2025-01-15', 'PRESIDENT');

-- Contributions
INSERT INTO contribution (
    member_id, collectivity_id, amount, payment_date, payment_method, type
)
VALUES
(1, 1, 50000, '2025-03-01', 'CASH', 'MONTHLY');


---Financial Account
INSERT INTO financial_account (id, collectivity_id, type, amount, holder_name)
VALUES (1, '1', 'CASH', 0.0, 'Caisse collective');
INSERT INTO financial_account (id, collectivity_id, type, amount, holder_name)
VALUES (2, NULL, 'CASH', 0.0, 'Compte Fédération');