package com.example.magasin.controllers;

import com.example.magasin.dao.HoraireDao;
import com.example.magasin.models.Horaire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class HoraireController {

    @Autowired
    private HoraireDao horaireDao;

    @GetMapping("/horaire/{id}")

    public ResponseEntity<Horaire> getHoraire(@PathVariable int id) {

        Optional<Horaire> horaireExistant = horaireDao.findById(id);

        if(horaireExistant.isPresent()) {
            return new ResponseEntity<>(horaireExistant.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/liste-horaire")

    public List<Horaire> getHoraires() {

        return horaireDao.findAll();
    }

    @PostMapping("/horaire")
    public ResponseEntity<Horaire> ajoutHoraire(@RequestBody Horaire horaire){

        //si l'horaire à un identifiant
        if(horaire.getId() != null) {
            Optional<Horaire> horaireExistant =
                    horaireDao.findById(horaire.getId());

            //l'horaire à fournit un id existant dans la BDD (c'est un update)
            if(horaireExistant.isPresent()) {
                horaireDao.save(horaire);
                return new ResponseEntity<>(horaire,HttpStatus.OK);
            } else {
                //l'horaire à fournit un id qui n'existe pas dans la BDD (c'est une erreur)
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            horaireDao.save(horaire);
            return new ResponseEntity<>(horaire,HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/horaire/{id}")
    public ResponseEntity<Horaire> supprimeHoraire(@PathVariable int id) {

        Optional<Horaire> horaireExistant = horaireDao.findById(id);

        if(horaireExistant.isPresent()) {
            horaireDao.deleteById(id);
            return new ResponseEntity<>(horaireExistant.get(),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
