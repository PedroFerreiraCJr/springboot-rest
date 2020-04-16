package br.com.dotofcodex.pontointeligente.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.dotofcodex.pontointeligente.dto.LancamentoDTO;
import br.com.dotofcodex.pontointeligente.entity.Funcionario;
import br.com.dotofcodex.pontointeligente.entity.Lancamento;
import br.com.dotofcodex.pontointeligente.response.Response;
import br.com.dotofcodex.pontointeligente.service.FuncionarioService;
import br.com.dotofcodex.pontointeligente.service.LancamentoService;
import br.com.dotofcodex.pontointeligente.types.TipoLancamento;

@RestController
@RequestMapping("/api/lancamentos")
@CrossOrigin(origins = "*")
public class LancamentoController {

	private static final Logger logger = LoggerFactory.getLogger(LancamentoController.class);
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private LancamentoService service;

	@Autowired
	private FuncionarioService funcionarioService;

	@Value("${paginacao.qtd_por_pagina}")
	private int qtdPorPagina;

	public LancamentoController() {
		super();
	}

	@GetMapping(value = "/funcionario/{funcionarioId}")
	public ResponseEntity<Response<Page<LancamentoDTO>>> listarPorFuncionarioId(@PathVariable("funcionarioId") Long id,
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir) {

		logger.info("Buscando lancamentos por Id do funcionario {}, pagina {}", id, pag);

		Response<Page<LancamentoDTO>> response = new Response<Page<LancamentoDTO>>();
		PageRequest request = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<Lancamento> lancamentos = this.service.buscarPorFuncionarioId(id, request);
		Page<LancamentoDTO> dtos = lancamentos.map(lanc -> converterLancamentoDTO(lanc));
		response.setData(dtos);

		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<LancamentoDTO>> listarPorId(@PathVariable("id") Long id) {
		logger.info("Buscando lancamento por Id {}", id);
		Response<LancamentoDTO> response = new Response<LancamentoDTO>();

		Optional<Lancamento> lancamento = service.buscarPorId(id);

		if (!lancamento.isPresent()) {
			logger.info("Lancamento não encontrado para o ID {}", id);
			response.getErrors().add("Lançamento não encontrado para o Id: " + id);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(converterLancamentoDTO(lancamento.get()));
		return ResponseEntity.ok(response);
	}

	@PostMapping
	public ResponseEntity<Response<LancamentoDTO>> adicionar(@Valid @RequestBody LancamentoDTO dto,
			BindingResult result) throws ParseException {
		logger.info("Adicionando lançamento... {}", dto.toString());
		Response<LancamentoDTO> response = new Response<LancamentoDTO>();
		validarFuncionario(dto, result);

		Lancamento lancamento = converterDTOLancamento(dto, result);

		if (result.hasErrors()) {
			logger.info("Erro validando lançamento {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		lancamento = service.persistir(lancamento);
		response.setData(converterLancamentoDTO(lancamento));
		return ResponseEntity.ok(response);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<LancamentoDTO>> atualizar(@PathVariable("id") Long id,
			@Valid @RequestBody LancamentoDTO dto, BindingResult result) throws ParseException {
		logger.info("Atualizando lançamento {}", dto.toString());
		Response<LancamentoDTO> response = new Response<LancamentoDTO>();
		validarFuncionario(dto, result);
		dto.setId(Optional.of(id));
		Lancamento lancamento = converterDTOLancamento(dto, result);

		if (result.hasErrors()) {
			logger.info("Erro validando lançamento {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		lancamento = service.persistir(lancamento);
		response.setData(converterLancamentoDTO(lancamento));
		return ResponseEntity.ok(response);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response<String>> remover(@PathVariable("id") Long id) {
		logger.info("Removendo lançamento por id {}", id);
		Response<String> response = new Response<String>();
		Optional<Lancamento> lancamento = service.buscarPorId(id);

		if (!lancamento.isPresent()) {
			logger.info("Erro ao remover lançamento por ID {}", id);
			response.getErrors().add("Erro ao remover lançamento: " + id + ". Registro não encontrado.");
			return ResponseEntity.badRequest().body(response);
		}

		service.remover(id);
		return ResponseEntity.ok(response);
	}

	private void validarFuncionario(LancamentoDTO dto, BindingResult result) {
		if (dto.getFuncionarioId() == null) {
			result.addError(new ObjectError("funcionario", "Funcionario não informado"));
			return;
		}

		logger.info("Validando funcionario id {}", dto.getFuncionarioId());
		Optional<Funcionario> funcionario = funcionarioService.buscarPorId(dto.getFuncionarioId());
		if (!funcionario.isPresent()) {
			result.addError(new ObjectError("funcionario", "Funcionario não encontrado. ID inexistente."));
		}
	}

	public Lancamento converterDTOLancamento(LancamentoDTO dto, BindingResult result) throws ParseException {
		Lancamento lancamento = new Lancamento();

		if (dto.getId().isPresent()) {
			Optional<Lancamento> value = service.buscarPorId(dto.getId().get());
			if (value.isPresent()) {
				lancamento = value.get();
			} else {
				result.addError(new ObjectError("lancamento", "Lançamento não encontrado"));
			}
		} else {
			lancamento.setFuncionario(new Funcionario());
			lancamento.getFuncionario().setId(dto.getFuncionarioId());
		}

		lancamento.setDescricao(dto.getDescricao());
		lancamento.setLocalizacao(dto.getLocalizacao());
		lancamento.setData(sdf.parse(dto.getData()));

		if (EnumUtils.isValidEnum(TipoLancamento.class, dto.getTipo())) {
			lancamento.setTipo(TipoLancamento.valueOf(dto.getTipo()));
		} else {
			result.addError(new ObjectError("tipo", "Tipo de lançamento inválido."));
		}

		return lancamento;
	}

	public LancamentoDTO converterLancamentoDTO(Lancamento lancamento) {
		LancamentoDTO dto = new LancamentoDTO();
		dto.setId(Optional.of(lancamento.getId()));
		dto.setData(sdf.format(lancamento.getData()));
		dto.setTipo(lancamento.getTipo().toString());
		dto.setDescricao(lancamento.getDescricao());
		dto.setFuncionarioId(lancamento.getFuncionario().getId());
		return dto;
	}

}
