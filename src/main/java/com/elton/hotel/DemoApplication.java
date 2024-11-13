package com.elton.hotel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.elton.hotel.repository.AcompanhanteRepository;
import com.elton.hotel.repository.HospedeRepository;
import com.elton.hotel.repository.RegistroRepository;
import com.elton.hotel.repository.ReservaRepository;
import com.elton.hotel.service.Menu;


@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
	@Autowired
	private RegistroRepository registroRepository;
	@Autowired
	private HospedeRepository hospedeRepository;
	@Autowired
	private ReservaRepository reservaRepository;
	@Autowired
	private AcompanhanteRepository acompanhanteRepository;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		new Menu(registroRepository, hospedeRepository, reservaRepository, acompanhanteRepository).iniciar();
	}

}
