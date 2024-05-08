package org.example.gallery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class ProductDTO {
    private String name;
    private Integer price;
    private String text;
    private MultipartFile image;
}
