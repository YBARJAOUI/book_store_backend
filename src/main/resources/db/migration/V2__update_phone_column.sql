-- Migration pour renommer la colonne phone en phone_number
-- Utiliser seulement si vous voulez changer le nom de colonne dans la DB

-- ALTER TABLE customers CHANGE COLUMN phone phone_number VARCHAR(20) NOT NULL;