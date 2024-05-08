package org.example.gallery.service;

import lombok.AllArgsConstructor;
import org.example.gallery.dto.ProductDTO;
import org.example.gallery.entity.AppUser;
import org.example.gallery.entity.Product;
import org.example.gallery.repository.ProductRepository;
import org.example.gallery.repository.UserRepository;
import org.springframework.stereotype.Service;

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
