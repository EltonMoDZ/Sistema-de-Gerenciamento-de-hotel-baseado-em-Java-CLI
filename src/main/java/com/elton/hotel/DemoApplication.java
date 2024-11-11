package com.elton.hotel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.elton.hotel.movimentacoes.Movimentacao;
import com.elton.hotel.repository.RegistroRepository;

@SuppressWarnings("unused")
@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
	@Autowired
	private RegistroRepository registroRepository;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Movimentacao movimentacao = new Movimentacao(registroRepository);
	}

}
