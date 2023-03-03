package com.example.magasin.controllers;

import com.example.magasin.dao.AnnonceDao;
import com.example.magasin.models.Annonce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class AnnonceController {
    @Autowired
    private AnnonceDao annonceDao;

    @GetMapping("/annonce/{id}")

    public ResponseEntity<Annonce> getAnnonce(@PathVariable int id) {

        Optional<Annonce> annonceExistant = annonceDao.findById(id);

        if(annonceExistant.isPresent()) {
            return new ResponseEntity<>(annonceExistant.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/liste-annonce")

    public List<Annonce> getAnnonces() {

        return annonceDao.findAll();
    }

    @PostMapping("/annonce")
    public ResponseEntity<Annonce> ajoutAnnonce(@RequestBody Annonce annonce){

        //si l'annonce à un identifiant
        if(annonce.getId() != null) {
            Optional<Annonce> annonceExistant =
                    annonceDao.findById(annonce.getId());

            //l'annonce à fournit un id existant dans la BDD (c'est un update)
            if(annonceExistant.isPresent()) {
                annonceDao.save(annonce);
                return new ResponseEntity<>(annonce,HttpStatus.OK);
            } else {
                //l'annonce à fournit un id qui n'existe pas dans la BDD (c'est une erreur)
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            annonceDao.save(annonce);
            return new ResponseEntity<>(annonce,HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/annonce/{id}")
    public ResponseEntity<Annonce> supprimeAnnonce(@PathVariable int id) {

        Optional<Annonce> annonceExistant = annonceDao.findById(id);

        if(annonceExistant.isPresent()) {
            annonceDao.deleteById(id);
            return new ResponseEntity<>(annonceExistant.get(),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
