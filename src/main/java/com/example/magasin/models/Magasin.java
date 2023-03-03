package com.example.magasin.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Magasin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String description;
    private String nom;
    private String proprietaire;
    private String adresse;
    private String emplacement;
    private String telephone;
    private String email;
    private String logo;


    @OneToMany(mappedBy = "magasin")
    @JsonIgnore
    private Set<Rayon> rayons = new HashSet<>();

    @OneToMany(mappedBy = "magasin")
    @JsonIgnore
    private Set<Horaire> horaires = new HashSet<>();
}
