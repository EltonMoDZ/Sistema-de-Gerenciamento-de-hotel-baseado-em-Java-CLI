package br.com.wydem.faci;

import java.util.Scanner;

import br.com.wydem.faci.service.MonetarySys;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Iniciando o sistema financeiro");
        var monetarySys = new MonetarySys();
        monetarySys.addRegistro("Salário", "Salário do mês de janeiro", 1000.0, true);
        monetarySys.addRegistro("Salário", "Salário do mês de fevereiro", 800.0, true);
        monetarySys.addRegistro("Salário", "Salário do mês de março", 3400.0, true);
        monetarySys.listarRegistros();
        monetarySys.listarRegistrosPorTipo(true);
        monetarySys.addRegistro("Saque aniversario", null, 400.0, false);
        monetarySys.listarRegistrosPorTipo(false);
        monetarySys.listarRegistrosPorMes(12);
        monetarySys.listarRegistrosDoDiaAtual();
        monetarySys.listarRegistrosPorSemana(1, 11);
        System.out.println("Me diga qual UUID você deseja remover:");
        var remover = scanner.nextLine();
        monetarySys.removeRegistro(remover);
        monetarySys.listarRegistros();
        System.out.println("Me diga qual UUID você deseja ver:");
        var ver = scanner.nextLine();
        monetarySys.listarRegistroPorUUID(ver);
        scanner.next();
        scanner.close();
    }

    
}