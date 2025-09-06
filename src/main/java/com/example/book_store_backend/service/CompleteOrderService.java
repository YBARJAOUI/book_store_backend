package com.example.book_store_backend.service;

import com.example.book_store_backend.dto.CreateOrderDTO;
import com.example.book_store_backend.dto.CustomerDTO;
import com.example.book_store_backend.dto.OrderItemDTO;
import com.example.book_store_backend.entity.Customer;
import com.example.book_store_backend.entity.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CompleteOrderService {

    private final OrderService orderService;
    private final CustomerService customerService;
    private final ModelMapper modelMapper;

    /**
     * Créer une commande complète avec création automatique du client si nécessaire
     */
    public Order createCompleteOrder(CreateOrderDTO orderDTO) {
        try {
            log.info("Création d'une commande complète pour le client: {} {}",
                    orderDTO.getCustomer().getFirstName(), orderDTO.getCustomer().getLastName());

            // Validation des données d'entrée
            if (orderDTO == null || orderDTO.getCustomer() == null) {
                throw new IllegalArgumentException("Les données de commande ne peuvent pas être nulles");
            }
            
            if (orderDTO.getItems() == null || orderDTO.getItems().isEmpty()) {
                throw new IllegalArgumentException("La liste des articles ne peut pas être vide");
            }

            // Convertir CustomerDTO en Customer entity
            Customer customerEntity;
            try {
                customerEntity = modelMapper.map(orderDTO.getCustomer(), Customer.class);
                log.debug("Conversion DTO -> Entity réussie pour le client");
            } catch (Exception e) {
                log.error("Erreur lors de la conversion CustomerDTO: {}", e.getMessage());
                throw new RuntimeException("Erreur lors de la conversion des données client", e);
            }

            // Créer ou récupérer le client
            Customer customer;
            try {
                customer = customerService.createOrGetCustomer(customerEntity);
                log.info("Client créé/récupéré avec succès. ID: {}", customer.getId());
            } catch (Exception e) {
                log.error("Erreur lors de la création/récupération du client: {}", e.getMessage());
                throw new RuntimeException("Erreur lors de la gestion du client", e);
            }

            // Convertir les items de commande
            List<OrderService.OrderItemRequest> orderItems;
            try {
                orderItems = orderDTO.getItems().stream()
                        .map(item -> {
                            if (item.getBookId() == null || item.getQuantity() == null || item.getQuantity() <= 0) {
                                throw new IllegalArgumentException("Article invalide: bookId=" + item.getBookId() + ", quantity=" + item.getQuantity());
                            }
                            return new OrderService.OrderItemRequest(item.getBookId(), item.getQuantity());
                        })
                        .collect(Collectors.toList());
                log.debug("Conversion des articles réussie. Nombre d'articles: {}", orderItems.size());
            } catch (Exception e) {
                log.error("Erreur lors de la conversion des articles: {}", e.getMessage());
                throw new RuntimeException("Erreur lors de la conversion des articles", e);
            }

            // Créer la commande
            Order order;
            try {
                order = orderService.createOrder(
                        customer.getId(),
                        orderItems,
                        orderDTO.getShippingAddress(),
                        orderDTO.getNotes()
                );
                log.info("Commande complète créée avec succès. Numéro: {}", order.getOrderNumber());
            } catch (Exception e) {
                log.error("Erreur lors de la création de la commande: {}", e.getMessage());
                throw new RuntimeException("Erreur lors de la création de la commande", e);
            }

            return order;
            
        } catch (Exception e) {
            log.error("Erreur générale lors de la création de la commande complète: {}", e.getMessage(), e);
            throw new RuntimeException("Erreur lors de la création de la commande: " + e.getMessage(), e);
        }
    }

    /**
     * Créer une commande simplifiée (avec juste les infos minimales du client)
     */
    public Order createSimpleOrder(String firstName, String lastName, String email,
                                   String phoneNumber, String address,
                                   List<OrderItemDTO> items, String notes) {
        
        try {
            log.info("Création d'une commande simple pour {} {}", firstName, lastName);
            
            // Validation des données d'entrée
            if (firstName == null || lastName == null || email == null || phoneNumber == null || address == null) {
                throw new IllegalArgumentException("Les informations client ne peuvent pas être nulles");
            }
            
            if (items == null || items.isEmpty()) {
                throw new IllegalArgumentException("La liste des articles ne peut pas être vide");
            }

            // Créer le DTO client
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setFirstName(firstName.trim());
            customerDTO.setLastName(lastName.trim());
            customerDTO.setEmail(email.trim().toLowerCase());
            customerDTO.setPhoneNumber(phoneNumber.trim());
            customerDTO.setAddress(address.trim());

            // Créer le DTO de commande
            CreateOrderDTO orderDTO = new CreateOrderDTO();
            orderDTO.setCustomer(customerDTO);
            orderDTO.setItems(items);
            orderDTO.setNotes(notes != null ? notes.trim() : "");
            orderDTO.setShippingAddress(address.trim());

            return createCompleteOrder(orderDTO);
            
        } catch (Exception e) {
            log.error("Erreur lors de la création de la commande simple: {}", e.getMessage(), e);
            throw new RuntimeException("Erreur lors de la création de la commande: " + e.getMessage(), e);
        }
    }
}