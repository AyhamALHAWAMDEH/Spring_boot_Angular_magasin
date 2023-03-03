package com.example.magasin.dao;

import com.example.magasin.models.Annonce;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnonceDao extends JpaRepository<Annonce, Integer> {
}
