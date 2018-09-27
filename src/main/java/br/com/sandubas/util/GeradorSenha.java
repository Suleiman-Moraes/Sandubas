package br.com.sandubas.util;

import java.util.Random;

public class GeradorSenha {
	public static final String alfabeto = "0123456789abcdefghijklmnopqrstuvwxyzABCDFEGHIJKLMNOPQRSTUVWXYZ";

	public static String getSenha() {
		StringBuilder senha = new StringBuilder("");
		Random generator = new Random();
		for (int i = 0; i < 8; i++) {
			senha.append(alfabeto.charAt(generator.nextInt(alfabeto.length())));
		}
		return senha.toString();
	}
}
