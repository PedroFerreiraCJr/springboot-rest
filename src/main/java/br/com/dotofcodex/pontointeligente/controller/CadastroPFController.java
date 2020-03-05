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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dotofcodex.pontointeligente.dto.CadastroPFDTO;
import br.com.dotofcodex.pontointeligente.entity.Empresa;
import br.com.dotofcodex.pontointeligente.entity.Funcionario;
import br.com.dotofcodex.pontointeligente.response.Response;
import br.com.dotofcodex.pontointeligente.service.EmpresaService;
import br.com.dotofcodex.pontointeligente.service.FuncionarioService;
import br.com.dotofcodex.pontointeligente.types.Perfil;
import br.com.dotofcodex.pontointeligente.util.PasswordUtil;

@RestController
@RequestMapping("/api/cadastro-pf")
@CrossOrigin(origins = "*")
public class CadastroPFController {

	private static final Logger logger = LoggerFactory.getLogger(CadastroPFController.class);

	@Autowired
	private FuncionarioService funcionarioService;

	@Autowired
	private EmpresaService empresaService;

	public CadastroPFController() {
		super();
	}

	@PostMapping
	public ResponseEntity<Response<CadastroPFDTO>> cadastrar(@Valid @RequestBody CadastroPFDTO dto,
			BindingResult result) throws NoSuchAlgorithmException {

		logger.info("Cadastrando PF: {}", dto.toString());
		Response<CadastroPFDTO> response = new Response<CadastroPFDTO>();

		validarDadosExistentes(dto, result);
		Funcionario funcionario = converterDTOParaFuncionario(dto, result);

		if (result.hasErrors()) {
			logger.error("Erro validando cadastro de PF {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		Optional<Empresa> empresa = this.empresaService.buscarPorCnpj(dto.getCnpj());
		funcionario.setEmpresa(empresa.get());
		this.funcionarioService.persistir(funcionario);

		response.setData(converterCadastroPFDTO(funcionario));
		return ResponseEntity.ok(response);
	}

	private void validarDadosExistentes(CadastroPFDTO dto, BindingResult result) {
		Optional<Empresa> empresa = this.empresaService.buscarPorCnpj(dto.getCnpj());
		if (empresa.isPresent()) {
			result.addError(new ObjectError("empresa", "Empresa não cadastrada"));
		}

		this.funcionarioService.buscarPorCpf(dto.getCpf()).ifPresent(func -> {
			result.addError(new ObjectError("funcionario", "CPF já existente"));
		});

		this.funcionarioService.buscarPorEmail(dto.getEmail()).ifPresent(func -> {
			result.addError(new ObjectError("funcionario", "E-mail já existente"));
		});
	}

	private Funcionario converterDTOParaFuncionario(CadastroPFDTO dto, BindingResult result)
			throws NoSuchAlgorithmException {
		Funcionario funcionario = new Funcionario();
		funcionario.setNome(dto.getNome());
		funcionario.setEmail(dto.getEmail());
		funcionario.setCpf(dto.getCpf());
		funcionario.setPerfil(Perfil.ROLE_USUARIO);
		funcionario.setSenha(PasswordUtil.gerarBCrypt(dto.getSenha()));
		dto.getValorHora().ifPresent(valorHora -> {
			funcionario.setValorHora(new BigDecimal(valorHora));
		});
		dto.getQuantidadeHorasTrabalhoDia().ifPresent(quantidade -> {
			funcionario.setQtdHorasTrabalhoDia(Double.valueOf(quantidade));
		});
		dto.getQuantidadeHorasAlmoco().ifPresent(quantidade -> {
			funcionario.setQtdHorasAlmoco(Double.valueOf(quantidade));
		});
		return funcionario;
	}

	private CadastroPFDTO converterCadastroPFDTO(Funcionario funcionario) {
		CadastroPFDTO dto = new CadastroPFDTO();
		dto.setId(funcionario.getId());
		dto.setNome(funcionario.getNome());
		dto.setEmail(funcionario.getEmail());
		dto.setCpf(funcionario.getCpf());
		dto.setCnpj(funcionario.getEmpresa().getCnpj());
		dto.setValorHora(Optional.ofNullable(String.valueOf(funcionario.getValorHora())));
		dto.setQuantidadeHorasTrabalhoDia(Optional.ofNullable(String.valueOf(funcionario.getQtdHorasTrabalhoDia())));
		dto.setQuantidadeHorasAlmoco(Optional.ofNullable(String.valueOf(funcionario.getQtdHorasAlmoco())));
		return dto;
	}

}