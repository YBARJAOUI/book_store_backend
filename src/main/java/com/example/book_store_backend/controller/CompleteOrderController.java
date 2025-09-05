package com.example.book_store_backend.controller;

import com.example.book_store_backend.dto.CreateOrderDTO;
import com.example.book_store_backend.dto.OrderItemDTO;
import com.example.book_store_backend.entity.Order;
import com.example.book_store_backend.service.CompleteOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/complete-orders")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Complete Orders", description = "API pour créer des commandes complètes avec gestion automatique des clients")
@CrossOrigin(origins = "*")
public class CompleteOrderController {

    private final CompleteOrderService completeOrderService;

    @PostMapping
    @Operation(summary = "Créer une commande complète avec client")
    public ResponseEntity<Order> createCompleteOrder(@Valid @RequestBody CreateOrderDTO orderDTO) {
        Order createdOrder = completeOrderService.createCompleteOrder(orderDTO);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @PostMapping("/simple")
    @Operation(summary = "Créer une commande avec informations client simplifiées")
    public ResponseEntity<Order> createSimpleOrder(@Valid @RequestBody SimpleOrderRequest request) {
        try {
            log.info("Reçu une demande de commande simple: {}", request);
            Order createdOrder = completeOrderService.createSimpleOrder(
                    request.getFirstName(),
                    request.getLastName(),
                    request.getEmail(),
                    request.getPhoneNumber(),
                    request.getAddress(),
                    request.getItems(),
                    request.getNotes()
            );
            log.info("Commande créée avec succès: {}", createdOrder.getId());
            return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Erreur lors de la création de la commande", e);
            throw e;
        }
    }

    @GetMapping("/test")
    @Operation(summary = "Test de l'endpoint")
    public ResponseEntity<String> testEndpoint() {
        return ResponseEntity.ok("Endpoint de commande fonctionne correctement!");
    }

    @PostMapping("/test-simple")
    @Operation(summary = "Test simple de création de commande")
    public ResponseEntity<Map<String, Object>> testSimpleOrder(@RequestBody Map<String, Object> request) {
        try {
            log.info("Test simple - Données reçues: {}", request);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Données reçues correctement");
            response.put("receivedData", request);
            response.put("timestamp", java.time.LocalDateTime.now());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Erreur dans le test simple", e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    // DTO pour la commande simple
    public static class SimpleOrderRequest {
        @jakarta.validation.constraints.NotBlank(message = "Le prénom est obligatoire")
        private String firstName;

        @jakarta.validation.constraints.NotBlank(message = "Le nom est obligatoire")
        private String lastName;

        @jakarta.validation.constraints.NotBlank(message = "L'email est obligatoire")
        @jakarta.validation.constraints.Email(message = "Format d'email invalide")
        private String email;

        @jakarta.validation.constraints.NotBlank(message = "Le numéro de téléphone est obligatoire")
        private String phoneNumber;

        @jakarta.validation.constraints.NotBlank(message = "L'adresse est obligatoire")
        private String address;

        @jakarta.validation.constraints.NotEmpty(message = "La liste des articles ne peut pas être vide")
        private List<OrderItemDTO> items;

        private String notes;

        // Constructeurs
        public SimpleOrderRequest() {}

        // Getters et setters
        public String getFirstName() { return firstName; }
        public void setFirstName(String firstName) { this.firstName = firstName; }

        public String getLastName() { return lastName; }
        public void setLastName(String lastName) { this.lastName = lastName; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getPhoneNumber() { return phoneNumber; }
        public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }

        public List<OrderItemDTO> getItems() { return items; }
        public void setItems(List<OrderItemDTO> items) { this.items = items; }

        public String getNotes() { return notes; }
        public void setNotes(String notes) { this.notes = notes; }
    }

    // Gestion des erreurs
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body("Erreur de validation: " + e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.badRequest().body("Erreur de traitement: " + e.getMessage());
    }

    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(org.springframework.web.bind.MethodArgumentNotValidException e) {
        StringBuilder errors = new StringBuilder("Erreurs de validation:\n");
        e.getBindingResult().getFieldErrors().forEach(error -> {
            errors.append("- ").append(error.getField()).append(": ").append(error.getDefaultMessage()).append("\n");
        });
        return ResponseEntity.badRequest().body(errors.toString());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception e) {
        return ResponseEntity.badRequest().body("Erreur inattendue: " + e.getMessage());
    }
}