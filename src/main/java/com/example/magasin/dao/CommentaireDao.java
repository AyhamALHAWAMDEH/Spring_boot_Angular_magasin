package com.example.magasin.dao;

import com.example.magasin.models.Article;
import com.example.magasin.models.Commentaire;
import com.example.magasin.models.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentaireDao extends JpaRepository<Commentaire, Integer> {

}
