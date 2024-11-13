package com.elton.hotel.service;

import com.elton.hotel.Entities.Acompanhante;
import com.elton.hotel.Entities.Hospede;
import com.elton.hotel.movimentacoes.Movimentacao;
import com.elton.hotel.repository.AcompanhanteRepository;
import com.elton.hotel.repository.HospedeRepository;
import com.elton.hotel.repository.RegistroRepository;
import com.elton.hotel.repository.ReservaRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private Movimentacao movimentacao;
    private ReservaService reservaService;

    public Menu(RegistroRepository registroRepository, HospedeRepository hospedeRepository, ReservaRepository reservaRepository, AcompanhanteRepository acompanhanteRepository) {
        this.movimentacao = new Movimentacao(registroRepository);
        this.reservaService = new ReservaService(hospedeRepository, reservaRepository, acompanhanteRepository);
    }

    public void exibirMenu() {
        System.out.println("Bem-vindo ao sistema de gerenciamento do hotel!");
        System.out.println("1. Adicionar registro");
        System.out.println("2. Remover registro");
        System.out.println("3. Listar registros");
        System.out.println("4. Ver saldo");
        System.out.println("5. Fazer reserva");
        System.out.println("6. Cancelar reserva");
        System.out.println("7. Listar reservas ativas");
        System.out.println("8. Sair");
        System.out.print("Escolha uma opção: ");
        return;
    }

    public void iniciar() {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        while (continuar) {
            reservaService.finalizaReserva();
            exibirMenu();
            int opcao = -1;
            try {
                opcao = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Opção inválida! Tente novamente.");
                scanner.nextLine();
                continue;
            }

            switch (opcao) {
                case 1:
                    System.out.print("Digite o título do registro: ");
                    String titulo = scanner.nextLine();
                    System.out.print("Digite a descrição do registro: ");
                    String descricao = scanner.nextLine();
                    System.out.print("Digite o valor do registro: ");
                    double valor = scanner.nextDouble();
                    System.out.print("É uma entrada? (true/false): ");
                    boolean entradaESaida = scanner.nextBoolean();
                    movimentacao.addRegistro(titulo, descricao, valor, entradaESaida);
                    break;
                case 2:
                    System.out.print("Digite o UUID do registro a ser removido: ");
                    String uuid = scanner.nextLine();
                    movimentacao.removeRegistro(uuid);
                    break;
                case 3:
                    movimentacao.listarRegistros();
                    break;
                case 4:
                    double saldo = movimentacao.getSaldo();
                    System.out.println("Saldo atual: " + saldo);
                    break;
                case 5:
                    System.out.print("Digite a data de entrada (AAAA-MM-DD): ");
                    LocalDate dataEntrada = LocalDate.parse(scanner.nextLine());
                    System.out.print("Digite a data de saída (AAAA-MM-DD): ");
                    LocalDate dataSaida = LocalDate.parse(scanner.nextLine());
                    System.out.print("Digite o nome do hóspede: ");
                    String nomeHospede = scanner.nextLine();
                    System.out.print("Digite o CPF do hóspede: ");
                    String cpfHospede = scanner.nextLine();
                    System.out.print("Digite o telefone do hóspede: ");
                    String telefoneHospede = scanner.nextLine();
                    System.out.print("Digite a cidade do hóspede: ");
                    String cidadeHospede = scanner.nextLine();
                    System.out.print("Digite o estado do hóspede: ");
                    String estadoHospede = scanner.nextLine();
                    Hospede hospede = new Hospede();
                    System.out.println("Terá acompanhantes? Se sim, digite o nome, cpf e telefone de cada um. Se não, dê Enter");
                    var ListaAcompanhantes = new ArrayList<Acompanhante>();
                    do {
                        System.out.print("Digite o nome do acompanhante: ");
                        String nomeAcompanhante = scanner.nextLine();
                        if (nomeAcompanhante.isEmpty()) {
                            break;
                        }
                        System.out.print("Digite o CPF do acompanhante: ");
                        String cpfAcompanhante = scanner.nextLine();
                        System.out.print("Digite o telefone do acompanhante: ");
                        String telefoneAcompanhante = scanner.nextLine();
                        Acompanhante acompanhante = new Acompanhante();
                        acompanhante.setNome(nomeAcompanhante);
                        acompanhante.setCpf(cpfAcompanhante);
                        acompanhante.setTelefone(telefoneAcompanhante);
                        ListaAcompanhantes.add(acompanhante);
                    } while (true);
                    double valorDiaria;
                    while (true) {
                        System.out.println("Valor da diária: ");
                        String input = scanner.nextLine();
                        try {
                            valorDiaria = Double.parseDouble(input);
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Entrada inválida! Por favor, insira um número válido.");
                        }
                    }
                    hospede.setNome(nomeHospede);
                    hospede.setCpf(cpfHospede);
                    hospede.setTelefone(telefoneHospede);
                    hospede.setCidade(cidadeHospede);
                    hospede.setEstado(estadoHospede);
                    hospede.setAcompanhantes(ListaAcompanhantes);
                    reservaService.reservar(dataEntrada, dataSaida, hospede, valorDiaria);
                    movimentacao.addRegistro("Reserva de Hospede: " + nomeHospede, "Dia de entrada: " + dataEntrada + " Dia de Saida: " + dataSaida + " Numero de acompanhantes: " + ListaAcompanhantes.stream().count(), valorDiaria * (dataEntrada.until(dataSaida).getDays()) * (ListaAcompanhantes.stream().count() + 1), true);
                    break;
                case 6:
                    System.out.println("Digite o ID da reserva a ser cancelada: ");
                    Long id = scanner.nextLong();
                    reservaService.cancelarReserva(id);
                    break;
                case 7:
                    reservaService.listarReservasAtivas();
                    break;
                case 8:
                    continuar = false;
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }

        scanner.close();
    }

}
