package br.com.tenoriogames.core.util.selenium;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.By;

public class Conducao extends TEST {
	
	@Test
	public void realizarConducao(){
		driver=Login.efetuarLogin("42167200803","alemanha");
		driver.manage().timeouts().implicitlyWait(1000, TimeUnit.SECONDS);
		driver.navigate().refresh();
		driver.findElement(By.id("frmVendaCad:tblProdutos:2:btAdicionar")).click();
		driver.findElement(By.id("frmFinalizar:btFinalizar")).click();
		driver.findElement(By.id("wvDlgFinVenda:frmPdl:btSalvar")).click();
		
		
		
	}
}

