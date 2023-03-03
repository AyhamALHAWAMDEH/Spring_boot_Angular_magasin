package com.example.magasin.controllers;

import com.example.magasin.dao.LignePanierDao;
import com.example.magasin.models.LignePanier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class LignePanierController {
    @Autowired
    private LignePanierDao lignePanierDao;

    @GetMapping("/lignepanier/{id}")

    public ResponseEntity<LignePanier> getLignePanier(@PathVariable int id) {

        Optional<LignePanier> lignePanierExistant = lignePanierDao.findById(id);

        if(lignePanierExistant.isPresent()) {
            return new ResponseEntity<>(lignePanierExistant.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/liste-lignepanier")

    public List<LignePanier> getLignePaniers() {

        return lignePanierDao.findAll();
    }

    @PostMapping("/lignepanier")
    public ResponseEntity<LignePanier> ajoutLignePanier(@RequestBody LignePanier lignePanier){

        //si l'lignePanier à un identifiant
        if(lignePanier.getId() != null) {
            Optional<LignePanier> lignePanierExistant =
                    lignePanierDao.findById(lignePanier.getId());

            //l'lignePanier à fournit un id existant dans la BDD (c'est un update)
            if(lignePanierExistant.isPresent()) {
                lignePanierDao.save(lignePanier);
                return new ResponseEntity<>(lignePanier,HttpStatus.OK);
            } else {
                //l'lignePanier à fournit un id qui n'existe pas dans la BDD (c'est une erreur)
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            lignePanierDao.save(lignePanier);
            return new ResponseEntity<>(lignePanier,HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/lignepanier/{id}")
    public ResponseEntity<LignePanier> supprimeLignePanier(@PathVariable int id) {

        Optional<LignePanier> lignePanierExistant = lignePanierDao.findById(id);

        if(lignePanierExistant.isPresent()) {
            lignePanierDao.deleteById(id);
            return new ResponseEntity<>(lignePanierExistant.get(),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
