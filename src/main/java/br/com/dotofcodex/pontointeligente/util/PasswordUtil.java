package br.com.dotofcodex.pontointeligente.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {

	private static final Logger logger = LoggerFactory.getLogger(PasswordUtil.class);

	private PasswordUtil() {
		super();
	}

	public static String gerarBCrypt(String password) {
		logger.info("Gerando hash com BCyrpt");

		if (password == null) {
			return null;
		}

		BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
		return bCrypt.encode(password);
	}
}
