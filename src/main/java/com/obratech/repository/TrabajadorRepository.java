package com.obratech.repository;

import com.obratech.entity.Trabajador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrabajadorRepository extends JpaRepository<Trabajador, Long> {
}
