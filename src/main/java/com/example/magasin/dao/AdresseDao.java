package com.example.magasin.dao;

import com.example.magasin.models.Adresse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdresseDao extends JpaRepository<Adresse, Integer> {
}
