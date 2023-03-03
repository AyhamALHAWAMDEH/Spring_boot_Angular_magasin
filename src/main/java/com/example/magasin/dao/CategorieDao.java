package com.example.magasin.dao;

import com.example.magasin.models.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieDao extends JpaRepository<Categorie, Integer> {
}
