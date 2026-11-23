package com.example.emprestimo.service;

import com.example.emprestimo.dto.EmprestimoRequest;
import com.example.emprestimo.exception.ConflitoException;
import com.example.emprestimo.exception.NaoEncontradoException;
import com.example.emprestimo.model.Colaborador;
import com.example.emprestimo.model.Emprestimo;
import com.example.emprestimo.model.Equipamento;
import com.example.emprestimo.repository.ColaboradorRepository;
import com.example.emprestimo.repository.EmprestimoRepository;
import com.example.emprestimo.repository.EquipamentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmprestimoService {
    private final EmprestimoRepository emprestimorepository;
    private final EquipamentoRepository equipamentoRepository;
    private final ColaboradorRepository colaboradorRepository;
    private final EquipamentoService equipamentoService;
    private final ColaboradorService colaboradorService;
    private static final int PRAZO_EM_DIAS = 14;

    public EmprestimoService(EmprestimoRepository emprestimorepository,
                             EquipamentoService equipamentoService,
                             ColaboradorService colaboradorService,
                             EquipamentoRepository equipamentoRepository,
                             ColaboradorRepository colaboradorRepository ) {

        this.emprestimorepository = emprestimorepository;
        this.equipamentoService = equipamentoService;
        this.colaboradorService = colaboradorService;
        this.equipamentoRepository = equipamentoRepository;
        this.colaboradorRepository = colaboradorRepository;
    }

    public List<Emprestimo> listar(Boolean atrasados) {
        if (Boolean.TRUE.equals(atrasados)) {
            return emprestimorepository.findByDataDevolucaoIsNullAndDataPrevistaDevolucaoBefore(LocalDate.now());
        }
        return emprestimorepository.findAll();
    }

    public Emprestimo buscar(Long id) {
        return emprestimorepository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Empréstimo não encontrado"));
    }
    public List<Emprestimo> listarDoColaborador(Long colaboradorId) {
        colaboradorService.buscar(colaboradorId);
        return emprestimorepository.findByColaboradorId(colaboradorId);
    }


    @Transactional
    public Emprestimo emprestar(EmprestimoRequest pedido) {

        Equipamento equipamento =
                equipamentoService.buscar(pedido.equipamentoId());

        Colaborador colaborador =
                colaboradorService.buscar(pedido.colaboradorId());

        boolean possuiAtrasado =
                emprestimorepository
                        .existsByColaboradorIdAndDataDevolucaoIsNullAndDataPrevistaDevolucaoBefore(
                                colaborador.getId(),
                                LocalDate.now()
                        );

        if (possuiAtrasado) {
            throw new ConflitoException(
                    "Colaborador possui empréstimo atrasado e não pode realizar outro empréstimo"
            );
        }

        if (!equipamento.isDisponivel()) {
            throw new ConflitoException("Equipamento indisponível");
        }


        equipamento.setDisponivel(false);


        Emprestimo emprestimo = new Emprestimo();

        emprestimo.setEquipamento(equipamento);
        emprestimo.setColaborador(colaborador);
        emprestimo.setDataEmprestimo(LocalDate.now());
        emprestimo.setDataPrevistaDevolucao(
                LocalDate.now().plusDays(PRAZO_EM_DIAS)
        );

        return emprestimorepository.save(emprestimo);
    }


    @Transactional
    public Emprestimo devolver(Long id) {
        Emprestimo emprestimo = buscar(id);
        if (emprestimo.getDataDevolucao() != null) {
            throw new ConflitoException("Empréstimo já devolvido");
        }
        emprestimo.setDataDevolucao(LocalDate.now());
        emprestimo.getEquipamento().setDisponivel(true);
        return emprestimorepository.save(emprestimo);
    }
}
