package com.example.magasin.controllers;

import com.example.magasin.dao.PaimentDao;
import com.example.magasin.models.Paiement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class PaimentController {
    @Autowired
    private PaimentDao paimentDao;

    @GetMapping("/paiment/{id}")

    public ResponseEntity<Paiement> getPaiment(@PathVariable int id) {

        Optional<Paiement> paimentExistant = paimentDao.findById(id);

        if(paimentExistant.isPresent()) {
            return new ResponseEntity<>(paimentExistant.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/liste-paiment")

    public List<Paiement> getPaiments() {

        return paimentDao.findAll();
    }

    @PostMapping("/paiment")
    public ResponseEntity<Paiement> ajoutPaiment(@RequestBody Paiement paiment){

        //si l'paiment à un identifiant
        if(paiment.getId() != null) {
            Optional<Paiement> paimentExistant =
                    paimentDao.findById(paiment.getId());

            //l'paiment à fournit un id existant dans la BDD (c'est un update)
            if(paimentExistant.isPresent()) {
                paimentDao.save(paiment);
                return new ResponseEntity<>(paiment,HttpStatus.OK);
            } else {
                //l'paiment à fournit un id qui n'existe pas dans la BDD (c'est une erreur)
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            paimentDao.save(paiment);
            return new ResponseEntity<>(paiment,HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/paiment/{id}")
    public ResponseEntity<Paiement> supprimePaiment(@PathVariable int id) {

        Optional<Paiement> paimentExistant = paimentDao.findById(id);

        if(paimentExistant.isPresent()) {
            paimentDao.deleteById(id);
            return new ResponseEntity<>(paimentExistant.get(),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
