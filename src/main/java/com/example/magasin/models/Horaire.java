package com.example.magasin.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Horaire {
    public enum Jour {
        Lundi, Mardi, Mercredi, Jeudi, Vendredi, Samedi, Dimanche
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('Lundi', 'Mardi', 'Mercredi','Jeudi','Vendredi','Samedi','Dimanche')")
    private Jour jour;
    @Temporal(TemporalType.TIME)
    private Date heureOuverture;
    @Temporal(TemporalType.TIME)
    private Date heureFermeture;

    @ManyToOne
    @JoinColumn(name = "id_magasin")
    private Magasin magasin;
}
