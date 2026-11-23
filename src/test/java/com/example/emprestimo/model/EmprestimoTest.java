package com.example.emprestimo.model;

/*import com.example.emprestimo.dto.EmprestimoRequest;
import com.example.emprestimo.repository.ColaboradorRepository;
import com.example.emprestimo.repository.EmprestimoRepository;
import com.example.emprestimo.repository.EquipamentoRepository;
import com.example.emprestimo.service.ColaboradorService;
import com.example.emprestimo.service.EmprestimoService;
import com.example.emprestimo.service.EquipamentoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class EmprestimoTest {
    @Mock
    private EmprestimoRepository emprestimoRepository;

    @Mock
    private EquipamentoService equipamentoService;

    @Mock
    private ColaboradorService colaboradorService;

    @Mock
    private EquipamentoRepository equipamentoRepository;

    @Mock
    private ColaboradorRepository colaboradorRepository;

    @InjectMocks
    private EmprestimoService emprestimoService;

    void deveDeixarEquipamentoIndisponivelAoEmprestar() {

        // Arrange - Preparação
        Long equipamentoId = 1L;
        Long colaboradorId = 1L;

        Equipamento equipamento = new Equipamento();
        equipamento.setId(equipamentoId);
        equipamento.setDisponivel(true);

        Colaborador colaborador = new Colaborador();
        colaborador.setId(colaboradorId);

        EmprestimoRequest pedido =
                new EmprestimoRequest(equipamentoId, colaboradorId);

        when(equipamentoService.buscar(equipamentoId))
                .thenReturn(equipamento);

        when(colaboradorService.buscar(colaboradorId))
                .thenReturn(colaborador);

        when(emprestimoRepository
                .existsByColaboradorIdAndDataDevolucaoIsNullAndDataPrevistaDevolucaoBefore(
                        eq(colaboradorId), any()))
                .thenReturn(false);

        when(emprestimoRepository.save(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act - Execução
        emprestimoService.emprestar(pedido);

        // Assert - Verificação
        assertFalse(equipamento.isDisponivel());

        verify(emprestimoRepository).save(any());
    }

}*/