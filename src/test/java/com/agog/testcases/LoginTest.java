package com.agog.testcases;

import java.io.IOException;


import org.openqa.selenium.By;


import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.agog.base.TestBase;

public class LoginTest extends TestBase{
	
	@Test
	public void loginTest() throws InterruptedException, IOException {
		
		
		Thread.sleep(2000);
		log.debug("On login page");
		
		SendKeys("email_CSS" ,"email");
		SendKeys("password_CSS","password");
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,350)", "");
		Thread.sleep(3000);
		ClickElement("loginbutton_xpath");
		Thread.sleep(2000);
		
		
		Assert.assertTrue(isElementPreset(By.xpath(OR.getProperty("LoginUserName_xpath"))));
		log.debug("Login is successfull!!");
		
	}
	

	
	

}
