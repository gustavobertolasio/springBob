package com.exercicio.modelagembanco.domain.entity;

import javax.persistence.Entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
public class StatusEvento {

    @Id
    @Column(name = "IdEventoStatus")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEventoStatus;

    @Column(name = "NomeStatus", nullable = false, length = 250)
    private String nomeStatus;

}