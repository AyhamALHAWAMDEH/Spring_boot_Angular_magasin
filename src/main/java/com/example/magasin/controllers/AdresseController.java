package com.example.magasin.controllers;

import com.example.magasin.dao.AdresseDao;
import com.example.magasin.models.Adresse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class AdresseController {
    @Autowired
    private AdresseDao adresseDao;

    @GetMapping("/adresse/{id}")

    public ResponseEntity<Adresse> getAdresse(@PathVariable int id) {

        Optional<Adresse> adresseExistant = adresseDao.findById(id);

        if(adresseExistant.isPresent()) {
            return new ResponseEntity<>(adresseExistant.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/liste-adresse")

    public List<Adresse> getAdresses() {

        return adresseDao.findAll();
    }

    @PostMapping("/adresse")
    public ResponseEntity<Adresse> ajoutAdresse(@RequestBody Adresse adresse){

        //si l'adresse à un identifiant
        if(adresse.getId() != null) {
            Optional<Adresse> adresseExistant =
                    adresseDao.findById(adresse.getId());

            //l'adresse à fournit un id existant dans la BDD (c'est un update)
            if(adresseExistant.isPresent()) {
                adresseDao.save(adresse);
                return new ResponseEntity<>(adresse,HttpStatus.OK);
            } else {
                //l'adresse à fournit un id qui n'existe pas dans la BDD (c'est une erreur)
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            adresseDao.save(adresse);
            return new ResponseEntity<>(adresse,HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/adresse/{id}")
    public ResponseEntity<Adresse> supprimeAdresse(@PathVariable int id) {

        Optional<Adresse> adresseExistant = adresseDao.findById(id);

        if(adresseExistant.isPresent()) {
            adresseDao.deleteById(id);
            return new ResponseEntity<>(adresseExistant.get(),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
