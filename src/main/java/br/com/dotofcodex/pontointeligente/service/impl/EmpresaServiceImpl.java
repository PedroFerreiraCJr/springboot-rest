package br.com.dotofcodex.pontointeligente.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dotofcodex.pontointeligente.entity.Empresa;
import br.com.dotofcodex.pontointeligente.repository.EmpresaRepository;
import br.com.dotofcodex.pontointeligente.service.EmpresaService;

@Service
public class EmpresaServiceImpl implements EmpresaService {

	private static final Logger logger = LoggerFactory.getLogger(EmpresaServiceImpl.class);

	@Autowired
	private EmpresaRepository repository;

	@Override
	public Optional<Empresa> buscarPorCnpj(String cnpj) {
		logger.info("Buscando empresa para o CPNJ {}", cnpj);
		return Optional.ofNullable(this.repository.findByCnpj(cnpj));
	}

	@Override
	public Empresa persistir(Empresa empresa) {
		logger.info("Persistindo empresa {}", empresa);
		return this.repository.save(empresa);
	}

}
