package com.elton.hotel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.elton.hotel.Entities.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    @Query("SELECT r FROM Reserva r JOIN FETCH r.hospede h JOIN FETCH h.acompanhantes")
    List<Reserva> findAllWithHospedeAndAcompanhantes();

}
