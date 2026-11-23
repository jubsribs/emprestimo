package com.example.emprestimo.model;

import com.example.emprestimo.exception.ConflitoException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EmprestimoTest {

    @Test
    void deveCriarEmprestimoComDataPrevistaDevolucao() {

        // Arrange
        Equipamento equipamento = new Equipamento();
        equipamento.setDisponivel(true);

        Colaborador colaborador = new Colaborador();

        LocalDate hoje = LocalDate.of(2026, 11, 14);

        // Act
        Emprestimo emprestimo =
                new Emprestimo(equipamento, colaborador, hoje);

        // Assert
        assertEquals(hoje, emprestimo.getDataEmprestimo());

        assertEquals(
                LocalDate.of(2026, 11, 28),
                emprestimo.getDataPrevistaDevolucao()
        );

        assertFalse(equipamento.isDisponivel());
    }

    @Test
    void deveDevolverEmprestimoAberto() {

        // Arrange
        Equipamento equipamento = new Equipamento();
        equipamento.setDisponivel(true);

        Colaborador colaborador = new Colaborador();

        LocalDate dataEmprestimo = LocalDate.of(2026, 11, 14);
        LocalDate dataDevolucao = LocalDate.of(2026, 11, 20);

        Emprestimo emprestimo =
                new Emprestimo(equipamento, colaborador, dataEmprestimo);

        // Act
        emprestimo.devolver(dataDevolucao);

        // Assert
        assertEquals(dataDevolucao, emprestimo.getDataDevolucao());

        assertTrue(equipamento.isDisponivel());
    }

    @Test
    void naoDeveDevolverEmprestimoDuasVezes() {

        // Arrange
        Equipamento equipamento = new Equipamento();
        equipamento.setDisponivel(true);

        Colaborador colaborador = new Colaborador();

        LocalDate hoje = LocalDate.of(2026, 11, 14);

        Emprestimo emprestimo =
                new Emprestimo(equipamento, colaborador, hoje);

        // Primeira devolução
        emprestimo.devolver( LocalDate.now());

        // Segunda devolução: deve lançar exceção
        assertThrows(ConflitoException.class, () -> {
            emprestimo.devolver(LocalDate.now());
        });
    }

    @Test
    void deveIdentificarEmprestimoAtrasado() {

        // Arrange
        Equipamento equipamento = new Equipamento();
        equipamento.setDisponivel(true);

        Colaborador colaborador = new Colaborador();

        LocalDate dataEmprestimo = LocalDate.of(2026, 10, 27);

        Emprestimo emprestimo =
                new Emprestimo(equipamento, colaborador, dataEmprestimo);

        LocalDate dataConsulta = LocalDate.of(2026, 11, 14);

        // Act e Assert
        assertEquals(
                LocalDate.of(2026, 11, 10),
                emprestimo.getDataPrevistaDevolucao()
        );

        assertTrue(emprestimo.isAtrasado(dataConsulta));
        assertFalse(equipamento.isDisponivel());
    }

    @Test
    void naoDeveConsiderarEmprestimoAtrasadoAntesDoPrazo()  {
        Equipamento equipamento = new Equipamento();
        equipamento.setDisponivel(true);

        Colaborador colaborador = new Colaborador();

        LocalDate dataEmprestimo = LocalDate.of(2026, 11, 6);
        LocalDate dataConsulta = LocalDate.of(2026, 11, 14);


        Emprestimo emprestimo =
                new Emprestimo(equipamento, colaborador, dataEmprestimo);


        assertEquals(
                LocalDate.of(2026, 11, 20),
                emprestimo.getDataPrevistaDevolucao()
        );

        assertNull(emprestimo.getDataDevolucao());
        assertFalse(emprestimo.isAtrasado(dataConsulta));
    }
    @Test
    void naoDeveConsiderarAtrasadoQuandoJaFoiDevolvido() {
        Equipamento equipamento = new Equipamento();
        equipamento.setDisponivel(true);

        Colaborador colaborador = new Colaborador();

        LocalDate dataEmprestimo = LocalDate.of(2026, 10, 27);
        LocalDate dataConsulta = LocalDate.of(2026, 11, 14);
        LocalDate dataDevolucao = LocalDate.of(2026, 11, 12);

        Emprestimo emprestimo =
                new Emprestimo(equipamento, colaborador, dataEmprestimo);

        emprestimo.setDataDevolucao(dataDevolucao);

        assertEquals(
                LocalDate.of(2026, 11, 10),
                emprestimo.getDataPrevistaDevolucao()
        );

        assertEquals(dataDevolucao, emprestimo.getDataDevolucao());
        assertFalse(emprestimo.isAtrasado(dataConsulta));
    }

    @Test
    void emprestimoNoPrazo() {
        Equipamento equipamento = new Equipamento();
        equipamento.setDisponivel(true);

        Colaborador colaborador = new Colaborador();

        LocalDate dataEmprestimo = LocalDate.of(2026, 10, 31);
        LocalDate dataConsulta = LocalDate.of(2026, 11, 14);

        Emprestimo emprestimo =
                new Emprestimo(equipamento, colaborador, dataEmprestimo);


        assertEquals(
                LocalDate.of(2026, 11, 14),
                emprestimo.getDataPrevistaDevolucao()
        );

        assertNull(emprestimo.getDataDevolucao());
        assertFalse(emprestimo.isAtrasado(dataConsulta));
    }
}