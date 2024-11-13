package com.elton.hotel.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.elton.hotel.Entities.Hospede;
import com.elton.hotel.Entities.Reserva;
import com.elton.hotel.repository.AcompanhanteRepository;
import com.elton.hotel.repository.HospedeRepository;
import com.elton.hotel.repository.ReservaRepository;

import jakarta.transaction.Transactional;

@Service
public class ReservaService {

    private HospedeRepository hospedeRepository;
    private ReservaRepository reservaRepository;
    private AcompanhanteRepository acompanhanteRepository;

    public ReservaService(HospedeRepository hospedeRepository, ReservaRepository reservaRepository, AcompanhanteRepository acompanhanteRepository) {
        this.hospedeRepository = hospedeRepository;
        this.reservaRepository = reservaRepository;
        this.acompanhanteRepository = acompanhanteRepository;
    }

    public void reservar(LocalDate dataEntrada, LocalDate dataSaida, Hospede hospede, double valorDiaria) {
        if (dataEntrada.isAfter(dataSaida)) {
            System.out.println("Data de entrada não pode ser posterior a data de saída!");
            return;
        } else if (dataSaida.isBefore(LocalDate.now())) {
            System.out.println("Data de saída não pode ser anterior a data atual!");
            return;
        } else if (dataEntrada == null || dataSaida == null) {
            System.out.println("Data de entrada e saída são obrigatórias!");
            return;
        }

        hospedeRepository.save(hospede);

        Reserva reserva = new Reserva();
        reserva.setHospede(hospede);
        reserva.setDataEntrada(dataEntrada);
        reserva.setDataSaida(dataSaida);
        reserva.setValorDiaria(valorDiaria);
        reservaRepository.save(reserva);

        hospede.getAcompanhantes().forEach(a -> {
            a.setReserva(reserva);
            a.setHospede(hospede);
            acompanhanteRepository.save(a);
        });

        System.out.println("Reserva realizada com sucesso!");
    }

    public void cancelarReserva(Long id) {
        Reserva reserva = reservaRepository.findById(id).orElseThrow(() -> new RuntimeException("Reserva não encontrada!"));
        reservaRepository.delete(reserva);
        System.out.println("Reserva cancelada com sucesso!");
    }

    @Transactional
    public void listarReservasAtivas() {
        reservaRepository.findAll().stream().filter(e -> e.isStatus()).forEach(r -> {
            System.out.println("=========================");
            System.out.println("Reserva: " + r.getId());
            System.out.println("Hospede: " + r.getHospede().getNome());
            System.out.println("Data de entrada: " + r.getDataEntrada());
            System.out.println("Data de saída: " + r.getDataSaida());
            if (r.getHospede().getAcompanhantes().size() > 0) {
                System.out.println("Acompanhantes: ");
                r.getHospede().getAcompanhantes().forEach(a -> {
                    System.out.println("Nome: " + a.getNome());
                    System.out.println("CPF: " + a.getCpf());
                });
            }
            System.out.println("=========================");
        });
    }

    @Transactional
    public void finalizaReserva() {
        if (reservaRepository.count() == 0) {
            System.out.println("Nenhuma reserva no sistema!");
            return;
        }
        reservaRepository.findAll().forEach(r -> {
            if (r.getDataSaida().isAfter(LocalDate.now())) {
                r.setStatus(false);
        }
        });
    }
}