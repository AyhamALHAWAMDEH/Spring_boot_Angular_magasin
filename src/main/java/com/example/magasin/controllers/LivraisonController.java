package com.example.magasin.controllers;

import com.example.magasin.dao.LivraisonDao;
import com.example.magasin.models.Livraison;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class LivraisonController {
    @Autowired
    private LivraisonDao livraisonDao;

    @GetMapping("/livraison/{id}")

    public ResponseEntity<Livraison> getLivraison(@PathVariable int id) {

        Optional<Livraison> livraisonExistant = livraisonDao.findById(id);

        if(livraisonExistant.isPresent()) {
            return new ResponseEntity<>(livraisonExistant.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/liste-livraison")

    public List<Livraison> getLivraisons() {

        return livraisonDao.findAll();
    }

    @PostMapping("/livraison")
    public ResponseEntity<Livraison> ajoutLivraison(@RequestBody Livraison livraison){

        //si l'livraison à un identifiant
        if(livraison.getId() != null) {
            Optional<Livraison> livraisonExistant =
                    livraisonDao.findById(livraison.getId());

            //l'livraison à fournit un id existant dans la BDD (c'est un update)
            if(livraisonExistant.isPresent()) {
                livraisonDao.save(livraison);
                return new ResponseEntity<>(livraison,HttpStatus.OK);
            } else {
                //l'livraison à fournit un id qui n'existe pas dans la BDD (c'est une erreur)
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            livraisonDao.save(livraison);
            return new ResponseEntity<>(livraison,HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/livraison/{id}")
    public ResponseEntity<Livraison> supprimeLivraison(@PathVariable int id) {

        Optional<Livraison> livraisonExistant = livraisonDao.findById(id);

        if(livraisonExistant.isPresent()) {
            livraisonDao.deleteById(id);
            return new ResponseEntity<>(livraisonExistant.get(),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
