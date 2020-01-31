package com.exercicio.modelagembanco.domain.entity;

import javax.persistence.Entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Evento
 */

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Evento")
public class Evento {

    @Id
    @Column(name = "IdEvento")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEvento;

    @ManyToOne
    @JoinColumn(name = "IdEventoStatus")
    private StatusEvento statusEvento;

    @ManyToOne
    @JoinColumn(name = "IdCategoriaEvento")
    private CategoriaEvento categoriaEvento;

    @Column(name = "Nome", nullable = false, length = 250)
    private String nome;

    @Column(name = "DataHoraInicio")
    private Date dataHoraInicio;

    @Column(name = "DataHoraFim")
    private Date dataHoraFim;

    @Column(name = "Local", nullable = false, length = 250)
    private String local;

    @Column(name = "Descricao",nullable = false, length = 250)
    private String descricao;

    @Column(name = "LimiteVagas")
    private Integer limiteVagas;

}