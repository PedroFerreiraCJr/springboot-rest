package br.com.dotofcodex.pontointeligente.service;

import java.util.ArrayList;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.dotofcodex.pontointeligente.entity.Lancamento;
import br.com.dotofcodex.pontointeligente.repository.LancamentoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LancamentoServiceTest {

	@MockBean
	private LancamentoRepository repository;

	@Autowired
	private LancamentoService service;

	@Before
	public void setup() {
		BDDMockito.given(this.repository.save(Mockito.any(Lancamento.class))).willReturn(new Lancamento());
		BDDMockito.given(this.repository.findOne(Mockito.anyLong())).willReturn(new Lancamento());
		BDDMockito.given(this.repository.findByFuncionarioId(Mockito.anyLong(), Mockito.any(PageRequest.class)))
				.willReturn(new PageImpl<Lancamento>(new ArrayList<Lancamento>()));
	}

	@Test
	public void testPersistirLancamento() {
		Lancamento lancamento = this.service.persistir(new Lancamento());

		Assert.assertNotNull(lancamento);
	}

	@Test
	public void testBuscarLancamentoPorFuncionarioId() {
		Page<Lancamento> lancamento = this.service.buscarPorFuncionarioId(1l, new PageRequest(0, 10));

		Assert.assertNotNull(lancamento);
	}

	@Test
	public void testBuscarFuncionarioPorCpf() {
		Optional<Lancamento> lancamento = this.service.buscarPorId(1l);

		Assert.assertTrue(lancamento.isPresent());
	}

}
