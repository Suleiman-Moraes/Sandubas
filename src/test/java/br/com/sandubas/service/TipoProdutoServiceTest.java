package br.com.sandubas.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import br.com.sandubas.util.TemplateTestUtil;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TipoProdutoServiceTest extends TemplateTestUtil{

	public static final String TESTE_INSERCAO = "Teste Selinium Inserção \"nome\" Teste Selinium Inserção \"descricao\"";
	public static final String TESTE_ALTERACAO = "Teste Selinium Alteração \"nome\" Teste Selinium Alteração \"descricao\"";
	private static String id;

	@Test
	@Override
	public void test00Login() {
		assertEquals(driver.getCurrentUrl(), "http://localhost:" + PORTA + "/sandubas/pages/principal.xhtml");
	}
	
	@Test
	public void test01ContarRegistrosCadastrados() {
		driver.get("http://localhost:" + PORTA + "/sandubas/pages/mantertipoproduto/administrar.xhtml");
		
		WebElement element = driver.findElement(By.id("formulario:listarRegistros:totalRegistrosID"));
		String label = element.getText().replaceAll("Total de Registros", "").trim();
		assertEquals(Integer.parseInt(label), 2);
	}

	@Test
	public void test02Salvar() {
		try {
			driver.get("http://localhost:" + PORTA + "/sandubas/pages/mantertipoproduto/modal/cadastroTipoProduto.xhtml?pfdlgcid=47a4b85d-916d-4bd8-baff-dfe637ebb55f");
			Thread.sleep(2000);
			driver.findElement(By.id("formulario:nome")).sendKeys("Teste Selinium Inserção \"nome\"");
			driver.findElement(By.id("formulario:descricao")).sendKeys("Teste Selinium Inserção \"descricao\"");
			driver.findElement(By.id("formulario:ButaoSalvar")).click();
			driver.get("http://localhost:" + PORTA + "/sandubas/pages/mantertipoproduto/administrar.xhtml");
			Thread.sleep(2000);
			String dados = driver.findElement(By.id("formulario:listarRegistros_data")).getText().split("\n")[0];
			id = dados.replaceAll(TESTE_INSERCAO, "").trim();
			assertTrue(dados.contains(TESTE_INSERCAO));
		} catch (Exception e) {
			assertTrue(Boolean.FALSE);
		}
	}
	
	@Test
	public void test03Alterar() {
		try {
			driver.get("http://localhost:" + PORTA + "/sandubas/pages/mantertipoproduto/administrar.xhtml");
			Thread.sleep(2000);
			driver.findElement(By.id("formulario:listarRegistros:0:j_idt56")).click();
			Thread.sleep(2000);
			String url = String.format
					("http://localhost:%s/sandubas/pages/mantertipoproduto/modal/cadastroTipoProduto.xhtml?objetoId=%s&pfdlgcid=5d3c2b24-f5c4-43cf-bb61-36f2d6b0b655", 
							PORTA, id);
			driver.get(url);
			Thread.sleep(2000);
			driver.findElement(By.id("formulario:nome")).clear();
			driver.findElement(By.id("formulario:nome")).sendKeys("Teste Selinium Alteração \"nome\"");
			driver.findElement(By.id("formulario:descricao")).clear();
			driver.findElement(By.id("formulario:descricao")).sendKeys("Teste Selinium Alteração \"descricao\"");
			driver.findElement(By.id("formulario:ButaoSalvar")).click();
			Thread.sleep(1000);
			driver.get("http://localhost:" + PORTA + "/sandubas/pages/mantertipoproduto/administrar.xhtml");
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
			driver.get("http://localhost:" + PORTA + "/sandubas/pages/mantertipoproduto/administrar.xhtml");
			Thread.sleep(2000);
			driver.findElement(By.id("formulario:listarRegistros:0:j_idt57")).click();
			Thread.sleep(1000);
			driver.findElement(By.id("j_idt59")).click();
			driver.get("http://localhost:" + PORTA + "/sandubas/pages/mantertipoproduto/administrar.xhtml"); 
			Thread.sleep(2000);
			String dados = driver.findElement(By.id("formulario:listarRegistros_data")).getText().split("\n")[0];
			assertFalse(dados.contains(TESTE_ALTERACAO));
			driver.close();
		} catch (Exception e) {
			assertTrue(Boolean.FALSE);
		}
	}
}
