-- Données de test pour H2
INSERT INTO books (id, isbn, title, author, description, price, stock, image_path, is_available, created_at, updated_at) VALUES
(1, '978-2-7654-1234-5', 'Le Petit Prince', 'Antoine de Saint-Exupéry', 'Un pilote s''écrase dans le désert du Sahara.', 15.99, 25, 'books/petit-prince.jpg', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, '978-2-7654-1235-2', '1984', 'George Orwell', 'Dans le monde de 1984, Big Brother veille.', 18.50, 30, 'books/1984.jpg', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, '978-2-7654-1236-9', 'Pride and Prejudice', 'Jane Austen', 'Pride and Prejudice is an 1813 novel of manners written by Jane Austen.', 14.25, 20, 'books/pride-prejudice.jpg', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO customers (id, first_name, last_name, email, phone, address, city, postal_code, country, created_at, updated_at) VALUES
(1, 'Jean', 'Dupont', 'jean.dupont@email.com', '0123456789', '123 Rue de la Paix', 'Paris', '75001', 'France', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Marie', 'Martin', 'marie.martin@email.com', '0123456790', '456 Avenue des Champs', 'Lyon', '69001', 'France', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
