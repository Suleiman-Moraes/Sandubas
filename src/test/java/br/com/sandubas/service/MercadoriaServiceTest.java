package br.com.sandubas.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import br.com.sandubas.util.TemplateTestUtil;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MercadoriaServiceTest extends TemplateTestUtil{

	private static final String TESTE_INSERCAO = "R$ 1.0 2.0 % 3.0 Teste valorMedida 4.0 Teste valorMedida Teste Selinium Inserção \"marca\"";
	private static final String TESTE_ALTERACAO = "R$ 5.0 6.0 % 7.0 Teste valorMedida 8.0 Teste valorMedida Teste Selinium Alteração \"marca\"";
	private static final String URL_ADMINISTRAR = PADRAO_URL + "/mantermercadoria/administrar.xhtml";
	private static final String URL_MODAL = PADRAO_URL + "/mantermercadoria/modal/cadastroMercadoria.xhtml";
	private static String id;

	@Test
	@Override
	public void test00Login() {
		assertEquals(driver.getCurrentUrl(), PAGINA_PRINCIPAL_URL);
	}
	
	@Test
	public void test01ContarRegistrosCadastrados() {
		driver.get(URL_ADMINISTRAR);
		
		WebElement element = driver.findElement(By.id("formulario:listarRegistros:totalRegistrosID"));
		String label = element.getText().replaceAll("Total de Registros", "").trim();
		assertEquals(Integer.parseInt(label), 2);
	}

	@Test
	public void test02Salvar() {
		try {
			driver.get(URL_MODAL +"?pfdlgcid=47a4b85d-916d-4bd8-baff-dfe637ebb55f");
			Thread.sleep(2000);
			driver.findElement(By.id("formulario:precoPago_input")).sendKeys("1");
			driver.findElement(By.id("formulario:porcentagemVenda_input")).sendKeys("2");
			driver.findElement(By.id("formulario:quantidade_input")).sendKeys("3");
			driver.findElement(By.id("formulario:valorAgrupamento_input")).sendKeys("4");
			driver.findElement(By.id("formulario:marca")).sendKeys("Teste Selinium Inserção \"marca\"");
			driver.findElement(By.id("formulario:valorMedida")).sendKeys("Teste valorMedida");
			new Select(driver.findElement(By.id("formulario:tipoProduto_input"))).selectByIndex(1);
			new Select(driver.findElement(By.id("formulario:classificacaoMercadoria_input"))).selectByIndex(1);
			
			driver.findElement(By.id("formulario:ButaoSalvar")).click();
			Thread.sleep(500);
			driver.get(URL_ADMINISTRAR);
			Thread.sleep(2000);
			String dados = driver.findElement(By.id("formulario:listarRegistros_data")).getText().split("\n")[0];
			Pattern p = Pattern.compile("\\d+");
			Matcher m = p.matcher(dados);
			id = m.find() ? m.group() : "0";
			assertTrue(dados.contains(TESTE_INSERCAO));
		} catch (Exception e) {
			assertTrue(Boolean.FALSE);
		}
	}
	
	@Test
	public void test03Alterar() {
		try {
			driver.get(URL_ADMINISTRAR);
			Thread.sleep(2000);
			driver.findElement(By.id("formulario:listarRegistros:0:j_idt68")).click();
			Thread.sleep(2000);
			String url = String.format
					("%s?objetoId=%s&&pfdlgcid=4b7c9dae-635d-4cc3-8a4a-6baa52f219bf", 
							URL_MODAL, id);
			driver.get(url);
			Thread.sleep(2000);
			
			driver.findElement(By.id("formulario:precoPago_input")).clear();
			driver.findElement(By.id("formulario:porcentagemVenda_input")).clear();
			driver.findElement(By.id("formulario:quantidade_input")).clear();
			driver.findElement(By.id("formulario:valorAgrupamento_input")).clear();
			driver.findElement(By.id("formulario:marca")).clear();
			driver.findElement(By.id("formulario:valorMedida")).clear();
			
			driver.findElement(By.id("formulario:precoPago_input")).sendKeys("5");
			driver.findElement(By.id("formulario:porcentagemVenda_input")).sendKeys("6");
			driver.findElement(By.id("formulario:quantidade_input")).sendKeys("7");
			driver.findElement(By.id("formulario:valorAgrupamento_input")).sendKeys("8");
			driver.findElement(By.id("formulario:marca")).sendKeys("Teste Selinium Alteração \"marca\"");
			driver.findElement(By.id("formulario:valorMedida")).sendKeys("Teste valorMedida");
			new Select(driver.findElement(By.id("formulario:tipoProduto_input"))).selectByIndex(2);
			new Select(driver.findElement(By.id("formulario:classificacaoMercadoria_input"))).selectByIndex(2);
			driver.findElement(By.id("formulario:ButaoSalvar")).click();
			Thread.sleep(1000);
			driver.get(URL_ADMINISTRAR);
			Thread.sleep(2000);
			String dados = driver.findElement(By.id("formulario:listarRegistros_data")).getText().split("\n")[0];
			assertTrue(dados.contains(TESTE_ALTERACAO));
		} catch (Exception e) {
			assertTrue(Boolean.FALSE);
		}
	}
	
	@Test
	public void test04Deletar() {
		try {
			driver.get(URL_ADMINISTRAR);
			Thread.sleep(2000);
			driver.findElement(By.id("formulario:listarRegistros:0:j_idt69")).click();
			Thread.sleep(1000);
			driver.findElement(By.id("j_idt71")).click();
			Thread.sleep(500);
			driver.get(URL_ADMINISTRAR); 
			Thread.sleep(2000);
			String dados = driver.findElement(By.id("formulario:listarRegistros_data")).getText().split("\n")[0];
			assertFalse(dados.contains(TESTE_ALTERACAO));
			driver.close();
		} catch (Exception e) {
			assertTrue(Boolean.FALSE);
		}
	}
}