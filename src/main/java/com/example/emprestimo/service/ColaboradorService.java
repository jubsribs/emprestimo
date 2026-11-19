package com.example.emprestimo.service;

import com.example.emprestimo.exception.ConflitoException;
import com.example.emprestimo.exception.NaoEncontradoException;
import com.example.emprestimo.model.Colaborador;
import com.example.emprestimo.repository.ColaboradorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColaboradorService {
    private final ColaboradorRepository repository;
    public ColaboradorService(ColaboradorRepository repository) {
        this.repository = repository;
    }
    public List<Colaborador> listar() { return repository.findAll(); }
    public Colaborador buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Colaborador não encontrado"));
    }
    public Colaborador cadastrar(Colaborador colaborador) {
        if (repository.existsByEmail(colaborador.getEmail())) {
            throw new ConflitoException("E-mail já cadastrado");
        }
// quarta-feira: aqui entra a busca do endereço no ViaCEP
        return repository.save(colaborador);
    }
}