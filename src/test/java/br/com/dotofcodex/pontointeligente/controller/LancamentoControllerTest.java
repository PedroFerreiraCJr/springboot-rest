package br.com.dotofcodex.pontointeligente.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.dotofcodex.pontointeligente.dto.LancamentoDTO;
import br.com.dotofcodex.pontointeligente.entity.Funcionario;
import br.com.dotofcodex.pontointeligente.entity.Lancamento;
import br.com.dotofcodex.pontointeligente.service.FuncionarioService;
import br.com.dotofcodex.pontointeligente.service.LancamentoService;
import br.com.dotofcodex.pontointeligente.types.TipoLancamento;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class LancamentoControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private LancamentoService service;

	@MockBean
	private FuncionarioService funcionarioService;

	private static final String URL_BASE = "/api/lancamentos/";
	private static final Long ID_FUNCIONARIO = 1l;
	private static final Long ID_LANCAMENTO = 1l;
	private static final String TIPO = TipoLancamento.INICIO_TRABALHO.name();
	private static final Date DATA = new Date();
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Test
	public void testCadastrarLancamento() throws Exception {
		Lancamento lancamento = obterDadosLancamento();

		BDDMockito.given(funcionarioService.buscarPorId(Mockito.anyLong())).willReturn(Optional.of(new Funcionario()));
		BDDMockito.given(service.persistir(Mockito.any(Lancamento.class))).willReturn(lancamento);

		mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
				.content(obterJsonRequisicaoPost())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(ID_LANCAMENTO))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.tipo").value(TIPO))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.data").value(sdf.format(DATA)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.funcionarioId").value(ID_FUNCIONARIO))
				.andExpect(MockMvcResultMatchers.jsonPath("$.errors").isEmpty());
	}

	@Test
	public void testCadastrarFuncionarioIdInvalido() throws Exception {
		BDDMockito.given(funcionarioService.buscarPorId(Mockito.anyLong())).willReturn(Optional.empty());

		mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
				.content(obterJsonRequisicaoPost())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.jsonPath("$.errors").value("Funcionario não encontrado. ID inexistente."))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());
	}

	@Test
	public void testRemoverLancamento() throws Exception {
		BDDMockito.given(service.buscarPorId(Mockito.anyLong())).willReturn(Optional.of(new Lancamento()));

		mvc.perform(MockMvcRequestBuilders.delete(URL_BASE + ID_LANCAMENTO)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	private String obterJsonRequisicaoPost() throws JsonProcessingException {
		LancamentoDTO dto = new LancamentoDTO();
		dto.setId(null);
		dto.setData(sdf.format(DATA));
		dto.setTipo(TIPO);
		dto.setFuncionarioId(ID_FUNCIONARIO);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(dto);
	}

	private Lancamento obterDadosLancamento() {
		Lancamento lancamento = new Lancamento();
		lancamento.setId(ID_LANCAMENTO);
		lancamento.setData(DATA);
		lancamento.setTipo(TipoLancamento.valueOf(TIPO));
		lancamento.setFuncionario(new Funcionario());
		lancamento.getFuncionario().setId(ID_FUNCIONARIO);
		return lancamento;
	}

}
