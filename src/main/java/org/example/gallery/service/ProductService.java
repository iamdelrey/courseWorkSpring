package org.example.gallery.service;

import lombok.AllArgsConstructor;
import org.example.gallery.dto.ProductDTO;
import org.example.gallery.entity.AppUser;
import org.example.gallery.entity.Product;
import org.example.gallery.repository.ProductRepository;
import org.example.gallery.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public Product create(ProductDTO dto) {
        Product product = Product.builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .text(dto.getText())
                .build();
        return productRepository.save(product);
    }

    private final String uploadDir = "C:\\Users\\stepa\\Downloads";

    public boolean saveImage(MultipartFile imageFile) {
        // Проверяем, есть ли файл
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                // Получаем имя файла
                String fileName = imageFile.getOriginalFilename();
                // Проверяем, что имя файла есть
                if (fileName != null && !fileName.isEmpty()) {
                    // Создаем путь к файлу в директории для загрузки
                    Path savePath = Paths.get(uploadDir + fileName);
                    // Убедитесь, что директория существует
                    if (!Files.exists(savePath)) {
                        Files.createDirectories(savePath.getParent());
                    }
                    // Сохраняем файл на диск
                    Files.copy(imageFile.getInputStream(), savePath);
                    return true; // Возвращаем true, если файл успешно сохранен
                }
            } catch (IOException e) {
                // Логируем ошибку
                e.printStackTrace();
                // Возвращаем false, если возникла ошибка
                return false;
            }
        }
        // Возвращаем false, если файл не был передан
        return false;
    }

    public String getUploadDir() {
        return uploadDir;
    }

    public List<Product> readAll() {
        return productRepository.findAll();
    }

    public Product update(Product product) {
        return productRepository.save(product);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    public Product addToOrder(Product product, Principal principal) {
        product.setUser(getUserByPrincipal(principal));
        return productRepository.save(product);
    }

    public AppUser getUserByPrincipal(Principal principal) {
        if (principal == null) return null;
        return userRepository.findByName(principal.getName()).get();
    }
}
