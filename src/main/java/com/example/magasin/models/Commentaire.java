package com.example.magasin.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.Optional;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Commentaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String texte;
    private int note;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreer;

    @ManyToOne
    @JoinColumn(name = "id_article")
    private Article article;

    @OneToOne(mappedBy = "commentaire")
    @JsonIgnore
    private Notification notification;


}
