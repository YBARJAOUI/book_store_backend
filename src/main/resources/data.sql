-- Insertion de données de test pour la table books
-- Note: Ce fichier est utilisé par Spring Boot pour initialiser les données au démarrage

-- Désactiver les contraintes de clés étrangères temporairement
SET FOREIGN_KEY_CHECKS = 0;

-- Nettoyer les tables si elles existent déjà
TRUNCATE TABLE order_items;
TRUNCATE TABLE orders;
TRUNCATE TABLE books;
TRUNCATE TABLE customers;

-- Réactiver les contraintes de clés étrangères
SET FOREIGN_KEY_CHECKS = 1;

-- Insérer des livres de test
INSERT INTO books (isbn, title, author, description, price, stock, image_path, is_available, created_at, updated_at) VALUES
('978-2-7654-1234-5', 'Le Petit Prince', 'Antoine de Saint-Exupéry', 'Un pilote s''écrase dans le désert du Sahara. Mille milles de toute région habitée, il tente de réparer son avion.', 15.99, 25, 'books/petit-prince.jpg', true, NOW(), NOW()),
('978-2-7654-1235-2', '1984', 'George Orwell', 'Dans le monde de 1984, Big Brother veille. Winston Smith, un obscur fonctionnaire du Parti, rêve d''un impossible amour.', 18.50, 30, 'books/1984.jpg', true, NOW(), NOW()),
('978-2-7654-1236-9', 'Pride and Prejudice', 'Jane Austen', 'Pride and Prejudice is an 1813 novel of manners written by Jane Austen.', 14.25, 20, 'books/pride-prejudice.jpg', true, NOW(), NOW()),
('978-2-7654-1237-6', 'To Kill a Mockingbird', 'Harper Lee', 'The unforgettable novel of a childhood in a sleepy Southern town and the crisis of conscience that rocked it.', 16.99, 15, 'books/mockingbird.jpg', true, NOW(), NOW()),
('978-2-7654-1238-3', 'The Great Gatsby', 'F. Scott Fitzgerald', 'Set in the summer of 1922, the novel follows Nick Carraway, a young Midwesterner who moves to Long Island.', 17.75, 22, 'books/great-gatsby.jpg', true, NOW(), NOW()),
('978-2-7654-1239-0', 'Harry Potter à l''école des sorciers', 'J.K. Rowling', 'Le jour de ses onze ans, Harry Potter, un orphelin élevé par un oncle et une tante qui le détestent, voit sa vie changer.', 19.99, 40, 'books/harry-potter-1.jpg', true, NOW(), NOW()),
('978-2-7654-1240-6', 'Le Seigneur des Anneaux', 'J.R.R. Tolkien', 'Dans les vertes prairies de la Comté, les Hobbits vivent en paix... jusqu''au jour où l''un d''entre eux hérite d''un anneau magique.', 29.99, 12, 'books/lotr.jpg', true, NOW(), NOW()),
('978-2-7654-1241-3', 'L''Étranger', 'Albert Camus', 'Aujourd''hui, maman est morte. Ou peut-être hier, je ne sais pas.', 13.50, 35, 'books/etranger.jpg', true, NOW(), NOW()),
('978-2-7654-1242-0', 'Dune', 'Frank Herbert', 'Il n''y a pas, dans tout l''Empire, de planète plus inhospitalière que Dune.', 22.99, 18, 'books/dune.jpg', true, NOW(), NOW()),
('978-2-7654-1243-7', 'Sapiens', 'Yuval Noah Harari', 'Il y a 100 000 ans, la Terre était habitée par au moins six espèces différentes d''hominidés.', 24.90, 28, 'books/sapiens.jpg', true, NOW(), NOW());

-- Insérer quelques clients de test
INSERT INTO customers (first_name, last_name, email, phone, address, city, postal_code, country, created_at, updated_at) VALUES
('Jean', 'Dupont', 'jean.dupont@email.com', '0123456789', '123 Rue de la Paix', 'Paris', '75001', 'France', NOW(), NOW()),
('Marie', 'Martin', 'marie.martin@email.com', '0123456790', '456 Avenue des Champs', 'Lyon', '69001', 'France', NOW(), NOW()),
('Pierre', 'Durand', 'pierre.durand@email.com', '0123456791', '789 Boulevard Saint-Germain', 'Marseille', '13001', 'France', NOW(), NOW()),
('Sophie', 'Bernard', 'sophie.bernard@email.com', '0123456792', '321 Rue Victor Hugo', 'Toulouse', '31000', 'France', NOW(), NOW()),
('Michel', 'Petit', 'michel.petit@email.com', '0123456793', '654 Place de la République', 'Nice', '06000', 'France', NOW(), NOW());