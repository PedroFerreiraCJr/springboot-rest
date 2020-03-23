package br.com.dotofcodex.pontointeligente.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dotofcodex.pontointeligente.dto.EmpresaDTO;
import br.com.dotofcodex.pontointeligente.entity.Empresa;
import br.com.dotofcodex.pontointeligente.response.Response;
import br.com.dotofcodex.pontointeligente.service.EmpresaService;

@RestController
@RequestMapping("/api/empresas")
@CrossOrigin(origins = "*")
public class EmpresaController {

	private static final Logger logger = LoggerFactory.getLogger(EmpresaController.class);

	@Autowired
	private EmpresaService service;

	public EmpresaController() {
		super();
	}

	@GetMapping("/cnpj/{cnpj}")
	public ResponseEntity<Response<EmpresaDTO>> buscarPorCnpj(@PathVariable("cnpj") String cnpj) {
		logger.info("Buscando empresa por CNPJ: {}", cnpj);
		Response<EmpresaDTO> response = new Response<EmpresaDTO>();
		Optional<Empresa> empresa = this.service.buscarPorCnpj(cnpj);

		if (!empresa.isPresent()) {
			logger.info("Empresa não encontrada para o CNPJ: {}", cnpj);
			response.getErrors().add("Empresa não encontrada para o CNPJ: " + cnpj);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(converterEmpresaDTO(empresa.get()));
		return ResponseEntity.ok(response);
	}

	private EmpresaDTO converterEmpresaDTO(Empresa empresa) {
		EmpresaDTO dto = new EmpresaDTO();
		dto.setId(empresa.getId());
		dto.setCnpj(empresa.getCnpj());
		dto.setRazaoSocial(empresa.getRazaoSocial());
		return dto;
	}

}
