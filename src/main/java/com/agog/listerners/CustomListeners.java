package com.agog.listerners;

import java.io.IOException;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.agog.base.TestBase;
import com.agog.utilities.TestConfig;
import com.agog.utilities.TestUtils;
import com.relevantcodes.extentreports.LogStatus;

public class CustomListeners extends TestBase implements ITestListener, ISuiteListener {
	
	public static String messageBody;

	public void onTestStart(ITestResult result) {

		test = report.startTest(result.getName().toUpperCase());

	}

	public void onTestFailure(ITestResult result) {

		System.setProperty("org.uncommons.reportng.escape-output", "false");

		try {
			TestUtils.captureScreenshot();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// ExtentReports
		test.log(LogStatus.FAIL,
				result.getName().toUpperCase() + " failed with exception" + " :" + result.getThrowable());
		test.log(LogStatus.FAIL, test.addScreenCapture(TestUtils.SSName));

		Reporter.log("Click link to view  the Screenshot");
		Reporter.log("<a target=\"_blank\" href=" + TestUtils.SSName + ">ScreenShot</a>");
		Reporter.log("<br>");
		Reporter.log("<a target=\"_blank\" href=" + TestUtils.SSName + "><img src=" + TestUtils.SSName
				+ " height=200 width=200></img></a>");

		report.endTest(test);
		report.flush();

	}

	public void onTestSuccess(ITestResult result) {
		// ExtentReports
		test.log(LogStatus.PASS, result.getName().toUpperCase() + " pass");
		report.endTest(test);
		report.flush();

	}

	
}
