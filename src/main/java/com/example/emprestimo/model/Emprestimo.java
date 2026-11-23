package com.example.emprestimo.model;

import com.example.emprestimo.exception.ConflitoException;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Equipamento equipamento;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Colaborador colaborador;


    @Column(nullable = false)
    private LocalDate dataEmprestimo;

    @Column(nullable = false)
    private LocalDate dataPrevistaDevolucao;

    private LocalDate dataDevolucao;

    public Emprestimo() { }

    public Emprestimo(Equipamento equipamento, Colaborador colaborador, LocalDate hoje) {
        this.equipamento = equipamento;
        this.colaborador = colaborador;

        equipamento.emprestar();

        this.dataEmprestimo = hoje;
        this.dataPrevistaDevolucao = hoje.plusDays(14);
    }

    public boolean isAtrasado(LocalDate dataConsulta) {
        return dataDevolucao == null
                && dataPrevistaDevolucao
                .isBefore(dataConsulta);
    }

    public void devolver(LocalDate hoje) {
        if (dataDevolucao != null) {
            throw new ConflitoException("Este empréstimo já foi devolvido!");
        }

        this.dataDevolucao = hoje;
        this.equipamento.devolver();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Equipamento getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(Equipamento equipamento) {
        this.equipamento = equipamento;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }
    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public LocalDate getDataPrevistaDevolucao() {
        return dataPrevistaDevolucao;
    }

    public void setDataPrevistaDevolucao(LocalDate dataPrevistaDevolucao) {
        this.dataPrevistaDevolucao = dataPrevistaDevolucao;
    }
    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }




}
