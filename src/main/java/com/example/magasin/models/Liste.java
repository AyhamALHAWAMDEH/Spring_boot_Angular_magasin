package com.example.magasin.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Liste {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nom;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreer;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateModifier;
    @ManyToOne
    @JoinColumn(name = "id_utilisateur")
    private Utilisateur utilisateur;


    @ManyToMany
    @JoinTable(
            name = "liste_article",
            joinColumns = @JoinColumn(name = "id_liste"),
            inverseJoinColumns = @JoinColumn(name = "id_article")
    )
    private Set<Article> articles = new HashSet<>();

}
