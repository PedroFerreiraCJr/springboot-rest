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

import br.com.dotofcodex.pontointeligente.entity.Funcionario;
import br.com.dotofcodex.pontointeligente.repository.FuncionarioRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FuncionarioServiceTest {

	@MockBean
	private FuncionarioRepository repository;

	@Autowired
	private FuncionarioService service;

	private static final String CPF = "03809876543";

	@Before
	public void setup() {
		BDDMockito.given(this.repository.save(Mockito.any(Funcionario.class))).willReturn(new Funcionario());
		BDDMockito.given(this.repository.findOne(Mockito.anyLong())).willReturn(new Funcionario());
		BDDMockito.given(this.repository.findByCpf(Mockito.anyString())).willReturn(new Funcionario());
		BDDMockito.given(this.repository.findByEmail(Mockito.anyString())).willReturn(new Funcionario());
	}

	@Test
	public void testPersistirFuncionario() {
		Funcionario funcionario = this.service.persistir(new Funcionario());

		Assert.assertNotNull(funcionario);
	}
	
	@Test
	public void testBuscarFuncionarioPorId() {
		Optional<Funcionario> empresa = this.service.buscarPorId(1l);

		Assert.assertTrue(empresa.isPresent());
	}

	@Test
	public void testBuscarFuncionarioPorCpf() {
		Optional<Funcionario> empresa = this.service.buscarPorCpf(CPF);

		Assert.assertTrue(empresa.isPresent());
	}

	@Test
	public void testBuscarFuncionarioPorEmail() {
		Optional<Funcionario> empresa = this.service.buscarPorEmail("pedroferreiracjr@gmail.com");

		Assert.assertTrue(empresa.isPresent());
	}

}
