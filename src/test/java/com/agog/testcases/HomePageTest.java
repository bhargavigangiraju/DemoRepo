package com.agog.testcases;

import org.testng.annotations.Test;

import com.agog.actions.HomePage;

public class HomePageTest extends HomePage {
	
	@Test(description="Verify the search functionality by passing Vehicles as text")
	public void searchTest() throws InterruptedException {
		
       searchVehicles();
		
	}
	@Test(description="Verify the search functionality by passing Vehicles as text")
	public void verifyLogo() throws InterruptedException {
		
       agogLogo();
       
	}
	
}
