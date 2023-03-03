package com.example.magasin.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String refernce;
    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCommande;
    private String etatCommande;

    @OneToMany
    @JsonIgnore
    private List<Livraison> livraisons;

    @OneToMany
    @JsonIgnore
    private List<Paiement> paiements;

    @ManyToOne
    @JoinColumn(name = "id_adresse")
    private Adresse adresse;

}
