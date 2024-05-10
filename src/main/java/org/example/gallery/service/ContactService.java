package org.example.gallery.service;

import org.example.gallery.dto.ContactDTO;
import org.example.gallery.entity.Contact;
import org.example.gallery.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ContactService {

    private final ContactRepository contactRepository;
    private final EmailService emailService;

    @Autowired
    public ContactService(ContactRepository contactRepository, EmailService emailService) {
        this.contactRepository = contactRepository;
        this.emailService = emailService;
    }

    public void handleContactRequest(ContactDTO contactDTO) {
        Contact contact = convertToEntity(contactDTO);
        contactRepository.save(contact);
        System.out.println("Handling contact request for: " + contactDTO);
        emailService.sendEmail(contact.getEmail(), "Благодарим за Ваше обращение!");
    }

    private Contact convertToEntity(ContactDTO contactDTO) {
        Contact contact = new Contact();
        contact.setFirstName(contactDTO.getFirstName());
        contact.setLastName(contactDTO.getLastName());
        contact.setEmail(contactDTO.getEmail());
        contact.setPhoneNumber(contactDTO.getPhoneNumber());
        contact.setQueryType(contactDTO.getQueryType());
        contact.setMessage(contactDTO.getMessage());

        if (contactDTO.getDateTime() != null && !contactDTO.getDateTime().isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(contactDTO.getDateTime(), formatter);
            contact.setDateTime(date.atStartOfDay());
        }
        return contact;
    }
}