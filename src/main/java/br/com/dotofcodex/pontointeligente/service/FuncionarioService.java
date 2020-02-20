package br.com.dotofcodex.pontointeligente.service;

import java.util.Optional;

import br.com.dotofcodex.pontointeligente.entity.Funcionario;

public interface FuncionarioService {

	Funcionario persistir(Funcionario funcionario);
	
	Optional<Funcionario> buscarPorCpf(String cpf);
	
	Optional<Funcionario> buscarPorEmail(String email);
	
	Optional<Funcionario> buscarPorId(Long id);

}
