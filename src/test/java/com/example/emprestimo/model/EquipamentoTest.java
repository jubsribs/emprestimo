package com.example.emprestimo.model;

import com.example.emprestimo.exception.ConflitoException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EquipamentoTest {

    @Test
    void deveEmprestarEquipamentoDisponivel() {

        Equipamento equipamento = new Equipamento();

        equipamento.setDisponivel(true);

        equipamento.emprestar();

        assertFalse(equipamento.isDisponivel());
    }

    @Test
    void naoDeveEmprestarEquipamentoIndisponivel() {

        Equipamento equipamento = new Equipamento();

        equipamento.setDisponivel(false);

        assertThrows(ConflitoException.class, () -> {
            equipamento.emprestar();
        });
    }

    @Test
    void devolverEquipamentoIndisponivel() {

        Equipamento equipamento = new Equipamento();
        equipamento.setDisponivel(false);

        equipamento.devolver();

        assertTrue(equipamento.isDisponivel());
    }
}