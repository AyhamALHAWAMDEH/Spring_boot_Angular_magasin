package com.example.magasin.dao;

import com.example.magasin.models.Livraison;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivraisonDao extends JpaRepository<Livraison, Integer> {
}
