package br.com.dotofcodex.pontointeligente.repository;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.dotofcodex.pontointeligente.entity.Empresa;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class EmpresaRepositoryTest {

	@Autowired
	private EmpresaRepository repository;

	@Before
	public void setup() {
		Empresa empresa = new Empresa();
		empresa.setRazaoSocial("Empresa Exemplo");
		empresa.setCnpj("123456789");
		this.repository.save(empresa);
	}

	@After
	public void tearDown() {
		this.repository.deleteAll();
	}

	@Test
	public void testBuscarPorCNPJ() {
		Empresa empresa = this.repository.findByCnpj("123456789");
		
		Assert.assertEquals("123456789", empresa.getCnpj());
	}
}
