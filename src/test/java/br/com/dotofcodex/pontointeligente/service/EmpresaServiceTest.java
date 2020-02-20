package br.com.dotofcodex.pontointeligente.service;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.dotofcodex.pontointeligente.entity.Empresa;
import br.com.dotofcodex.pontointeligente.repository.EmpresaRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class EmpresaServiceTest {

	@MockBean
	private EmpresaRepository repository;

	@Autowired
	private EmpresaService service;

	private static final String CNPJ = "1234567891000";

	@Before
	public void setup() {
		BDDMockito.given(this.repository.findByCnpj(Mockito.anyString())).willReturn(new Empresa());
		BDDMockito.given(this.repository.save(Mockito.any(Empresa.class))).willReturn(new Empresa());
	}

	@Test
	public void testBuscarEmpresaPorCnpj() {
		Optional<Empresa> empresa = this.service.buscarPorCnpj(CNPJ);

		Assert.assertTrue(empresa.isPresent());
	}

	@Test
	public void testPersistirEmpresa() {
		Empresa empresa = this.service.persistir(new Empresa());

		Assert.assertNotNull(empresa);
	}

}
