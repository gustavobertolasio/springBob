package com.exercicio.modelagembanco.domain.entity;

import javax.persistence.Entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Evento
 */

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Participacao {

    @Id
    @Column(name = "IdParticipacao")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idParticipacao;

    @ManyToOne
    @JoinColumn(name = "IdEvento")
    private Evento evento;

    @Column(name = "LoginParticipante", nullable = false, length = 250)
    private String loginParticipante;

    @Column(name = "FlagPresente", nullable = false)
    private Boolean flagPresente;

    @Column(name = "Nota", nullable = false)
    private Integer nota;

    @Column(name = "Comentario", nullable = false, length = 250)
    private String comentario;

}