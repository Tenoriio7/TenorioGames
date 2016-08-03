package br.com.tenoriogames.core.util.selenium;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CRUD extends TEST {
	private static WebDriver driver = null;
	public static WebDriverWait wait = null;
	@Test
	public void realizarCrud(){
		
		driver=Login.efetuarLogin("719.392.286-62","alemanha");
		driver.manage().timeouts().implicitlyWait(1000, TimeUnit.SECONDS);
		driver.navigate().refresh();
		driver.get("http://localhost:8080/TenorioGames/Pages/Genero/generoPesquisa.xhtml");
		driver.findElement(By.id("formgenero:dtgenero:btnovo")).click();
		driver.findElement(By.id("frmFabCad:txtdescricao")).sendKeys("Descrição Selenium");
		driver.findElement(By.id("frmFabCad:btsalvar")).click();
		driver.get("http://localhost:8080/TenorioGames/Pages/Genero/generoPesquisa.xhtml");
		driver.findElement(By.id("formgenero:dtgenero:2:btExcluir")).click();
		driver.findElement(By.id("frmFabCad:btExcluir")).click();
		driver.get("http://localhost:8080/TenorioGames/Pages/Genero/generoPesquisa.xhtml");

		
		
		
	
		
	}


}
