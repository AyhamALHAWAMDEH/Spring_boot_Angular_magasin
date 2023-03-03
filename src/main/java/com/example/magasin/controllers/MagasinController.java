package com.example.magasin.controllers;

import com.example.magasin.dao.MagasinDao;
import com.example.magasin.models.Magasin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
    /*L'annotation @CrossOrigin est utilisée dans Spring Boot pour autoriser les requêtes HTTP
     provenant d'un domaine différent de celui du serveur. Elle permet de configurer les
     CORS (Cross-Origin Resource Sharing) pour un contrôleur spécifique ou pour l'ensemble de l'application.*/

    public class MagasinController {

        @Autowired
        private MagasinDao magasinDao;

        @GetMapping("/magasin/{id}")

        public ResponseEntity<Magasin> getMagasin(@PathVariable int id) {

            Optional<Magasin> magasinExistant = magasinDao.findById(id);

            if(magasinExistant.isPresent()) {
                return new ResponseEntity<>(magasinExistant.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        }

        @GetMapping("/liste-magasin")

        public List<Magasin> getMagasins() {

            return magasinDao.findAll();
        }

        @PostMapping("/magasin")
        public ResponseEntity<Magasin> ajoutMagasin(@RequestBody Magasin magasin){

            //si l'magasin à un identifiant
            if(magasin.getId() != null) {
                Optional<Magasin> magasinExistant =
                        magasinDao.findById(magasin.getId());

                //l'magasin à fournit un id existant dans la BDD (c'est un update)
                if(magasinExistant.isPresent()) {
                    magasinDao.save(magasin);
                    return new ResponseEntity<>(magasin,HttpStatus.OK);
                } else {
                    //l'magasin à fournit un id qui n'existe pas dans la BDD (c'est une erreur)
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            } else {
                magasinDao.save(magasin);
                return new ResponseEntity<>(magasin,HttpStatus.CREATED);
            }
        }

        @DeleteMapping("/magasin/{id}")
        public ResponseEntity<Magasin> supprimeMagasin(@PathVariable int id) {

            Optional<Magasin> magasinExistant = magasinDao.findById(id);

            if(magasinExistant.isPresent()) {
                magasinDao.deleteById(id);
                return new ResponseEntity<>(magasinExistant.get(),HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }
}
