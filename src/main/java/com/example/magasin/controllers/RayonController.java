package com.example.magasin.controllers;

import com.example.magasin.dao.RayonDao;
import com.example.magasin.models.Rayon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class RayonController {

    @Autowired
    private RayonDao rayonDao;

    @GetMapping("/rayon/{id}")

    public ResponseEntity<Rayon> getRayon(@PathVariable int id) {

        Optional<Rayon> rayonExistant = rayonDao.findById(id);

        if(rayonExistant.isPresent()) {
            return new ResponseEntity<>(rayonExistant.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/liste-rayon")

    public List<Rayon> getRayons() {

        return rayonDao.findAll();
    }

    @PostMapping("/rayon")
    public ResponseEntity<Rayon> ajoutRayon(@RequestBody Rayon rayon){

        //si l'rayon à un identifiant
        if(rayon.getId() != null) {
            Optional<Rayon> rayonExistant =
                    rayonDao.findById(rayon.getId());

            //l'rayon à fournit un id existant dans la BDD (c'est un update)
            if(rayonExistant.isPresent()) {
                rayonDao.save(rayon);
                return new ResponseEntity<>(rayon,HttpStatus.OK);
            } else {
                //l'rayon à fournit un id qui n'existe pas dans la BDD (c'est une erreur)
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            rayonDao.save(rayon);
            return new ResponseEntity<>(rayon,HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/rayon/{id}")
    public ResponseEntity<Rayon> supprimeRayon(@PathVariable int id) {

        Optional<Rayon> rayonExistant = rayonDao.findById(id);

        if(rayonExistant.isPresent()) {
            rayonDao.deleteById(id);
            return new ResponseEntity<>(rayonExistant.get(),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
    
}
