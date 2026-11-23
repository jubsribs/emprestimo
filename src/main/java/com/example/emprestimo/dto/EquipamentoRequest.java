package com.example.emprestimo.dto;

import com.example.emprestimo.model.Equipamento;
import com.example.emprestimo.model.TipoEquipamento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EquipamentoRequest(
        @NotBlank(message = "O patrimônio é obrigatório") String patrimonio,
        @NotBlank(message = "O nome é obrigatório") String nome,
        @NotNull(message = "O tipo é obrigatório") TipoEquipamento tipo) {

}