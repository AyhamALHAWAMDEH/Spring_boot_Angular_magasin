package com.example.magasin.controllers;

import com.example.magasin.dao.ArticleDao;
import com.example.magasin.models.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class ArticleController {
    @Autowired
    private ArticleDao articleDao;

    @GetMapping("/article/{id}")

    public ResponseEntity<Article> getArticle(@PathVariable int id) {

        Optional<Article> articleExistant = articleDao.findById(id);

        if(articleExistant.isPresent()) {
            return new ResponseEntity<>(articleExistant.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/liste-article")

    public List<Article> getArticles() {

        return articleDao.findAll();
    }

    @PostMapping("/article")
    public ResponseEntity<Article> ajoutArticle(@RequestBody Article article){

        //si l'article à un identifiant
        if(article.getId() != null) {
            Optional<Article> articleExistant =
                    articleDao.findById(article.getId());

            //l'article à fournit un id existant dans la BDD (c'est un update)
            if(articleExistant.isPresent()) {
                articleDao.save(article);
                return new ResponseEntity<>(article,HttpStatus.OK);
            } else {
                //l'article à fournit un id qui n'existe pas dans la BDD (c'est une erreur)
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            articleDao.save(article);
            return new ResponseEntity<>(article,HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/article/{id}")
    public ResponseEntity<Article> supprimeArticle(@PathVariable int id) {

        Optional<Article> articleExistant = articleDao.findById(id);

        if(articleExistant.isPresent()) {
            articleDao.deleteById(id);
            return new ResponseEntity<>(articleExistant.get(),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
