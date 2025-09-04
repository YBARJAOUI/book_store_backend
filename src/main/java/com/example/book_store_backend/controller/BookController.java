package com.example.book_store_backend.controller;

import com.example.book_store_backend.entity.Book;
import com.example.book_store_backend.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Tag(name = "Books", description = "API de gestion des livres")
@CrossOrigin(origins = "*")
public class BookController {

    private final BookService bookService;

    @PostMapping
    @Operation(summary = "Créer un nouveau livre")
    public ResponseEntity<Book> createBook(@RequestBody @Valid Book book) {
        Book createdBook = bookService.createBook(book);
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un livre existant")
    public ResponseEntity<Book> updateBook(
            @Parameter(description = "ID du livre") @PathVariable Long id,
            @RequestBody @Valid Book bookDetails) {

        Book updatedBook = bookService.updateBook(id, bookDetails);
        return ResponseEntity.ok(updatedBook);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un livre par ID")
    public ResponseEntity<Book> getBookById(
            @Parameter(description = "ID du livre") @PathVariable Long id) {
        Book book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }


    // MOBILE-FRIENDLY: Return direct list instead of paginated response
    @GetMapping("/all")
    @Operation(summary = "Récupérer tous les livres (liste simple pour mobile)")
    public ResponseEntity<List<Book>> getAllBooksSimple() {
        List<Book> books = bookService.getAllBooksSimple();
        return ResponseEntity.ok(books);
    }

    // Keep the paginated version for web admin
    @GetMapping
    @Operation(summary = "Récupérer tous les livres avec pagination")
    public ResponseEntity<Page<Book>> getAllBooks(Pageable pageable) {
        Page<Book> books = bookService.getAllBooks(pageable);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/available")
    @Operation(summary = "Récupérer tous les livres disponibles")
    public ResponseEntity<List<Book>> getAvailableBooks() {
        List<Book> books = bookService.getAvailableBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/active")
    @Operation(summary = "Récupérer tous les livres actifs")
    public ResponseEntity<List<Book>> getActiveBooks() {
        List<Book> books = bookService.getAvailableBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/categories")
    @Operation(summary = "Récupérer toutes les catégories")
    public ResponseEntity<List<String>> getCategories() {
        List<String> categories = bookService.getCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/featured")
    @Operation(summary = "Récupérer les livres en vedette")
    public ResponseEntity<List<Book>> getFeaturedBooks() {
        List<Book> books = bookService.getFeaturedBooks();
        return ResponseEntity.ok(books);
    }





    @GetMapping("/search")
    @Operation(summary = "Rechercher des livres")
    public ResponseEntity<List<Book>> searchBooks(
            @Parameter(description = "Mot-clé de recherche") @RequestParam String keyword) {
        List<Book> books = bookService.searchBooks(keyword);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/price-range")
    @Operation(summary = "Récupérer les livres par plage de prix")
    public ResponseEntity<List<Book>> getBooksByPriceRange(
            @Parameter(description = "Prix minimum") @RequestParam BigDecimal minPrice,
            @Parameter(description = "Prix maximum") @RequestParam BigDecimal maxPrice) {
        List<Book> books = bookService.getBooksByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(books);
    }

    @PutMapping("/{id}/toggle-availability")
    @Operation(summary = "Changer la disponibilité d'un livre")
    public ResponseEntity<Book> toggleAvailability(
            @Parameter(description = "ID du livre") @PathVariable Long id) {
        Book updatedBook = bookService.toggleAvailability(id);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un livre")
    public ResponseEntity<Void> deleteBook(
            @Parameter(description = "ID du livre") @PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    // Gestion des erreurs
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.notFound().build();
    }
}