package com.example.book_store_backend.service;

import com.example.book_store_backend.entity.Book;
import com.example.book_store_backend.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BookService {

    private final BookRepository bookRepository;

    /**
     * Créer un nouveau livre
     */
    public Book createBook(Book book) {
        log.info("Création d'un nouveau livre: {}", book.getTitle());

        // Définir les valeurs par défaut
        if (book.getIsAvailable() == null) {
            book.setIsAvailable(true);
        }

        Book savedBook = bookRepository.save(book);
        log.info("Livre créé avec succès. ID: {}", savedBook.getId());
        return savedBook;
    }

    /**
     * Mettre à jour un livre existant
     */
    public Book updateBook(Long id, Book bookDetails) {
        log.info("Mise à jour du livre avec ID: {}", id);

        Book existingBook = getBookById(id);

        // Mettre à jour les champs
        existingBook.setTitle(bookDetails.getTitle());
        existingBook.setAuthor(bookDetails.getAuthor());
        existingBook.setDescription(bookDetails.getDescription());
        existingBook.setPrice(bookDetails.getPrice());
        existingBook.setImage(bookDetails.getImage());
        existingBook.setLanguage(bookDetails.getLanguage());
        existingBook.setCategory(bookDetails.getCategory());
        existingBook.setIsAvailable(bookDetails.getIsAvailable());

        Book updatedBook = bookRepository.save(existingBook);
        log.info("Livre mis à jour avec succès. ID: {}", updatedBook.getId());
        return updatedBook;
    }

    /**
     * Récupérer un livre par ID
     */
    @Transactional(readOnly = true)
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livre non trouvé avec l'ID: " + id));
    }


    /**
     * Récupérer tous les livres disponibles
     */
    @Transactional(readOnly = true)
    public List<Book> getAvailableBooks() {
        return bookRepository.findByIsAvailableTrue();
    }

    /**
     * Récupérer tous les livres avec pagination
     */
    @Transactional(readOnly = true)
    public Page<Book> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    /**
     * Récupérer tous les livres (liste simple pour mobile)
     */
    @Transactional(readOnly = true)
    public List<Book> getAllBooksSimple() {
        return bookRepository.findAll();
    }

    /**
     * Rechercher des livres
     */
    @Transactional(readOnly = true)
    public List<Book> searchBooks(String keyword) {
        return bookRepository.searchBooks(keyword);
    }

    /**
     * Récupérer les livres par plage de prix
     */
    @Transactional(readOnly = true)
    public List<Book> getBooksByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return bookRepository.findBooksByPriceRange(minPrice, maxPrice);
    }

    /**
     * Supprimer un livre
     */
    public void deleteBook(Long id) {
        log.info("Suppression du livre avec ID: {}", id);
        Book book = getBookById(id);

        bookRepository.deleteById(id);
        log.info("Livre supprimé avec succès. ID: {}", id);
    }

    /**
     * Changer la disponibilité d'un livre
     */
    public Book toggleAvailability(Long id) {
        Book book = getBookById(id);
        book.setIsAvailable(!book.getIsAvailable());
        return bookRepository.save(book);
    }

    /**
     * Récupérer toutes les catégories
     */
    @Transactional(readOnly = true)
    public List<String> getCategories() {
        // Pour cette version simplifiée, nous retournons des catégories par défaut
        // Dans une version future, on pourrait les extraire des descriptions ou avoir une table séparée
        return List.of("Fiction", "Non-Fiction", "Science", "Histoire", "Romance", "Thriller", "Fantaisie", "Biographie");
    }

    /**
     * Récupérer les livres en vedette
     */
    @Transactional(readOnly = true)
    public List<Book> getFeaturedBooks() {
        // Pour cette version, on retourne les livres les plus récents
        return bookRepository.findTop10ByIsAvailableTrueOrderByCreatedAtDesc();
    }



}