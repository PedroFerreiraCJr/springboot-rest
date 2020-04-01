package br.com.dotofcodex.pontointeligente.controller;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dotofcodex.pontointeligente.dto.FuncionarioDTO;
import br.com.dotofcodex.pontointeligente.entity.Funcionario;
import br.com.dotofcodex.pontointeligente.response.Response;
import br.com.dotofcodex.pontointeligente.service.FuncionarioService;
import br.com.dotofcodex.pontointeligente.util.PasswordUtil;

@RestController
@RequestMapping("/api/funcionarios")
@CrossOrigin(origins = "*")
public class FuncionarioController {

	private static final Logger logger = LoggerFactory.getLogger(FuncionarioController.class);

	@Autowired
	private FuncionarioService service;

	public FuncionarioController() {
		super();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<FuncionarioDTO>> atualizar(@PathVariable("id") Long id,
			@Valid @RequestBody FuncionarioDTO dto, BindingResult result) throws NoSuchAlgorithmException {
		logger.info("Atualizando funcionario {}", dto.toString());

		Response<FuncionarioDTO> response = new Response<>();

		Optional<Funcionario> funcionario = this.service.buscarPorId(id);
		if (!funcionario.isPresent()) {
			result.getAllErrors().add(new ObjectError("funcionario", "Funcionario não encontrado"));
		}

		this.atualizarDadosFuncionario(funcionario.get(), dto, result);

		if (result.hasErrors()) {
			logger.info("Erro validando funcionário {}", result.getAllErrors());
			result.getAllErrors().forEach((error) -> {
				response.getErrors().add(error.getDefaultMessage());
			});
			return ResponseEntity.badRequest().body(response);
		}

		this.service.persistir(funcionario.get());
		response.setData(this.converterFuncionarioDTO(funcionario.get()));

		return ResponseEntity.ok(response);
	}

	private void atualizarDadosFuncionario(Funcionario funcionario, FuncionarioDTO dto, BindingResult result)
			throws NoSuchAlgorithmException {
		funcionario.setNome(dto.getNome());

		if (!funcionario.getEmail().equals(dto.getEmail())) {
			this.service.buscarPorEmail(dto.getEmail()).ifPresent((Funcionario func) -> {
				result.addError(new ObjectError("email", "E-mail já existente."));
			});
			funcionario.setEmail(dto.getEmail());
		}

		funcionario.setQtdHorasAlmoco(null);
		dto.getQuantidadeHorasAlmoco().ifPresent((quantidade) -> {
			funcionario.setQtdHorasAlmoco(Double.parseDouble(quantidade));
		});

		funcionario.setQtdHorasTrabalhoDia(null);
		dto.getQuantidadeHorasTrabalhoDia().ifPresent((quantidade) -> {
			funcionario.setQtdHorasTrabalhoDia(Double.parseDouble(quantidade));
		});

		funcionario.setValorHora(null);
		dto.getValorHora().ifPresent((valor) -> {
			funcionario.setValorHora(new BigDecimal(valor));
		});

		dto.getSenha().ifPresent((senha) -> {
			funcionario.setSenha(PasswordUtil.gerarBCrypt(senha));
		});
	}

	private FuncionarioDTO converterFuncionarioDTO(Funcionario funcionario) {
		FuncionarioDTO dto = new FuncionarioDTO();
		dto.setId(funcionario.getId().toString());
		dto.setNome(funcionario.getNome());
		dto.setEmail(funcionario.getEmail());

		if (funcionario.getValorHora() != null) {
			dto.setValorHora(Optional.ofNullable(funcionario.getValorHora().toString()));
		}

		if (funcionario.getQtdHorasAlmoco() != null) {
			dto.setQuantidadeHorasAlmoco(Optional.ofNullable(funcionario.getQtdHorasAlmoco().toString()));
		}

		if (funcionario.getQtdHorasTrabalhoDia() != null) {
			dto.setQuantidadeHorasTrabalhoDia(Optional.ofNullable(funcionario.getQtdHorasTrabalhoDia().toString()));
		}

		return dto;
	}

}
