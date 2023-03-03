package com.example.magasin.controllers;

import com.example.magasin.dao.CategorieDao;
import com.example.magasin.models.Categorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class CategorieController {
    @Autowired
    private CategorieDao categorieDao;

    @GetMapping("/categorie/{id}")

    public ResponseEntity<Categorie> getCategorie(@PathVariable int id) {

        Optional<Categorie> categorieExistant = categorieDao.findById(id);

        if(categorieExistant.isPresent()) {
            return new ResponseEntity<>(categorieExistant.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/liste-categorie")

    public List<Categorie> getCategories() {

        return categorieDao.findAll();
    }

    @PostMapping("/categorie")
    public ResponseEntity<Categorie> ajoutCategorie(@RequestBody Categorie categorie){

        //si l'categorie à un identifiant
        if(categorie.getId() != null) {
            Optional<Categorie> categorieExistant =
                    categorieDao.findById(categorie.getId());

            //l'categorie à fournit un id existant dans la BDD (c'est un update)
            if(categorieExistant.isPresent()) {
                categorieDao.save(categorie);
                return new ResponseEntity<>(categorie,HttpStatus.OK);
            } else {
                //l'categorie à fournit un id qui n'existe pas dans la BDD (c'est une erreur)
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            categorieDao.save(categorie);
            return new ResponseEntity<>(categorie,HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/categorie/{id}")
    public ResponseEntity<Categorie> supprimeCategorie(@PathVariable int id) {

        Optional<Categorie> categorieExistant = categorieDao.findById(id);

        if(categorieExistant.isPresent()) {
            categorieDao.deleteById(id);
            return new ResponseEntity<>(categorieExistant.get(),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
