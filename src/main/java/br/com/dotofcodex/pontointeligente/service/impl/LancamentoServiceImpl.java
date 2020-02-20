package br.com.dotofcodex.pontointeligente.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.dotofcodex.pontointeligente.entity.Lancamento;
import br.com.dotofcodex.pontointeligente.repository.LancamentoRepository;
import br.com.dotofcodex.pontointeligente.service.LancamentoService;

@Service
public class LancamentoServiceImpl implements LancamentoService {

	private static final Logger logger = LoggerFactory.getLogger(LancamentoServiceImpl.class);

	@Autowired
	private LancamentoRepository repository;

	@Override
	public Page<Lancamento> buscarPorFuncionarioId(Long funcionarioId, PageRequest pageRequest) {
		logger.info("Buscando lancamento por funcionario id paginado {}", funcionarioId);
		return this.repository.findByFuncionarioId(funcionarioId, pageRequest);
	}

	@Override
	public Optional<Lancamento> buscarPorId(Long id) {
		logger.info("Buscando lancamento por id {}", id);
		return Optional.ofNullable(this.repository.findOne(id));
	}

	@Override
	public Lancamento persistir(Lancamento lancamento) {
		logger.info("Buscando lancamento por funcionario id paginado {}", lancamento);
		return this.repository.save(lancamento);
	}

	@Override
	public void remover(Long id) {
		logger.info("Deletando lancamento por id {}", id);
		this.repository.delete(id);
	}

}
