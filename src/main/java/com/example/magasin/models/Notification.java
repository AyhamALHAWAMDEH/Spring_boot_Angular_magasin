package com.example.magasin.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String titre;
    private String message;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    private boolean lu;

    @OneToOne
    @JoinColumn(name = "id_commentaire")
    private Commentaire commentaire;
}
