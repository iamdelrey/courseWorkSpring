package org.example.gallery.controllers;

import org.example.gallery.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void sendProductUpdate(Product product) {
        messagingTemplate.convertAndSend("/topic/productUpdate", product);
    }
}