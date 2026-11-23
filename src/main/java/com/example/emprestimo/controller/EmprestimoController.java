package com.example.emprestimo.controller;

import com.example.emprestimo.dto.EmprestimoRequest;
import com.example.emprestimo.model.Emprestimo;
import com.example.emprestimo.service.EmprestimoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emprestimos")
public class EmprestimoController {
    private final EmprestimoService emprestimoService;

    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Emprestimo emprestar(
            @ModelAttribute @Valid EmprestimoRequest pedido) {
        return emprestimoService.emprestar(pedido);
    }

    @GetMapping
    public List<Emprestimo> listar(
            @RequestParam(required = false)
            Boolean atrasados) {
        return emprestimoService.listar(atrasados);
    }

    @GetMapping("/{id}")
    public Emprestimo buscar(@PathVariable Long id) {
        return emprestimoService.buscar(id);
    }

    @PutMapping("/{id}/devolucao")
    public Emprestimo devolver(
            @PathVariable Long id) {
        return emprestimoService.devolver(id);
    }
}




