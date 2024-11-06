package br.com.wydem.faci.service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.LinkedHashMap;
import java.util.UUID;
import br.com.wydem.faci.entities.Registro;

public class MonetarySys {
    private LinkedHashMap<String, Registro> registros = new LinkedHashMap<>();

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

        var uuid = UUID.randomUUID();
        var idString = gerarUUIDUnico(uuid.toString());
        registros.put(uuid.toString(), new Registro(idString, titulo, descricao, LocalDate.now(), valor, entradaESaida));
        System.out.println("Registro adicionado com sucesso!");
    }

    public void removeRegistro(String uuid) {
        if (registros.containsKey(uuid)) {
            registros.remove(uuid);
            System.out.println("Registro removido com sucesso!");
        } else {
            System.out.println("Registro não encontrado!");
        }
    }

    public void listarRegistros() {
        System.out.println("Listando registros:");
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

    public void listarRegistrosPorTipo(boolean entradaESaida) {
        System.out.println("Listando registros:");
        registros.forEach((k, v) -> {
            if (v.isEntradaESaida() == entradaESaida) {
                System.out.println("UUID: " + v.getUuid());
                System.out.println("Título: " + v.getTitulo());
                System.out.println("Descrição: " + v.getDescricao());
                System.out.println("Data: " + v.getData());
                System.out.println("Valor: " + v.getValor());
                System.out.println("Entrada ou Saída: " + (v.isEntradaESaida() ? "Entrada" : "Saída"));
                System.out.println("=====================================");
            }
        });
    }

    public void listarRegistrosPorMes(int mes) {
        boolean temRegistrosNoMes = registros.values().stream()
                .anyMatch(v -> v.getData().getMonthValue() == mes);
        if (!temRegistrosNoMes) {
            System.out.println("Não há registros para o mês especificado.");
            return;
        }
        System.out.println("Listando registros:");
        registros.forEach((k, v) -> {
            if (v.getData().getMonthValue() == mes) {
                System.out.println("UUID: " + v.getUuid());
                System.out.println("Título: " + v.getTitulo());
                System.out.println("Descrição: " + v.getDescricao());
                System.out.println("Data: " + v.getData());
                System.out.println("Valor: " + v.getValor());
                System.out.println("Entrada ou Saída: " + (v.isEntradaESaida() ? "Entrada" : "Saída"));
                System.out.println("=====================================");
            }
        });
    }

    public void listarRegistrosPorDia(String diaString) {
        int dia;
        try {
            dia = Integer.parseInt(diaString);
        } catch (NumberFormatException e) {
            System.out.println("O dia informado não é um número válido.");
            return;
        }
        LocalDate data;
        try {
            data = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), dia);
        } catch (DateTimeException e) {
            System.out.println("O dia informado não é válido para o mês atual.");
            return;
        }
        boolean temRegistrosNoDia = registros.values().stream()
                .anyMatch(registro -> registro.getData().equals(data));
        if (!temRegistrosNoDia) {
            System.out.println("Não há registros para o dia especificado.");
            return;
        }
        System.out.println("Listando registros por Dia:");
        registros.forEach((key, registro) -> {
            if (registro.getData().equals(data)) {
                System.out.println("UUID: " + registro.getUuid());
                System.out.println("Título: " + registro.getTitulo());
                System.out.println("Descrição: " + registro.getDescricao());
                System.out.println("Data: " + registro.getData());
                System.out.println("Valor: " + registro.getValor());
                System.out.println("Entrada ou Saída: " + (registro.isEntradaESaida() ? "Entrada" : "Saída"));
                System.out.println("=====================================");
            }
        });
    }

    public void listarRegistrosDoDiaAtual() {
        String diaAtual = String.valueOf(LocalDate.now().getDayOfMonth());
        listarRegistrosPorDia(diaAtual);
    }

    public void listarRegistrosPorSemana(int semana, int mes) {
        boolean temRegistrosNaSemana = registros.values().stream()
                .anyMatch(registro ->
                        registro.getData().getMonthValue() == mes &&
                        registro.getData().get(WeekFields.ISO.weekOfMonth()) == semana
                );
        if (!temRegistrosNaSemana) {
            System.out.println("Não há registros para a semana especificada.");
            return;
        }
        System.out.println("Listando registros pela semana do mês:");
        registros.forEach((key, registro) -> {
            if (registro.getData().getMonthValue() == mes &&
                    registro.getData().get(WeekFields.ISO.weekOfMonth()) == semana) {
                System.out.println("UUID: " + registro.getUuid());
                System.out.println("Título: " + registro.getTitulo());
                System.out.println("Descrição: " + registro.getDescricao());
                System.out.println("Data: " + registro.getData());
                System.out.println("Valor: " + registro.getValor());
                System.out.println("Entrada ou Saída: " + (registro.isEntradaESaida() ? "Entrada" : "Saída"));
                System.out.println("=====================================");
            }
        });
    }

    public void listarRegistrosPorSemanaAtual(int semana) {
        listarRegistrosPorSemana(semana, LocalDate.now().getMonthValue());
    }

    public void listarRegistroPorUUID(String uuid) {
        Registro registro = registros.get(uuid);
        if (registro != null) {
            System.out.println("UUID: " + registro.getUuid());
            System.out.println("Título: " + registro.getTitulo());
            System.out.println("Descrição: " + registro.getDescricao());
            System.out.println("Data: " + registro.getData());
            System.out.println("Valor: " + registro.getValor());
            System.out.println("Entrada ou Saída: " + (registro.isEntradaESaida() ? "Entrada" : "Saída"));
            System.out.println("=====================================");
        } else {
            System.out.println("Registro não encontrado com o UUID especificado.");
        }
    }
    
    private String gerarUUIDUnico(String idGerado) {
        String uuid;
        if (registros.containsKey(idGerado)) {
            do {
                uuid = UUID.randomUUID().toString();
            } while (registros.containsKey(uuid));
            return uuid;
        } else {
            return idGerado;
        }
        
    }

}
