package br.com.sandubas.spring.security;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;

@SuppressWarnings("deprecation")
public class SpringEnconder {

	@Test
	public void testSpringEncoder() {
		PasswordEncoder encoder = new Md5PasswordEncoder();
		String hashedPass = encoder.encodePassword("12345678", null);
		//20fdf1a3b4edbd94a385b34564a5d24a
		//20FDF1A3B4EDBD94A385B34564A5D24A
		assertEquals("25d55ad283aa400af464c76d713c07ad", hashedPass);
	}

}
