package br.com.tenoriogames.core.util.selenium;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Login extends TEST {
	public static WebDriver efetuarLogin( String login, String senha) {
		WebDriver driver = null;
		WebDriverWait wait = null;
		
		driver = TEST.retornarDriverSeleniumChrome();
		driver.get("http://localhost:8080/TenorioGames/Pages/Usuario/autenticacao.xhtml");
		
		wait = new WebDriverWait(driver, 5000);
		wait.ignoring(NoSuchElementException.class);
        wait.ignoring(StaleElementReferenceException.class);

        driver.manage().timeouts().implicitlyWait(5000, TimeUnit.SECONDS);
		// Capturando os elementos de email e senha pelo atributo name
		WebElement txtUsuario = driver.findElement(By.id("formlogin:txtcpf"));
		WebElement txtSenha = driver.findElement(By.id("formlogin:txtsenha"));

		// Setando valores nos atributos de email e senha
		// Setando valores nos atributos de email e senha
		txtUsuario.sendKeys(login);
		txtSenha.sendKeys(senha);

		// Efetuando um click no botão
		txtSenha.sendKeys(Keys.ENTER);
		
		return driver;
	}
	

}
