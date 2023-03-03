package com.example.magasin.controllers;

import com.example.magasin.dao.CommandeDao;
import com.example.magasin.models.Commande;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class CommandeController {
    @Autowired
    private CommandeDao commandeDao;

    @GetMapping("/commande/{id}")

    public ResponseEntity<Commande> getCommande(@PathVariable int id) {

        Optional<Commande> commandeExistant = commandeDao.findById(id);

        if(commandeExistant.isPresent()) {
            return new ResponseEntity<>(commandeExistant.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/liste-commande")

    public List<Commande> getCommandes() {

        return commandeDao.findAll();
    }

    @PostMapping("/commande")
    public ResponseEntity<Commande> ajoutCommande(@RequestBody Commande commande){

        //si l'commande à un identifiant
        if(commande.getId() != null) {
            Optional<Commande> commandeExistant =
                    commandeDao.findById(commande.getId());

            //l'commande à fournit un id existant dans la BDD (c'est un update)
            if(commandeExistant.isPresent()) {
                commandeDao.save(commande);
                return new ResponseEntity<>(commande,HttpStatus.OK);
            } else {
                //l'commande à fournit un id qui n'existe pas dans la BDD (c'est une erreur)
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            commandeDao.save(commande);
            return new ResponseEntity<>(commande,HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/commande/{id}")
    public ResponseEntity<Commande> supprimeCommande(@PathVariable int id) {

        Optional<Commande> commandeExistant = commandeDao.findById(id);

        if(commandeExistant.isPresent()) {
            commandeDao.deleteById(id);
            return new ResponseEntity<>(commandeExistant.get(),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
