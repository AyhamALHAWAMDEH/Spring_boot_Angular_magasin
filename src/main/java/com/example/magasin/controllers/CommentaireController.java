package com.example.magasin.controllers;

import com.example.magasin.dao.CommentaireDao;
import com.example.magasin.models.Commentaire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class CommentaireController {
    @Autowired
    private CommentaireDao commentaireDao;

    @GetMapping("/commentaire/{id}")

    public ResponseEntity<Commentaire> getCommentaire(@PathVariable int id) {

        Optional<Commentaire> commentaireExistant = commentaireDao.findById(id);

        if(commentaireExistant.isPresent()) {
            return new ResponseEntity<>(commentaireExistant.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/liste-commentaire")

    public List<Commentaire> getCommentaires() {

        return commentaireDao.findAll();
    }

    @PostMapping("/commentaire")
    public ResponseEntity<Commentaire> ajoutCommentaire(@RequestBody Commentaire commentaire){

        //si l'commentaire à un identifiant
        if(commentaire.getId() != null) {
            Optional<Commentaire> commentaireExistant =
                    commentaireDao.findById(commentaire.getId());

            //l'commentaire à fournit un id existant dans la BDD (c'est un update)
            if(commentaireExistant.isPresent()) {
                commentaireDao.save(commentaire);
                return new ResponseEntity<>(commentaire,HttpStatus.OK);
            } else {
                //l'commentaire à fournit un id qui n'existe pas dans la BDD (c'est une erreur)
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            commentaireDao.save(commentaire);
            return new ResponseEntity<>(commentaire,HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/commentaire/{id}")
    public ResponseEntity<Commentaire> supprimeCommentaire(@PathVariable int id) {

        Optional<Commentaire> commentaireExistant = commentaireDao.findById(id);

        if(commentaireExistant.isPresent()) {
            commentaireDao.deleteById(id);
            return new ResponseEntity<>(commentaireExistant.get(),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
