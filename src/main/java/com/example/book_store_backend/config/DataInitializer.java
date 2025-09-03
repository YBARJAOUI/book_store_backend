package com.example.book_store_backend.config;

import com.example.book_store_backend.entity.Book;
import com.example.book_store_backend.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final BookRepository bookRepository;

    @Override
    public void run(String... args) throws Exception {
        if (bookRepository.count() == 0) {
            log.info("Initialisation des données de test...");
            initializeBooks();
            log.info("Données de test initialisées avec succès!");
        } else {
            log.info("Données déjà présentes dans la base de données, pas d'initialisation nécessaire.");
        }
    }

    private void initializeBooks() {
        List<Book> books = Arrays.asList(
            createBook("978-2-7654-1234-5", "Le Petit Prince", "Antoine de Saint-Exupéry", 
                      "Un pilote s'écrase dans le désert du Sahara. Mille milles de toute région habitée, il tente de réparer son avion.", 
                      new BigDecimal("15.99"), 25, "francais", "Fiction"),
            
            createBook("978-2-7654-1235-2", "1984", "George Orwell", 
                      "Dans le monde de 1984, Big Brother veille. Winston Smith, un obscur fonctionnaire du Parti, rêve d'un impossible amour.", 
                      new BigDecimal("18.50"), 30, "francais", "Science-Fiction"),
            
            createBook("978-2-7654-1236-9", "Pride and Prejudice", "Jane Austen", 
                      "Pride and Prejudice is an 1813 novel of manners written by Jane Austen.", 
                      new BigDecimal("14.25"), 20, "anglais", "Romance"),
            
            createBook("978-2-7654-1237-6", "To Kill a Mockingbird", "Harper Lee", 
                      "The unforgettable novel of a childhood in a sleepy Southern town and the crisis of conscience that rocked it.", 
                      new BigDecimal("16.99"), 15, "anglais", "Fiction"),
            
            createBook("978-2-7654-1238-3", "The Great Gatsby", "F. Scott Fitzgerald", 
                      "Set in the summer of 1922, the novel follows Nick Carraway, a young Midwesterner who moves to Long Island.", 
                      new BigDecimal("17.75"), 22, "anglais", "Fiction"),
            
            createBook("978-2-7654-1239-0", "Harry Potter à l'école des sorciers", "J.K. Rowling", 
                      "Le jour de ses onze ans, Harry Potter, un orphelin élevé par un oncle et une tante qui le détestent, voit sa vie changer.", 
                      new BigDecimal("19.99"), 40, "francais", "Fantasy"),
            
            createBook("978-2-7654-1240-6", "Le Seigneur des Anneaux", "J.R.R. Tolkien", 
                      "Dans les vertes prairies de la Comté, les Hobbits vivent en paix... jusqu'au jour où l'un d'entre eux hérite d'un anneau magique.", 
                      new BigDecimal("29.99"), 12, "francais", "Fantasy"),
            
            createBook("978-2-7654-1241-3", "L'Étranger", "Albert Camus", 
                      "Aujourd'hui, maman est morte. Ou peut-être hier, je ne sais pas.", 
                      new BigDecimal("13.50"), 35, "francais", "Philosophie"),
            
            createBook("978-2-7654-1242-0", "Dune", "Frank Herbert", 
                      "Il n'y a pas, dans tout l'Empire, de planète plus inhospitalière que Dune.", 
                      new BigDecimal("22.99"), 18, "francais", "Science-Fiction"),
            
            createBook("978-2-7654-1243-7", "Sapiens", "Yuval Noah Harari", 
                      "Il y a 100 000 ans, la Terre était habitée par au moins six espèces différentes d'hominidés.", 
                      new BigDecimal("24.90"), 28, "francais", "Histoire")
        );

        bookRepository.saveAll(books);
        log.info("Ajouté {} livres à la base de données", books.size());
    }

    private Book createBook(String isbn, String title, String author, String description, 
                           BigDecimal price, int stock, String language, String category) {
        Book book = new Book();
        book.setIsbn(isbn);
        book.setTitle(title);
        book.setAuthor(author);
        book.setDescription(description);
        book.setPrice(price);
        book.setStock(stock);
        book.setLanguage(language);
        book.setCategory(category);
        book.setImageBase64(null); // Pas d'image par défaut, sera ajoutée via l'interface
        book.setIsAvailable(true);
        return book;
    }
}