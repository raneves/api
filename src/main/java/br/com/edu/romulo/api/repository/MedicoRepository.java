package br.com.edu.romulo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.edu.romulo.api.domain.Medico;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
	
}
