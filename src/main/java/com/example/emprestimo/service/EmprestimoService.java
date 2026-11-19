package com.example.emprestimo.service;

import com.example.emprestimo.exception.NaoEncontradoException;
import com.example.emprestimo.model.Emprestimo;
import com.example.emprestimo.repository.EmprestimoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmprestimoService {
    private final EmprestimoRepository repository;
    private final EquipamentoService equipamentoService;
    private final ColaboradorService colaboradorService;
    public EmprestimoService(EmprestimoRepository repository,
                             EquipamentoService equipamentoService,
                             ColaboradorService colaboradorService) {

        this.repository = repository;
        this.equipamentoService = equipamentoService;
        this.colaboradorService = colaboradorService;
    }
    public Emprestimo buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Empréstimo não encontrado"));
    }
    public List<Emprestimo> listarDoColaborador(Long colaboradorId) {
        colaboradorService.buscar(colaboradorId); // 404 se não existir
        return repository.findByColaboradorId(colaboradorId);
    }
}
