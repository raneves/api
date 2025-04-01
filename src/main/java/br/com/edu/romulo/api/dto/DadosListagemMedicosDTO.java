package br.com.edu.romulo.api.dto;

import br.com.edu.romulo.api.domain.Medico;

public record DadosListagemMedicosDTO(
		Long id,
		String nome, 
		String email, 
		String crm, 
		EspecialidadeDTO especialidade) {

    public DadosListagemMedicosDTO(Medico medico) {
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getEspecialidade());
    }
}