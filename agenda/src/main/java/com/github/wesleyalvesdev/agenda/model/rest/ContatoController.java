package com.github.wesleyalvesdev.agenda.model.rest;

import javax.servlet.http.Part;
import javax.transaction.Transactional;

import com.github.wesleyalvesdev.agenda.model.entity.Contato;
import com.github.wesleyalvesdev.agenda.model.service.ContatoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contatos")
@CrossOrigin("*")
public class ContatoController {

	@Autowired
	private ContatoService contatoService;

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public Contato save(@RequestBody Contato contato) {
		return contatoService.save(contato);
	}

	@PutMapping("{id}/foto")
	@Transactional
	public HttpStatus addPhoto(@PathVariable Integer id, @RequestParam("foto") Part arquivo) {
		return contatoService.addPhoto(id, arquivo);
	}

	@GetMapping
	@ResponseStatus(value = HttpStatus.OK)
	@Transactional
	public Page<Contato> listAll(
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size,
			@PageableDefault(sort = "nome", direction = Direction.ASC) Pageable paginacao) {
		Pageable paged = PageRequest.of(page, size, Direction.ASC,"nome");
		return contatoService.listAll(paged);
	}

	@GetMapping("/favoritos")
	@ResponseStatus(value = HttpStatus.OK)
	@Transactional
	public Page<Contato> listFavorite(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size,
			@PageableDefault(sort = "nome", direction = Direction.ASC) Pageable paginacao) {
		Pageable paged = PageRequest.of(page, size, Direction.ASC,"nome");
		return contatoService.listFavorite(paged);
	}

	@PatchMapping("{id}/favorito")
	@Transactional
	public void favorite(@PathVariable Integer id) {
		contatoService.favorite(id);
	}

	@DeleteMapping("{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Integer id) {
		contatoService.delete(id);
	}
}
