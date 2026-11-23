package com.example.emprestimo;

import com.example.emprestimo.model.Equipamento;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class EmprestimoApplicationTests {

	@Test
	void equipamentoNovoNasceDisponivel() {
// 1. prepara: monta o cenário
		Equipamento equipamento = new Equipamento();
// 2. executa: faz a ação que está sendo testada
		boolean disponivel = equipamento.isDisponivel();
// 3. confere: compara com o esperado
		assertTrue(disponivel);
	}

}
