package com.example.magasin.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Livraison {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String modeLivraison;
    private String creneauMatin;
    private String creneauApresMidi;
    private String creneauSoir;
    private String legendCreneaux;
    private String paimentLivraison;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateLivraisonPrevue;

    @ManyToOne
    @JoinColumn(name = "id_commande")
    private Commande commande;

    @ManyToOne
    @JoinColumn(name = "id_adresse")
    private Adresse adresse;
}
