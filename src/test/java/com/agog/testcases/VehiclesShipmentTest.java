package com.agog.testcases;

import org.testng.annotations.Test;
import com.agog.base.TestBase;


public class VehiclesShipmentTest extends TestBase{
	
	
	@Test
	public void selectDate() throws InterruptedException {

		ClickElement("vehicles_xpath");
		
		ClickElement("SelectDatefield_xpath");
		
		Thread.sleep(3000);
		
		//String date="01/08/2021";
		
		//selectDate(driver, ele, date);
		
		
		ClickElement("Date_xpath");

	}

}
