package com.example.book_store_backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderItemDTO {
    @NotNull(message = "L'ID du livre est obligatoire")
    private Long bookId;

    @NotNull(message = "La quantité est obligatoire")
    @Min(value = 1, message = "La quantité doit être supérieure à 0")
    private Integer quantity;
    
    // Constructeur par défaut
    public OrderItemDTO() {}
    
    // Constructeur avec paramètres
    public OrderItemDTO(Long bookId, Integer quantity) {
        this.bookId = bookId;
        this.quantity = quantity;
    }
}
