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
import br.com.dotofcodex.pontointeligente.entity.Funcionario;
import br.com.dotofcodex.pontointeligente.types.Perfil;
import br.com.dotofcodex.pontointeligente.util.PasswordUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FuncionarioRepositoryTest {

	@Autowired
	private FuncionarioRepository repository;

	@Autowired
	private EmpresaRepository empresaRepository;

	private static final String EMAIL = "pedroferreiracjr@gmail.com";
	private static final String CPF = "03809876543";

	@Before
	public void setup() {
		Empresa empresa = this.empresaRepository.save(obterDadosEmpresa());
		this.repository.save(obterDadosFuncionario(empresa));
	}

	@After
	public void tearDown() {
		this.empresaRepository.deleteAll();
	}

	@Test
	public void testBuscarFuncionarioPorCpf() {
		Funcionario funcionario = this.repository.findByCpf(CPF);

		Assert.assertEquals(CPF, funcionario.getCpf());
	}

	@Test
	public void testBuscarFuncionarioPorEmail() {
		Funcionario funcionario = this.repository.findByEmail(EMAIL);

		Assert.assertEquals(EMAIL, funcionario.getEmail());
	}

	@Test
	public void testBuscarFuncionarioPorCpfEmail() {
		Funcionario funcionario = this.repository.findByCpfOrEmail(CPF, EMAIL);

		Assert.assertEquals(CPF, funcionario.getCpf());
		Assert.assertEquals(EMAIL, funcionario.getEmail());
	}

	@Test	
	public void testBuscarFuncionarioPorCpfInvalido() {
		Funcionario funcionario = this.repository.findByCpf("123456");

		Assert.assertNull(funcionario);
	}

	private Funcionario obterDadosFuncionario(Empresa empresa) {
		Funcionario funcionario = new Funcionario();
		funcionario.setNome("Pedro");
		funcionario.setPerfil(Perfil.ROLE_USUARIO);
		funcionario.setSenha(PasswordUtil.gerarBCrypt("123456"));
		funcionario.setCpf(CPF);
		funcionario.setEmail(EMAIL);
		funcionario.setEmpresa(empresa);
		return funcionario;
	}

	private Empresa obterDadosEmpresa() {
		Empresa empresa = new Empresa();
		empresa.setRazaoSocial("Empresa Exemplo");
		empresa.setCnpj("123456789");
		return empresa;
	}
}
