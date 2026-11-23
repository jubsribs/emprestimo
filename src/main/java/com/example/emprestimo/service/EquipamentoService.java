package com.example.emprestimo.service;

import com.example.emprestimo.exception.ConflitoException;
import com.example.emprestimo.model.Equipamento;
import com.example.emprestimo.model.TipoEquipamento;
import com.example.emprestimo.repository.EmprestimoRepository;
import com.example.emprestimo.repository.EquipamentoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EquipamentoService {
    private final EquipamentoRepository equipamentoRepository;
    private final EmprestimoRepository emprestimoRepository;

    public EquipamentoService(EquipamentoRepository equipamentoRepository, EmprestimoRepository emprestimoRepository) {
        this.equipamentoRepository = equipamentoRepository;
        this.emprestimoRepository = emprestimoRepository;
    }

    public List<Equipamento> listar(
            TipoEquipamento tipo,
            Boolean disponivel) {

        if (tipo != null && disponivel != null) {
            return equipamentoRepository.findByTipoandDisponivel(tipo, disponivel);
        }

        if (tipo != null) {
            return equipamentoRepository.findByTipo(tipo);
        }

        if (disponivel != null) {
            return equipamentoRepository.findByDisponivel(disponivel);
        }

        return equipamentoRepository.findAll();
    }

    public Equipamento buscar(Long id) {
        return equipamentoRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Equipamento não encontrado"));
    }

    public Equipamento cadastrar(Equipamento equipamento) {
        if (equipamentoRepository.existsByPatrimonio(equipamento.getPatrimonio())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Patrimônio já cadastrado");
        }
        equipamento.setDisponivel(true);  // todo equipamento novo nasce disponível
        return equipamentoRepository.save(equipamento);
    }

    public Equipamento atualizar(Long id,
                                 Equipamento dados) {
        Equipamento equipamento = buscar(id);
        equipamento.setNome(dados.getNome());
        equipamento.setTipo(dados.getTipo());
        return equipamentoRepository.save(equipamento);
    }

    public void remover(Long id) {
        Equipamento equipamento = buscar(id);
        if (emprestimoRepository
                .existsByEquipamentoId(id)) {
            throw new ConflitoException(
                    "Equipamento tem empréstimos registrados");
        }
        equipamentoRepository.delete(equipamento);
    }
}
