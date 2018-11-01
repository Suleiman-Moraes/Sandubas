package br.com.sandubas.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.jsoup.helper.StringUtil;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.sandubas.dao.TipoProdutoDAO;
import br.com.sandubas.exception.NegocioException;
import br.com.sandubas.model.TipoProduto;
import sun.swing.plaf.synth.Paint9Painter;

public class TipoProdutoServiceTest{

	private TipoProduto objeto = new TipoProduto("Teste Inserção", "Teste");
	private static EntityManagerFactory factory;
	private EntityManager manager;
	
	private static WebDriver driver;
	
	private TipoProdutoService service = new TipoProdutoService();
	
	@BeforeClass
	public static void inicializaFabrica() {
		System.setProperty("webdriver.chrome.driver", new File("src\\test\\resources\\br\\com\\sandubas\\driverselenium\\chromedriver.exe").getAbsolutePath());

		driver = new ChromeDriver();

		driver.get("http://localhost:8080/sandubas/login.jsp");
		driver.manage().window().maximize();
		
//		driver.findElement(By.className("close")).click();// Fechar modal
		
		WebElement element = driver.findElement(By.id("login-username"));
		element.sendKeys("root");
		driver.findElement(By.id("login-password")).sendKeys("123456");
		
		driver.findElement(By.id("btn-login")).click();
		
		try {
			Thread.sleep(5000);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Before
	public void inicialiaDependencias() {
//		System.out.println("inicialiaDependencias");
//		this.manager = factory.createEntityManager();
//		service.setPersistencia(new TipoProdutoDAO());
//		service.getPersistencia().use("ProjetoPU");
//		service.getPersistencia().setEntityManagerFactory(factory);
//		service.getPersistencia().setEntityManager(manager);
	}
	
	@Test
	public void testContarRegistrosCadastrados() {
		driver.get("http://localhost:8080/sandubas/pages/mantertipoproduto/administrar.xhtml");
		String conteudo = driver.getPageSource();
		System.out.println(conteudo);
		System.out.println("\n\n\n\n\n\n\n\n\n");
		final Pattern PATTERN = Pattern.compile("<tr data-ri=\"\\d+\" class=\"[^\"]+\" role=\"row\">", Pattern.MULTILINE);
		Matcher matcher = PATTERN.matcher(conteudo);
		System.out.println(matcher.matches());
		System.out.println(matcher.groupCount());
		for (int i = 0; i <= matcher.groupCount(); i++) {
			System.out.println(matcher.group(i));
		}
	}

	@Test
	public void testSalvar() {
		try {
			String testeAlterar = "Teste alterar";
			service.salvar(objeto);
			assertTrue(objeto.getId() > 0);
			objeto.setNome(testeAlterar);
			service.salvar(objeto);
			assertTrue(objeto.getId() > 0);
			assertEquals(testeAlterar, objeto.getNome());
		} catch (Exception e) {
			assertTrue(Boolean.FALSE);
		}
	}

	@Test
	public void testExcluir() {
		try {
			service.excluir(objeto);
			Long id = service.getPersistencia().findById(TipoProduto.class, objeto.getId()).getId();
			Long esperado = 0l;
			assertEquals(null, id);
		} catch (NegocioException e) {
			assertTrue(Boolean.FALSE);
		}
	}

}
