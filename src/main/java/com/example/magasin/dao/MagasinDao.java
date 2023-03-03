package com.example.magasin.dao;

import com.example.magasin.models.Magasin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MagasinDao extends JpaRepository<Magasin, Integer> {
}
