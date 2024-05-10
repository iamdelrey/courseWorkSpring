package org.example.gallery.controllers;

import org.example.gallery.dto.ContactDTO;
import org.example.gallery.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/contact")
public class ContactController {

    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping
    public ModelAndView showContactForm() {
        ModelAndView modelAndView = new ModelAndView("contact");
        modelAndView.addObject("contactDTO", new ContactDTO());
        return modelAndView;
    }

    @PostMapping
    public ModelAndView submitContactRequest(@Valid @ModelAttribute ContactDTO contactDTO, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("contact");
        System.out.println("ContactDTO: " + contactDTO);

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("contactDTO", contactDTO);
            modelAndView.addObject("errors", bindingResult.getAllErrors());
            return modelAndView;
        }

        contactService.handleContactRequest(contactDTO);

        modelAndView.addObject("message", "Благодарим за Ваше обращение!");
        modelAndView.addObject("contactDTO", new ContactDTO());

        return modelAndView;
    }
}