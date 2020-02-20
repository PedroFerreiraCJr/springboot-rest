package br.com.dotofcodex.pontointeligente.service;

import java.util.Optional;

import br.com.dotofcodex.pontointeligente.entity.Empresa;

public interface EmpresaService {

	Optional<Empresa> buscarPorCnpj(String cnpj);

	Empresa persistir(Empresa empresa);
}
