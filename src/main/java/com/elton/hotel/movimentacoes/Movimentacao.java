package com.elton.hotel.movimentacoes;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.elton.hotel.movimentacoes.Entities.Registro;
import com.elton.hotel.repository.RegistroRepository;

@Service
public class Movimentacao {
    private LinkedHashMap<String, Registro> registros = new LinkedHashMap<>();

    private RegistroRepository registroRepository;

    public Movimentacao(RegistroRepository registroRepository) {
        this.registroRepository = registroRepository;
    }

    public void addRegistro(String titulo, String descricao, Double valor, boolean entradaESaida) {
        if (titulo.isEmpty()) {
            System.out.println("O título não pode ser vazio!");
            return;
        }
        if (valor <= 0) {
            System.out.println("O valor não pode ser menor ou igual a zero!");
            return;
        }
        if (valor.isNaN()) {
            valor = 0.0;
        }
        if (descricao == null) {
            descricao = "";
        }
        if (descricao.isEmpty()) {
            descricao = "";
        }
        if (!entradaESaida) {
            if (getSaldo() < valor) {
                System.out.println("Saldo insuficiente!");
                return;
            }
        }

        var idString = gerarUUIDUnico(UUID.randomUUID().toString());
        // registros.put(idString.toString(), new Registro(idString, titulo, descricao, LocalDate.now(), valor, entradaESaida));
        var registro = new Registro(idString, titulo, descricao, LocalDate.now(), valor, entradaESaida);
        registroRepository.save(registro);
        System.out.println("Registro adicionado com sucesso!");
    }

    public void removeRegistro(String uuid) {
        if (registroRepository.existsById(uuid)) {
            registroRepository.deleteById(uuid);
            System.out.println("Registro removido com sucesso!");
        } else {
            System.out.println("Registro não encontrado!");
        }
    }

    public void listarRegistros() {
        System.out.println("Listando registros:");
        System.out.println("=====================================");
        registros = registroRepository.findAll().stream()
                .collect(LinkedHashMap::new, (map, registro) -> map.put(registro.getUuid(), registro), LinkedHashMap::putAll);
        registros.forEach((k, v) -> {
            System.out.println("UUID: " + v.getUuid());
            System.out.println("Título: " + v.getTitulo());
            System.out.println("Descrição: " + v.getDescricao());
            System.out.println("Data: " + v.getData());
            System.out.println("Valor: " + v.getValor());
            System.out.println("Entrada ou Saída: " + (v.isEntradaESaida() ? "Entrada" : "Saída"));
            System.out.println("=====================================");
        });
    }

    public double getSaldo() {
        registros = registroRepository.findAll().stream()
                .collect(LinkedHashMap::new, (map, registro) -> map.put(registro.getUuid(), registro), LinkedHashMap::putAll);
        double entradas = registros.values().stream()
                .filter(Registro::isEntradaESaida)
                .mapToDouble(Registro::getValor)
                .sum();
        double saidas = registros.values().stream()
                .filter(registro -> !registro.isEntradaESaida())
                .mapToDouble(Registro::getValor)
                .sum();
        return entradas - saidas;
    }

            private String gerarUUIDUnico(String idGerado) {
        String uuid;
        if (registroRepository.existsById(uuid = idGerado)) {
            do {
                uuid = UUID.randomUUID().toString();
            } while (registroRepository.existsById(uuid = idGerado));
            return uuid;
        } else {
            return idGerado;
        }
    }
}
