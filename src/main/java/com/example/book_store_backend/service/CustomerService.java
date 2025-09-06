package com.example.book_store_backend.service;

import com.example.book_store_backend.entity.Customer;
import com.example.book_store_backend.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;

    /**
     * Créer un nouveau client
     */
    public Customer createCustomer(Customer customer) {
        log.info("Création d'un nouveau client: {} {}", customer.getFirstName(), customer.getLastName());

        // Vérifier si l'email existe déjà
        if (customerRepository.findByEmail(customer.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Un client avec cet email existe déjà");
        }

        // Pour les numéros de téléphone, on peut être plus souple dans le contexte des commandes
        // Vérifier si le numéro de téléphone existe déjà (mais seulement pour les appels directs)
        if (customerRepository.findByPhoneNumber(customer.getPhoneNumber()).isPresent()) {
            throw new IllegalArgumentException("Un client avec ce numéro de téléphone existe déjà");
        }

        // Définir les valeurs par défaut
        if (customer.getIsActive() == null) {
            customer.setIsActive(true);
        }

        Customer savedCustomer = customerRepository.save(customer);
        log.info("Client créé avec succès. ID: {}", savedCustomer.getId());
        return savedCustomer;
    }
    
    /**
     * Créer un client pour une commande (plus souple sur les contraintes)
     */
    public Customer createCustomerForOrder(Customer customer) {
        log.info("Création d'un nouveau client pour commande: {} {}", customer.getFirstName(), customer.getLastName());

        // Définir les valeurs par défaut
        if (customer.getIsActive() == null) {
            customer.setIsActive(true);
        }

        try {
            Customer savedCustomer = customerRepository.save(customer);
            log.info("Client créé avec succès pour commande. ID: {}", savedCustomer.getId());
            return savedCustomer;
        } catch (Exception e) {
            log.error("Erreur lors de la création du client pour commande: {}", e.getMessage());
            throw new RuntimeException("Erreur lors de la création du client: " + e.getMessage());
        }
    }

    /**
     * Mettre à jour un client existant
     */
    public Customer updateCustomer(Long id, Customer customerDetails) {
        log.info("Mise à jour du client avec ID: {}", id);

        Customer existingCustomer = getCustomerById(id);

        // Vérifier l'unicité de l'email lors de la mise à jour
        if (!existingCustomer.getEmail().equals(customerDetails.getEmail()) &&
                customerRepository.existsByEmailAndIdNot(customerDetails.getEmail(), id)) {
            throw new IllegalArgumentException("Un autre client avec cet email existe déjà");
        }

        // Vérifier l'unicité du numéro de téléphone lors de la mise à jour
        if (!existingCustomer.getPhoneNumber().equals(customerDetails.getPhoneNumber()) &&
                customerRepository.existsByPhoneNumberAndIdNot(customerDetails.getPhoneNumber(), id)) {
            throw new IllegalArgumentException("Un autre client avec ce numéro de téléphone existe déjà");
        }

        // Mettre à jour les champs
        existingCustomer.setFirstName(customerDetails.getFirstName());
        existingCustomer.setLastName(customerDetails.getLastName());
        existingCustomer.setEmail(customerDetails.getEmail());
        existingCustomer.setPhoneNumber(customerDetails.getPhoneNumber());
        existingCustomer.setAddress(customerDetails.getAddress());
        existingCustomer.setCity(customerDetails.getCity());
        existingCustomer.setPostalCode(customerDetails.getPostalCode());
        existingCustomer.setCountry(customerDetails.getCountry());
        existingCustomer.setIsActive(customerDetails.getIsActive());

        Customer updatedCustomer = customerRepository.save(existingCustomer);
        log.info("Client mis à jour avec succès. ID: {}", updatedCustomer.getId());
        return updatedCustomer;
    }

    /**
     * Récupérer un client par ID
     */
    @Transactional(readOnly = true)
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client non trouvé avec l'ID: " + id));
    }

    /**
     * Récupérer un client par email
     */
    @Transactional(readOnly = true)
    public Optional<Customer> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    /**
     * Récupérer un client par numéro de téléphone
     */
    @Transactional(readOnly = true)
    public Optional<Customer> getCustomerByPhoneNumber(String phoneNumber) {
        return customerRepository.findByPhoneNumber(phoneNumber);
    }

    /**
     * Récupérer tous les clients actifs
     */
    @Transactional(readOnly = true)
    public List<Customer> getAllActiveCustomers() {
        return customerRepository.findByIsActiveTrue();
    }

    /**
     * Récupérer tous les clients
     */
    @Transactional(readOnly = true)
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    /**
     * Rechercher des clients
     */
    @Transactional(readOnly = true)
    public List<Customer> searchCustomers(String keyword) {
        return customerRepository.searchCustomers(keyword);
    }

    /**
     * Rechercher des clients par nom
     */
    @Transactional(readOnly = true)
    public List<Customer> searchCustomersByName(String keyword) {
        return customerRepository.searchCustomersByName(keyword);
    }

    /**
     * Récupérer les clients par ville
     */
    @Transactional(readOnly = true)
    public List<Customer> getCustomersByCity(String city) {
        return customerRepository.findByCityAndIsActiveTrue(city);
    }

    /**
     * Récupérer les clients par pays
     */
    @Transactional(readOnly = true)
    public List<Customer> getCustomersByCountry(String country) {
        return customerRepository.findByCountryAndIsActiveTrue(country);
    }

    /**
     * Supprimer un client (suppression logique)
     */
    public void deleteCustomer(Long id) {
        log.info("Suppression du client avec ID: {}", id);
        Customer customer = getCustomerById(id);
        customer.setIsActive(false);
        customerRepository.save(customer);
        log.info("Client supprimé (désactivé) avec succès. ID: {}", id);
    }

    /**
     * Supprimer définitivement un client
     */
    public void permanentlyDeleteCustomer(Long id) {
        log.info("Suppression définitive du client avec ID: {}", id);
        if (!customerRepository.existsById(id)) {
            throw new RuntimeException("Client non trouvé avec l'ID: " + id);
        }
        customerRepository.deleteById(id);
        log.info("Client supprimé définitivement avec succès. ID: {}", id);
    }

    /**
     * Activer/Désactiver un client
     */
    public Customer toggleCustomerStatus(Long id) {
        Customer customer = getCustomerById(id);
        customer.setIsActive(!customer.getIsActive());
        return customerRepository.save(customer);
    }

    /**
     * Récupérer les clients les plus actifs
     */
    @Transactional(readOnly = true)
    public List<Customer> getMostActiveCustomers() {
        return customerRepository.findMostActiveCustomers();
    }

    /**
     * Créer ou récupérer un client (utile pour les commandes)
     * Si un client avec l'email existe, le retourner, sinon créer un nouveau
     */
    public Customer createOrGetCustomer(Customer customerData) {
        // Chercher d'abord par email
        Optional<Customer> existingCustomerByEmail = getCustomerByEmail(customerData.getEmail());

        if (existingCustomerByEmail.isPresent()) {
            log.info("Client existant trouvé avec l'email: {}", customerData.getEmail());
            return existingCustomerByEmail.get();
        }
        
        // Si pas trouvé par email, chercher par numéro de téléphone
        Optional<Customer> existingCustomerByPhone = getCustomerByPhoneNumber(customerData.getPhoneNumber());
        
        if (existingCustomerByPhone.isPresent()) {
            log.info("Client existant trouvé avec le téléphone: {}", customerData.getPhoneNumber());
            // Mettre à jour les informations si différentes
            Customer existingCustomer = existingCustomerByPhone.get();
            existingCustomer.setEmail(customerData.getEmail());
            existingCustomer.setFirstName(customerData.getFirstName());
            existingCustomer.setLastName(customerData.getLastName());
            existingCustomer.setAddress(customerData.getAddress());
            return customerRepository.save(existingCustomer);
        }
        
        // Si aucun client trouvé, créer un nouveau en gérant les contraintes
        return createCustomerSafely(customerData);
    }
    
    /**
     * Créer un client en gérant les contraintes d'unicité de manière plus souple
     */
    private Customer createCustomerSafely(Customer customer) {
        try {
            // Essayer d'abord avec les contraintes normales
            return createCustomer(customer);
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("email existe déjà")) {
                // Si l'email existe déjà, récupérer ce client
                Optional<Customer> existingCustomer = getCustomerByEmail(customer.getEmail());
                if (existingCustomer.isPresent()) {
                    return existingCustomer.get();
                }
            } else if (e.getMessage().contains("numéro de téléphone existe déjà")) {
                // Si le téléphone existe déjà, récupérer ce client
                Optional<Customer> existingCustomer = getCustomerByPhoneNumber(customer.getPhoneNumber());
                if (existingCustomer.isPresent()) {
                    return existingCustomer.get();
                }
            }
            
            // En dernier recours, essayer la création pour commande (plus souple)
            try {
                return createCustomerForOrder(customer);
            } catch (Exception orderException) {
                log.error("Echec même avec createCustomerForOrder: {}", orderException.getMessage());
                throw new RuntimeException("Impossible de créer le client: " + e.getMessage());
            }
        }
    }

    /**
     * Vérifier si un client existe par email
     */
    @Transactional(readOnly = true)
    public boolean customerExistsByEmail(String email) {
        return customerRepository.findByEmail(email).isPresent();
    }

    /**
     * Vérifier si un client existe par numéro de téléphone
     */
    @Transactional(readOnly = true)
    public boolean customerExistsByPhoneNumber(String phoneNumber) {
        return customerRepository.findByPhoneNumber(phoneNumber).isPresent();
    }

    /**
     * Obtenir des statistiques sur les clients
     */
    @Transactional(readOnly = true)
    public CustomerStatistics getCustomerStatistics() {
        long totalCustomers = customerRepository.count();
        long activeCustomers = customerRepository.findByIsActiveTrue().size();
        List<Object[]> customersByCity = customerRepository.countCustomersByCity();

        return CustomerStatistics.builder()
                .totalCustomers(totalCustomers)
                .activeCustomers(activeCustomers)
                .inactiveCustomers(totalCustomers - activeCustomers)
                .customersByCity(customersByCity)
                .build();
    }

    // Classe d'aide pour les statistiques
    @lombok.Builder
    @lombok.Data
    public static class CustomerStatistics {
        private long totalCustomers;
        private long activeCustomers;
        private long inactiveCustomers;
        private List<Object[]> customersByCity;
    }
}