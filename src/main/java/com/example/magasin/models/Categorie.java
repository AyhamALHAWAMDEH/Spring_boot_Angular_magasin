package com.example.magasin.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nom;
    private String description;
    private String image;

    @ManyToOne
    @JoinColumn(name = "id_rayon")
    private Rayon rayon;

    @OneToMany(mappedBy = "categorie")
    @JsonIgnore
    private List<Article> articles;



}
