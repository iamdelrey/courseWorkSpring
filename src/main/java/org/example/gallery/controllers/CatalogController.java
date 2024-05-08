package org.example.gallery.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
@AllArgsConstructor
public class CatalogController {

    @GetMapping("/catalog")
    public String index(Model model) {
        return "catalog";
    }
}
