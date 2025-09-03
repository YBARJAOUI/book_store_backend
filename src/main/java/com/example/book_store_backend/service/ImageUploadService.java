package com.example.book_store_backend.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@Slf4j
public class ImageUploadService {

    @Value("${app.upload.dir:uploads/images}")
    private String uploadDir;

    @Value("${app.upload.max-file-size:5MB}")
    private String maxFileSize;

    public String uploadImage(MultipartFile file) throws IOException {
        // Vérifications
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Fichier vide");
        }

        // Vérifier le type de fichier
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Seules les images sont autorisées");
        }

        // Vérifier la taille (5MB max)
        if (file.getSize() > 5 * 1024 * 1024) {
            throw new IllegalArgumentException("La taille du fichier ne peut pas dépasser 5MB");
        }

        // Créer le dossier s'il n'existe pas
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Générer un nom unique pour le fichier
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String filename = UUID.randomUUID().toString() + extension;

        // Sauvegarder le fichier
        Path filePath = uploadPath.resolve(filename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        log.info("Image uploadée avec succès: {}", filename);
        return filename;
    }

    public void deleteImage(String filename) {
        if (filename == null || filename.trim().isEmpty()) {
            return;
        }

        try {
            Path filePath = Paths.get(uploadDir).resolve(filename);
            Files.deleteIfExists(filePath);
            log.info("Image supprimée: {}", filename);
        } catch (IOException e) {
            log.error("Erreur lors de la suppression de l'image: {}", filename, e);
        }
    }

    public boolean imageExists(String filename) {
        if (filename == null || filename.trim().isEmpty()) {
            return false;
        }
        Path filePath = Paths.get(uploadDir).resolve(filename);
        return Files.exists(filePath);
    }
}