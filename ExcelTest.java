package com.AT.testscripts;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.windows.WindowsDriver;

public class ExcelTest {

	private static WindowsDriver<RemoteWebElement> ExcelSession = null;
	// Using RemoteWebElement as
	// https://github.com/appium/java-client/issues/774#issuecomment-348814473
	
	//Setup winappdriver, appium and node as pre-requisites
	@SuppressWarnings("unused")
	private static AppiumDriverLocalService service = null;

	@BeforeClass
	public static void setup() throws MalformedURLException {

		String Appium_Node_Path = "C:\\Program Files\\nodejs\\node.exe";
		String Appium_JS_Path = "C:\\Users\\robin.gupta\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js";
		AppiumDriverLocalService appiumService;

		appiumService = AppiumDriverLocalService.buildService(new AppiumServiceBuilder().usingAnyFreePort()
				.usingDriverExecutable(new File(Appium_Node_Path)).withAppiumJS(new File(Appium_JS_Path)));
		appiumService.start();

		String appiumServiceUrl = appiumService.getUrl().toString();
		System.out.println("Appium URL " + appiumServiceUrl);

		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability("app", "C:\\Program Files\\Microsoft Office\\root\\Office16\\EXCEL.EXE");
		cap.setCapability("appArguments", "/e");
		cap.setCapability("platformName", "Windows");
		cap.setCapability("deviceName", "WindowsPC");
		ExcelSession = new WindowsDriver<RemoteWebElement>(new URL(appiumServiceUrl), cap);
		ExcelSession.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

	}

	@AfterClass
	public static void TearDown() throws InterruptedException, IOException {

		try {
			if (ExcelSession != null) {
		}
			ExcelSession.quit();
		}
		
		catch (Exception e) {
			System.out.println("Exception in closing excel session as : "+ e.getMessage());
			
		}
	}

	@Test
	public void JournalEntry() throws InterruptedException {

		ExcelSession.findElementByName("File Tab").click();
		Thread.sleep(4000);
		ExcelSession.findElementByName("JournalEntry.xlsx").click();
		Thread.sleep(5000);
		ExcelSession.findElementByName("C2").sendKeys("Hello And Welcome to Selenium Conference in Chicago");
		Thread.sleep(4000);

		
		try {
			ProcessBuilder processBuilder = new ProcessBuilder();
	        processBuilder.command("cmd.exe", "/c", "taskkill /f /im excel.exe");

	        try {

	            Process process1 = processBuilder.start();

	            BufferedReader reader =
	                    new BufferedReader(new InputStreamReader(process1.getInputStream()));

	            String line;
	            while ((line = reader.readLine()) != null) {
	                System.out.println(line);
	            }

	            int exitCode = process1.waitFor();
	            System.out.println("\nExited with error code : " + exitCode);

	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }


			
		}
		catch (Exception e) {
			
		}
		
		Thread.sleep(2000);
	}

}
