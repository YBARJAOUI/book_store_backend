package com.example.book_store_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    @NotBlank(message = "Le titre est obligatoire")
    @Size(min = 2, max = 200, message = "Le titre doit contenir entre 2 et 200 caractères")
    private String title;

    @Column(nullable = false)
    @NotBlank(message = "L'auteur est obligatoire")
    @Size(min = 2, max = 100, message = "L'auteur doit contenir entre 2 et 100 caractères")
    private String author;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    @NotNull(message = "Le prix est obligatoire")
    @DecimalMin(value = "0.0", inclusive = false, message = "Le prix doit être supérieur à 0")
    @Digits(integer = 8, fraction = 2, message = "Format de prix invalide")
    private BigDecimal price;

    @Column(columnDefinition = "LONGTEXT")
    private String image; // Image path or base64

    @Column(nullable = false)
    private Boolean isAvailable = true; // Simplification : disponible ou non


    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column
    private LocalDateTime updatedAt;

    // Relation One-to-Many avec OrderItem
    @Column(nullable = false)
    @NotBlank(message = "La langue est obligatoire")
    private String language; // francais, arabe, anglais

    @Column(nullable = false)
    @NotBlank(message = "La catégorie est obligatoire")
    @Size(min = 2, max = 100, message = "La catégorie doit contenir entre 2 et 100 caractères")
    private String category;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;
}