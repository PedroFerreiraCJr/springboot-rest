package br.com.dotofcodex.pontointeligente.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dotofcodex.pontointeligente.entity.Funcionario;
import br.com.dotofcodex.pontointeligente.repository.FuncionarioRepository;
import br.com.dotofcodex.pontointeligente.service.FuncionarioService;

@Service
public class FuncionarioServiceImpl implements FuncionarioService {

	private static final Logger logger = LoggerFactory.getLogger(FuncionarioServiceImpl.class);

	@Autowired
	private FuncionarioRepository repository;

	@Override
	public Funcionario persistir(Funcionario funcionario) {
		logger.info("Persistindo funcionario {}", funcionario);
		return this.repository.save(funcionario);
	}

	@Override
	public Optional<Funcionario> buscarPorCpf(String cpf) {
		logger.info("Buscando funcionario por cpf {}", cpf);
		return Optional.ofNullable(this.repository.findByCpf(cpf));
	}

	@Override
	public Optional<Funcionario> buscarPorEmail(String email) {
		logger.info("Buscando funcionario por email {}", email);
		return Optional.ofNullable(this.repository.findByEmail(email));
	}

	@Override
	public Optional<Funcionario> buscarPorId(Long id) {
		logger.info("Buscando funcionario por id {}", id);
		return Optional.ofNullable(this.repository.findOne(id));
	}

}
