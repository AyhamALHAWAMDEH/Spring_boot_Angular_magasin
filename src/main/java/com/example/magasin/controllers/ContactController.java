package com.example.magasin.controllers;

import com.example.magasin.dao.ContactDao;
import com.example.magasin.models.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class ContactController {
    @Autowired
    private ContactDao contactDao;

    @GetMapping("/contact/{id}")

    public ResponseEntity<Contact> getContact(@PathVariable int id) {

        Optional<Contact> contactExistant = contactDao.findById(id);

        if(contactExistant.isPresent()) {
            return new ResponseEntity<>(contactExistant.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/liste-contact")

    public List<Contact> getContacts() {

        return contactDao.findAll();
    }

    @PostMapping("/contact")
    public ResponseEntity<Contact> ajoutContact(@RequestBody Contact contact){

        //si l'contact à un identifiant
        if(contact.getId() != null) {
            Optional<Contact> contactExistant =
                    contactDao.findById(contact.getId());

            //l'contact à fournit un id existant dans la BDD (c'est un update)
            if(contactExistant.isPresent()) {
                contactDao.save(contact);
                return new ResponseEntity<>(contact,HttpStatus.OK);
            } else {
                //l'contact à fournit un id qui n'existe pas dans la BDD (c'est une erreur)
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            contactDao.save(contact);
            return new ResponseEntity<>(contact,HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/contact/{id}")
    public ResponseEntity<Contact> supprimeContact(@PathVariable int id) {

        Optional<Contact> contactExistant = contactDao.findById(id);

        if(contactExistant.isPresent()) {
            contactDao.deleteById(id);
            return new ResponseEntity<>(contactExistant.get(),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
