package com.agog.testcases;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.agog.base.TestBase;

public class HomePageTest extends TestBase {
	
	@Test
	public void searchTest() throws InterruptedException {
		
        SendKeys("Searchbox_xpath", "Searchtext");
		
	    Thread.sleep(5000);
		
		List<WebElement> li=driver.findElements(By.xpath(OR.getProperty("MatchingList_xpath")));
		System.out.println(li.size());
		
		for(int i=0;i<li.size();i++) {
			
			String exp=li.get(i).getText();
			System.out.println(exp);
			if(li.get(i).getText().contains("Vehicles")) {
				li.get(i).click();
				break;
			}
				
		}
		
		
	}
}
