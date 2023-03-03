package com.example.magasin.dao;

import com.example.magasin.models.Horaire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HoraireDao extends JpaRepository<Horaire, Integer> {
}
