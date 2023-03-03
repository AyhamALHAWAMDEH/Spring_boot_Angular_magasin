package com.example.magasin.dao;

import com.example.magasin.models.Liste;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListeDao extends JpaRepository<Liste, Integer> {
}
