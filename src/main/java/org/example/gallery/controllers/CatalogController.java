package org.example.gallery.controllers;

import org.example.gallery.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CatalogController {

    @Autowired
    private ProductService productService;

    @GetMapping("/catalog")
    public ModelAndView showCatalogPage() {
        ModelAndView modelAndView = new ModelAndView("catalog");
        modelAndView.addObject("products", productService.readAll());
        return modelAndView;
    }
}