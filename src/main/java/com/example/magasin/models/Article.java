package com.example.magasin.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nom;
    private String description;
    private Double prix;
    private String image;
    private int stock;



    @ManyToOne
    @JoinColumn(name = "id_categorie")
    private Categorie categorie;

    @OneToMany(mappedBy = "article")
    @JsonIgnore
    private List<Commentaire> commentaires;

    @OneToMany(mappedBy = "article")
    @JsonIgnore
    private List<LignePanier> lignePaniers;

    @ManyToMany
    @JoinTable(
            name = "liste_article",
            joinColumns = @JoinColumn(name = "id_article"),
            inverseJoinColumns = @JoinColumn(name = "id_liste")
    )
    private Set<Liste> listes = new HashSet<>();

}
