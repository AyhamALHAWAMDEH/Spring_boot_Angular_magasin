package com.example.magasin.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nom;
    private String prenom;
    private String telephone;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String motDePasse;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "utilisateur_role",
            joinColumns = @JoinColumn(name = "id_utilisateur"),
            inverseJoinColumns = @JoinColumn(name = "id_role"))
    private Set<Role> listeRole = new HashSet<>();

    @OneToMany(mappedBy = "utilisateur")
    @JsonIgnore
    private List<Liste> listes;

    @OneToMany(mappedBy = "utilisateur")
    @JsonIgnore
    private List<LignePanier> lignePaniers;

    @OneToMany(mappedBy = "utilisateur")
    @JsonIgnore
    private List<Adresse> adresses;


}

