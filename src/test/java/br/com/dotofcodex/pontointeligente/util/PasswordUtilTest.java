package br.com.dotofcodex.pontointeligente.util;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtilTest {

	@Test
	public void testSenhaNula() {
		Assert.assertNull(PasswordUtil.gerarBCrypt(null));
	}

	@Test
	public void testGerarHashSenha() {
		String hash = PasswordUtil.gerarBCrypt("12345");

		Assert.assertTrue(new BCryptPasswordEncoder().matches("12345", hash));
	}
}
