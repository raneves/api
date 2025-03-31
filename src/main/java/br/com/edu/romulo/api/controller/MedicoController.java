package br.com.edu.romulo.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.edu.romulo.api.domain.Medico;
import br.com.edu.romulo.api.dto.DadosCadastroMedicoDTO;
import br.com.edu.romulo.api.repository.MedicoRepository;
import jakarta.validation.Valid;


@RestController
@RequestMapping("medicos")
public class MedicoController {
	@Autowired
	private MedicoRepository medicoRepository;
	
	@PostMapping
	@Transactional //tem que ser do spring, para gravar no banco de dadoss
    public void cadastrar(@RequestBody @Valid DadosCadastroMedicoDTO dados) {
		medicoRepository.save(new Medico(dados));	
		//System.out.println(dados);
    }
}
