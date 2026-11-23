package com.example.emprestimo.controller;

import com.example.emprestimo.model.Equipamento;
import com.example.emprestimo.model.TipoEquipamento;
import com.example.emprestimo.service.EquipamentoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipamentos")
public class EquipamentoController {
    private final EquipamentoService equipamentoService;
    public EquipamentoController(EquipamentoService equipamentoService){
        this.equipamentoService = equipamentoService;
    }

    @GetMapping
    public List<Equipamento> listar(
            @RequestParam(required = false)
            TipoEquipamento tipo,
            @RequestParam(required = false)
            Boolean disponivel) {
        return equipamentoService.listar(tipo, disponivel);
    }

    @GetMapping("/{id}")
    public Equipamento buscar(@PathVariable Long id) { return equipamentoService.buscar(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Equipamento cadastrar(@RequestBody @Valid Equipamento equipamento) {
        return equipamentoService.cadastrar(equipamento);
    }

    @PutMapping("/{id}")
    public Equipamento atualizar( @PathVariable Long id, @RequestBody @Valid Equipamento dados){
            return equipamentoService.atualizar(id,dados);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover (@PathVariable Long id){
         equipamentoService.remover(id);
    }

}
