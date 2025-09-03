package com.example.book_store_backend.repository;

import com.example.book_store_backend.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // Trouver un livre par ISBN
    Optional<Book> findByIsbn(String isbn);

    // Trouver les livres disponibles
    List<Book> findByIsAvailableTrue();

    // Trouver les livres par auteur
    List<Book> findByAuthorContainingIgnoreCaseAndIsAvailableTrue(String author);

    // Recherche par titre
    List<Book> findByTitleContainingIgnoreCaseAndIsAvailableTrue(String title);

    // Recherche globale (titre, auteur, description)
    @Query("SELECT b FROM Book b WHERE b.isAvailable = true AND " +
            "(LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(b.author) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(b.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Book> searchBooks(@Param("keyword") String keyword);

    // Recherche avec pagination
    @Query("SELECT b FROM Book b WHERE b.isAvailable = true AND " +
            "(LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(b.author) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(b.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Book> searchBooksWithPagination(@Param("keyword") String keyword, Pageable pageable);

    // Trouver les auteurs distincts
    @Query("SELECT DISTINCT b.author FROM Book b WHERE b.isAvailable = true")
    List<String> findDistinctAuthors();

    // Trouver les livres les plus vendus (basé sur OrderItems)
    @Query("SELECT b FROM Book b JOIN b.orderItems oi WHERE b.isAvailable = true GROUP BY b ORDER BY SUM(oi.quantity) DESC")
    List<Book> findBestSellingBooks(Pageable pageable);

    // Vérifier l'existence par ISBN (excluant un ID spécifique pour les mises à jour)
    boolean existsByIsbnAndIdNot(String isbn, Long id);

    // Trouver les livres par plage de prix
    @Query("SELECT b FROM Book b WHERE b.isAvailable = true AND b.price BETWEEN :minPrice AND :maxPrice")
    List<Book> findBooksByPriceRange(@Param("minPrice") BigDecimal minPrice,
                                     @Param("maxPrice") BigDecimal maxPrice);

    // Trouver les livres en vedette (les plus récents)
    List<Book> findTop10ByIsAvailableTrueOrderByCreatedAtDesc();

    // Trouver les livres avec stock faible
    List<Book> findByStockLessThanEqual(int threshold);

    // Vérifier l'existence par ISBN
    boolean existsByIsbn(String isbn);
}