
package com.obratech.service;

import com.obratech.entity.Contrato;
import com.obratech.repository.ContratoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContratoService {

    private final ContratoRepository repo;

    public ContratoService(ContratoRepository repo) {
        this.repo = repo;
    }

    public List<Contrato> findAll() { return repo.findAll(); }

    public Optional<Contrato> findById(Long id) { return repo.findById(id); }

    public Contrato save(Contrato c) { return repo.save(c); }

    public void deleteById(Long id) { repo.deleteById(id); }
}
