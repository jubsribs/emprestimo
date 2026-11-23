package com.example.emprestimo.controller;

import com.example.emprestimo.model.Colaborador;
import com.example.emprestimo.model.Emprestimo;
import com.example.emprestimo.service.ColaboradorService;
import com.example.emprestimo.service.EmprestimoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/colaboradores")
public class ColaboradorController {
    private final ColaboradorService colaboradorService;
    private final EmprestimoService emprestimoService;

     public ColaboradorController(ColaboradorService colaboradorService, EmprestimoService emprestimoService){
         this.colaboradorService = colaboradorService;
         this.emprestimoService = emprestimoService;
     }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Colaborador cadastrar(@RequestBody @Valid Colaborador colaborador) {
        return colaboradorService.cadastrar(colaborador);
    }

    @GetMapping
    public List<Colaborador> listar() {
        return colaboradorService.listar();
    }

    @GetMapping("/{id}")
    public Colaborador buscar(@PathVariable Long id) {
        return colaboradorService.buscar(id);
    }

    @GetMapping("/{id}/emprestimos")
    public List<Emprestimo> emprestimos(
            @PathVariable Long id) {
        return emprestimoService.listarDoColaborador(id);
    }

}
