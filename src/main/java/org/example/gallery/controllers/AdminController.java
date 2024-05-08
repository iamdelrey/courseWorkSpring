package org.example.gallery.controllers;

import lombok.AllArgsConstructor;
import org.example.gallery.dto.ProductDTO;
import org.example.gallery.entity.Product;
import org.example.gallery.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.core.io.UrlResource;


import java.util.List;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
@AllArgsConstructor
public class AdminController {

    private final ProductService productService;

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String addMenu(@RequestParam(value="ask_name", required = false) String ask_name, Model model) {
        List<Product> positions = productService.readAll();
        model.addAttribute("products", positions);
        if (ask_name == null) {
            String answer = "Ошибка! Введите название картины.";
            model.addAttribute("answer", answer);
        }
        else {
            long current_id = findIdByName(ask_name, positions);
            if (current_id == -1) {
                model.addAttribute("answer", "Такой позиции нет");
            }
            else {
                model.addAttribute("answer", "ID данной позиции: " + current_id);
            }
        }
        return "admin";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.POST)
    public ResponseEntity<String> create(@RequestParam String name, @RequestParam Integer price,
                                         @RequestParam String text, @RequestParam("image") MultipartFile image, Model model) {
        try {
            ProductDTO dto = new ProductDTO(name, price, text, image);
            Product product = productService.create(dto);
            boolean isImageSaved = productService.saveImage(image);
            if (isImageSaved) {
                return ResponseEntity.ok("Товар и изображение успешно добавлены");
            } else {
                return ResponseEntity.ok("Товар добавлен, но изображение сохранить не удалось");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка при добавлении товара: " + e.getMessage());
        }
    }

    @GetMapping("/download/{filename:.+}")
    public ResponseEntity<?> downloadFile(@PathVariable String filename) {
        String uploadDir = productService.getUploadDir(); // Получаем путь к директории для загрузки
        Path filePath = Paths.get(uploadDir, filename);
        if (Files.exists(filePath)) {
            try {
                Resource resource = new UrlResource(filePath.toUri());
                String contentType = "application/octet-stream";
                String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                        .body(resource);
            } catch (MalformedURLException e) {
                return ResponseEntity.badRequest().body("Неверный формат URL: " + e.getMessage());
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/admin", method = RequestMethod.DELETE)
    public void delete(@RequestParam Long delete_id, Model model) {
        productService.delete(delete_id);
    }

    public long findIdByName(String name, List<Product> list) {
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i).getName().equals(name))
                return list.get(i).getId();
        }
        return -1;
    }
}