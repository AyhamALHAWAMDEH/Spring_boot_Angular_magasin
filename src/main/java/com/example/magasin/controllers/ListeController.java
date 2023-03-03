package com.example.magasin.controllers;

import com.example.magasin.dao.ListeDao;
import com.example.magasin.models.Liste;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class ListeController {
    @Autowired
    private ListeDao listeDao;

    @GetMapping("/liste/{id}")

    public ResponseEntity<Liste> getListe(@PathVariable int id) {

        Optional<Liste> listeExistant = listeDao.findById(id);

        if(listeExistant.isPresent()) {
            return new ResponseEntity<>(listeExistant.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/liste-liste")

    public List<Liste> getListes() {

        return listeDao.findAll();
    }

    @PostMapping("/liste")
    public ResponseEntity<Liste> ajoutListe(@RequestBody Liste liste){

        //si l'liste à un identifiant
        if(liste.getId() != null) {
            Optional<Liste> listeExistant =
                    listeDao.findById(liste.getId());

            //l'liste à fournit un id existant dans la BDD (c'est un update)
            if(listeExistant.isPresent()) {
                listeDao.save(liste);
                return new ResponseEntity<>(liste,HttpStatus.OK);
            } else {
                //l'liste à fournit un id qui n'existe pas dans la BDD (c'est une erreur)
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            listeDao.save(liste);
            return new ResponseEntity<>(liste,HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/liste/{id}")
    public ResponseEntity<Liste> supprimeListe(@PathVariable int id) {

        Optional<Liste> listeExistant = listeDao.findById(id);

        if(listeExistant.isPresent()) {
            listeDao.deleteById(id);
            return new ResponseEntity<>(listeExistant.get(),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
