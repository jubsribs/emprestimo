package com.example.emprestimo.service;

import com.example.emprestimo.exception.ConflitoException;
import com.example.emprestimo.exception.NaoEncontradoException;
import com.example.emprestimo.model.Colaborador;
import com.example.emprestimo.repository.ColaboradorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColaboradorService {
    private final ColaboradorRepository colaboradorRepository;
    public ColaboradorService(ColaboradorRepository repository) {
        this.colaboradorRepository = repository;
    }

    public List<Colaborador> listar() { return colaboradorRepository.findAll(); }

    public Colaborador buscar(Long id) {
        return colaboradorRepository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Colaborador não encontrado"));
    }
    public Colaborador cadastrar(Colaborador colaborador) {
        if (colaboradorRepository.existsByEmail(colaborador.getEmail())) {
            throw new ConflitoException("E-mail já cadastrado");
        }
        return colaboradorRepository.save(colaborador);
    }
}