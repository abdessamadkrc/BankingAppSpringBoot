package com.bankingapp.compteservice.controller;

import com.bankingapp.compteservice.entity.Compte;

import com.bankingapp.compteservice.repository.CompteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comptes")
public class CompteController {

    private final CompteRepository repo;

    public CompteController(CompteRepository repo) { this.repo = repo; }

    @PostMapping
    public Compte create(@RequestBody Compte c){ return repo.save(c); }

    @GetMapping
    public List<Compte> all(){ return repo.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Compte> get(@PathVariable Long id){
        return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Compte> update(@PathVariable Long id, @RequestBody Compte c){
        return repo.findById(id).map(existing -> {
            existing.setType(c.getType());
            existing.setSolde(c.getSolde());
            repo.save(existing);
            return ResponseEntity.ok(existing);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){ repo.deleteById(id); }
}
