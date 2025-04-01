package br.com.edu.romulo.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.edu.romulo.api.domain.Medico;
import br.com.edu.romulo.api.dto.DadosAtualizacaoMedicoDTO;
import br.com.edu.romulo.api.dto.DadosCadastroMedicoDTO;
import br.com.edu.romulo.api.dto.DadosListagemMedicosDTO;
import br.com.edu.romulo.api.repository.MedicoRepository;
import jakarta.validation.Valid;
import lombok.experimental.var;


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
	
//	@GetMapping
//    public List<DadosListagemMedicosDTO> listar() {
//        //return medicoRepository.findAll(); //para retornar o objeto
//		//precisamos retornar um DTO
//		return medicoRepository.findAll().stream().map(DadosListagemMedicosDTO::new).toList();
//    }
	
	
	//retornar pageada, cuidado com o import tem que ser do org.springframework.data
//	@GetMapping
//  public Page<DadosListagemMedicosDTO> listar(Pageable paginacao) {
//      //return medicoRepository.findAll(); //para retornar o objeto
//		//precisamos retornar um DTO
//		
//		//http://localhost:8080/medicos?size=1
//		//http://localhost:8080/medicos?size=1&page=1
//		//http://localhost:8080/medicos?sort=nome
//		//http://localhost:8080/medicos?sort=nome,desc
//		//Obs: Se não passarmos o parâmetro size, o Spring devolverá 20 registros por padrão.
//		return medicoRepository.findAll(paginacao).map(DadosListagemMedicosDTO::new);
//  }
	
	//se nao informar o sie e o sort no post main, o padrao sera 10 e sort por nome
	@GetMapping
	public Page<DadosListagemMedicosDTO> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
	      //return medicoRepository.findAll(); //para retornar o objeto
			//precisamos retornar um DTO
			
			//http://localhost:8080/medicos?size=1
			//http://localhost:8080/medicos?size=1&page=1
			//http://localhost:8080/medicos?sort=nome
			//http://localhost:8080/medicos?sort=nome,desc
			//Obs: Se não passarmos o parâmetro size, o Spring devolverá 20 registros por padrão.
		//carrega todos os registros
			//return medicoRepository.findAll(paginacao).map(DadosListagemMedicosDTO::new);
		
		//carregar todos os registros que os usuarios esteja ativos
		return medicoRepository.findAllByAtivoTrue(paginacao).map(DadosListagemMedicosDTO::new);
	  }
	
	@PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizacaoMedicoDTO dados) {
		//carregar o objeto pelo id
         var medico = medicoRepository.getReferenceById(dados.id());
         medico.atualizarInformacoes(dados);
    }
	
	//este metodo remove o registro no banco de dados
//	@Transactional
//	@DeleteMapping("/{id}")//parametro dinamico, o id do campo a ser removido
//	public void excluir(@PathVariable Long id) {
//		medicoRepository.deleteById(id);
//	}
	
	//metodo para realizar a exclusao logica
	@Transactional
	@DeleteMapping("/{id}")//parametro dinamico, o id do campo a ser removido
	public void excluir(@PathVariable Long id) {
		//carregar o objeto pelo id
        var medico = medicoRepository.getReferenceById(id);
        //setar para inativo
        medico.excluir();
	}
}
