package com.github.wesleyalvesdev.agenda;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AgendaApiApplication implements CommandLineRunner {
	
//	@Bean
//	public CommandLineRunner commandLineRunner(@Autowired ContatoRepository contatoRepository) {
//		return args -> {
//			Contato contato = new Contato();
//			contato.setNome("Wesley Alves");
//			contato.setEmail("wesley@email.com");
//			contato.setFavorito(true);
//			contatoRepository.save(contato);
//		};
//	}

	public static void main(String[] args) {
		SpringApplication.run(AgendaApiApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		System.out.println("Running Application");
	}

}
