package com.elton.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elton.hotel.movimentacoes.Entities.Registro;

public interface RegistroRepository extends JpaRepository<Registro, String> {
} 
