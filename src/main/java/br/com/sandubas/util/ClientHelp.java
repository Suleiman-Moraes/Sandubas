package br.com.sandubas.util;

import java.lang.reflect.Type;
import java.util.Arrays;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

public class ClientHelp {
	
	public static <T> Object realizarRequisicao(String url, HttpMethod httpMethod, Type type) {
		return realizarRequisicao(url, httpMethod, type, null);
	}
	
	public static <T> Object realizarRequisicao(String url, HttpMethod httpMethod, Type type, T object) {
		HttpHeaders headers = new HttpHeaders();
		
		headers.setAccept(Arrays.asList(new MediaType[] {MediaType.APPLICATION_JSON}));
		headers.setContentType(MediaType.APPLICATION_JSON);
//		headers.set("key", "value");
		
		HttpEntity<T> entity = new HttpEntity<>(object, headers);
		RestTemplate restTemplate = new RestTemplate();
//		MappingJackson2HttpMessageConverter jsonHttpMessageConverter = new MappingJackson2HttpMessageConverter();
//		jsonHttpMessageConverter.getObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
//		restTemplate.getMessageConverters().add(jsonHttpMessageConverter);
		ResponseEntity<String> response = restTemplate.exchange(url, httpMethod, entity, String.class);
//		HttpStatus httpStatus = response.getStatusCode();
		String result = response.getBody();
		
		try {
			Gson gson = new Gson();
			Object objectReturn = gson.fromJson(result, type);
			return objectReturn;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
