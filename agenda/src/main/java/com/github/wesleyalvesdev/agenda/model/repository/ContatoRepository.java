package com.github.wesleyalvesdev.agenda.model.repository;

import com.github.wesleyalvesdev.agenda.model.entity.Contato;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ContatoRepository extends JpaRepository<Contato, Integer> {

	@Query(value = "select c from Contato c where c.favorito = true")
	Page<Contato> findByFavorito(Pageable paged);

}
