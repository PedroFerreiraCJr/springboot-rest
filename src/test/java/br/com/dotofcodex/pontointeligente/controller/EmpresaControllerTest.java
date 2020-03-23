package br.com.dotofcodex.pontointeligente.controller;

import java.util.Optional;

import org.hamcrest.CoreMatchers;
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

import br.com.dotofcodex.pontointeligente.entity.Empresa;
import br.com.dotofcodex.pontointeligente.service.EmpresaService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class EmpresaControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private EmpresaService service;

	private static final String URL = "/api/empresas/cnpj/";
	private static final Long ID = Long.valueOf(1l);
	private static final String CNPJ = "51463645000100";
	private static final String RAZAO_SOCIAL = "Empresa Kazale";

	@Test
	public void testBuscarEmpresaCnpjInvalido() throws Exception {
		BDDMockito.given(this.service.buscarPorCnpj(Mockito.anyString())).willReturn(Optional.empty());

		mvc.perform(MockMvcRequestBuilders.get(URL.concat(CNPJ)).accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.jsonPath("$.errors").value("Empresa não encontrada para o CNPJ: " + CNPJ));
	}

	@Test
	public void testBuscarEmpresaCnpjValido() throws Exception {
		BDDMockito.given(this.service.buscarPorCnpj(Mockito.anyString())).willReturn(Optional.of(this.getDados()));
		
		mvc.perform(MockMvcRequestBuilders.get(URL.concat(CNPJ)).accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(ID))
			.andExpect(MockMvcResultMatchers.jsonPath("$.data.razaoSocial", CoreMatchers.equalTo(RAZAO_SOCIAL)))
			.andExpect(MockMvcResultMatchers.jsonPath("$.data.cnpj", CoreMatchers.equalTo(CNPJ)))
			.andExpect(MockMvcResultMatchers.jsonPath("$.errors").isEmpty());
		
	}

	private Empresa getDados() {
		Empresa empresa = new Empresa();
		empresa.setId(ID);
		empresa.setCnpj(CNPJ);
		empresa.setRazaoSocial(RAZAO_SOCIAL);
		return empresa;
	}
}
