package br.com.sandubas.util;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public abstract class TemplateTestUtil {
	
	public static final String PORTA = "8080";
	protected static WebDriver driver;

	@BeforeClass
	public static void inicializaFabrica() {
		try {
			System.setProperty("webdriver.chrome.driver",
					new File("src\\test\\resources\\br\\com\\sandubas\\driverselenium\\chromedriver.exe")
							.getAbsolutePath());

			driver = new ChromeDriver();

			driver.get("http://localhost:" + PORTA + "/sandubas/login.jsp");
//			driver.manage().window().maximize();

			WebElement element = driver.findElement(By.id("login-username"));
			element.sendKeys("root");
			driver.findElement(By.id("login-password")).sendKeys("123456");

			driver.findElement(By.id("btn-login")).click();
			
			Thread.sleep(3000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public abstract void test00Login();
}
