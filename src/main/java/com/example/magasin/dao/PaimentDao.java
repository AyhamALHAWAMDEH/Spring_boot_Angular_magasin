package com.example.magasin.dao;

import com.example.magasin.models.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaimentDao extends JpaRepository<Paiement, Integer> {
}
