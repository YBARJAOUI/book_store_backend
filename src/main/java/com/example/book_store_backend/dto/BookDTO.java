package com.example.book_store_backend.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BookDTO {
    private Long id;
    private String isbn;
    private String title;
    private String author;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private String category;
    private String language; // francais, arabe, anglais
    private String imageBase64;
    private Boolean isAvailable;
    private String createdAt;
    private String updatedAt;
}
