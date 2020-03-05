package br.com.dotofcodex.pontointeligente.controller;

import java.security.NoSuchAlgorithmException;

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

import br.com.dotofcodex.pontointeligente.dto.CadastroPJDTO;
import br.com.dotofcodex.pontointeligente.entity.Empresa;
import br.com.dotofcodex.pontointeligente.entity.Funcionario;
import br.com.dotofcodex.pontointeligente.response.Response;
import br.com.dotofcodex.pontointeligente.service.EmpresaService;
import br.com.dotofcodex.pontointeligente.service.FuncionarioService;
import br.com.dotofcodex.pontointeligente.types.Perfil;
import br.com.dotofcodex.pontointeligente.util.PasswordUtil;

@RestController
@RequestMapping("/api/cadastro-pj")
@CrossOrigin(origins = "*")
public class CadastroPJController {

	private static final Logger logger = LoggerFactory.getLogger(CadastroPJController.class);

	@Autowired
	private FuncionarioService funcionarioService;

	@Autowired
	private EmpresaService empresaService;

	public CadastroPJController() {
		super();
	}

	@PostMapping
	public ResponseEntity<Response<CadastroPJDTO>> cadastrar(@Valid @RequestBody CadastroPJDTO dto,
			BindingResult result) throws NoSuchAlgorithmException {

		logger.info("Cadastrando PJ {}", dto.toString());

		Response<CadastroPJDTO> response = new Response<>();

		validarDadosExistentes(dto, result);
		Empresa empresa = this.converterDTOParaEmpresa(dto);
		Funcionario funcionario = this.converterDTOParaFuncionario(dto, result);

		if (result.hasErrors()) {
			logger.error("Erro validando dados do cadastro PJ {}", result.getAllErrors());

			result.getAllErrors().forEach(error -> {
				response.getErrors().add(error.getDefaultMessage());
			});
			return ResponseEntity.badRequest().body(response);
		}

		this.empresaService.persistir(empresa);
		funcionario.setEmpresa(empresa);
		this.funcionarioService.persistir(funcionario);

		response.setData(this.converterCadastroPJDTO(funcionario));
		return ResponseEntity.ok(response);
	}

	private void validarDadosExistentes(CadastroPJDTO dto, BindingResult result) {
		this.empresaService.buscarPorCnpj(dto.getCnpj()).ifPresent(empresa -> {
			result.addError(new ObjectError("empresa", "Empresa já existente"));
		});

		this.funcionarioService.buscarPorCpf(dto.getCpf()).ifPresent(funcionario -> {
			result.addError(new ObjectError("funcionario", "CPF já existente"));
		});

		this.funcionarioService.buscarPorEmail(dto.getEmail()).ifPresent(funcionario -> {
			result.addError(new ObjectError("funcionario", "E-mail já existente"));
		});
	}

	private Empresa converterDTOParaEmpresa(CadastroPJDTO dto) {
		Empresa empresa = new Empresa();
		empresa.setRazaoSocial(dto.getRazaoSocial());
		empresa.setCnpj(dto.getCnpj());
		return empresa;
	}

	private Funcionario converterDTOParaFuncionario(CadastroPJDTO dto, BindingResult result)
			throws NoSuchAlgorithmException {
		Funcionario funcionario = new Funcionario();
		funcionario.setNome(dto.getNome());
		funcionario.setEmail(dto.getEmail());
		funcionario.setCpf(dto.getCpf());
		funcionario.setPerfil(Perfil.ROLE_ADMIN);
		funcionario.setSenha(PasswordUtil.gerarBCrypt(dto.getSenha()));
		return funcionario;
	}

	private CadastroPJDTO converterCadastroPJDTO(Funcionario funcionario) {
		CadastroPJDTO dto = new CadastroPJDTO();
		dto.setId(funcionario.getId());
		dto.setNome(funcionario.getNome());
		dto.setEmail(funcionario.getEmail());
		dto.setCpf(funcionario.getCpf());
		dto.setRazaoSocial(funcionario.getEmpresa().getRazaoSocial());
		dto.setCnpj(funcionario.getEmpresa().getCnpj());
		return dto;
	}

}
