//package org.example.gallery.controllers;
//
//import lombok.AllArgsConstructor;
//import org.example.gallery.dto.ProductDTO;
//import org.example.gallery.entity.Product;
//import org.example.gallery.service.ProductService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.Base64;
//import java.util.List;
//
//@Controller
//@PreAuthorize("hasAuthority('ADMIN')")
//@AllArgsConstructor
//public class AdminController {
//
//    private final ProductService productService;
//
//    @RequestMapping(value = "/admin", method = RequestMethod.GET)
//    public String addMenu(@RequestParam(value="ask_name", required = false) String ask_name, Model model) {
//        List<Product> positions = productService.readAll();
//        model.addAttribute("products", positions);
//        if (ask_name == null) {
//            String answer = "Ошибка! Введите название картины.";
//            model.addAttribute("answer", answer);
//        }
//        else {
//            long current_id = findIdByName(ask_name, positions);
//            if (current_id == -1) {
//                model.addAttribute("answer", "Такой позиции нет");
//            }
//            else {
//                model.addAttribute("answer", "ID данной позиции: " + current_id);
//            }
//        }
//        return "admin";
//    }
//
//    @RequestMapping(value = "/admin", method = RequestMethod.POST)
//    public ResponseEntity<Product> addPizza(@RequestParam String name, @RequestParam String price,
//                                                @RequestParam String text, @RequestParam String imgBase64, Model model) {
//        byte[] img = Base64.getDecoder().decode(imgBase64);
//        ProductDTO dto = new ProductDTO(name, price, text, img);
//        return new ResponseEntity<>(productService.create(dto), HttpStatus.OK);
//    }
//
//    @RequestMapping(value = "/admin", method = RequestMethod.DELETE)
//    public void delete(@RequestParam Long delete_id, Model model) {
//        productService.delete(delete_id);
//    }
//
//    public long findIdByName(String name, List<Product> list) {
//        for (int i = 0; i < list.size(); ++i) {
//            if (list.get(i).getName().equals(name))
//                return list.get(i).getId();
//        }
//        return -1;
//    }
//}