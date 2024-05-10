package org.example.gallery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDTO {

    private String name;
    private String price;
    private String text;
    private byte[] img;
}
