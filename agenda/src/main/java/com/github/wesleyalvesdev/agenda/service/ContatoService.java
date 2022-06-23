package com.github.wesleyalvesdev.agenda.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import javax.servlet.http.Part;

import com.github.wesleyalvesdev.agenda.model.Contato;
import com.github.wesleyalvesdev.agenda.repository.ContatoRepository;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ContatoService {

	@Autowired
	private ContatoRepository contatoRepository;

	public Contato save(Contato contato) {
		return contatoRepository.save(contato);
	}

	public Page<Contato> listAll(Pageable paged) {
		return contatoRepository.findAll(paged);
	}

	public Page<Contato> listFavorite(Pageable paged) {
		return contatoRepository.findByFavorito(paged);
	}

	public void favorite(Integer id) {
		Optional<Contato> contato = contatoRepository.findById(id);
		contato.ifPresent(c -> {
			boolean favorito = c.getFavorito() == Boolean.TRUE;
			c.setFavorito(!favorito);
			contatoRepository.save(c);
		});
	}

	public HttpStatus addPhoto(Integer id, Part arquivo) {
		Optional<Contato> contato = contatoRepository.findById(id);
		return contato.map(c -> {
			try {
				InputStream is = arquivo.getInputStream();
				byte[] bytes = new byte[(int) arquivo.getSize()];
				IOUtils.readFully(is, bytes);
				c.setFoto(bytes);
				contatoRepository.save(c);
				is.close();
				return HttpStatus.OK;
			} catch (IOException e) {
				return null;
			}
		}).orElse(null);
	}

	public void delete(Integer id) {
		contatoRepository.deleteById(id);
	}
}
