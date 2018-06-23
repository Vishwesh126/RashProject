package com.customer.newscreens;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

//Class for Testing the new customer screens

public class NewCustomerScreens {

	public static WebDriver wd;
	public String username;
	public String email;
	
	//Method to open the browser with desired URL
	@BeforeSuite
	public void openBrowser() {		
		
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Vichu\\Documents\\My Selenium Practice\\RashAssignment\\Lib\\chromedriver.exe");
		ChromeOptions opt = new ChromeOptions();
		opt.addArguments("--start-maximized");		
		
		
		wd = new ChromeDriver(opt);
		wd.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wd.get("http://ec2-54-174-213-136.compute-1.amazonaws.com:8080/admin");
		Reporter.log("Browser Opened");
		
	}
	
	
	//Method to goto the new customer registration screen
	@Test (priority=1)
	public void gotoNewCustomerScreen() {
		
		try {
			wd.findElement(By.linkText("Users")).click();
			wd.findElement(By.linkText("New User")).click();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//Method to register a new user 
	@Test (priority=2, dependsOnMethods="gotoNewCustomerScreen")
	public void newUserReg() {
		
		Random rand = new Random();
		
		username = "Frnd"+rand.nextInt(1000);
		email = "frnd"+rand.nextInt(1000)+"@gmail.com";
		
		try {
			wd.findElement(By.id("user_username")).sendKeys(username);
			Reporter.log("Inputted UserName: "+username);
			wd.findElement(By.id("user_password")).sendKeys("frnd123");
			Reporter.log("Inputted Password: frnd123");
			wd.findElement(By.name("user[email]")).sendKeys(email);
			Reporter.log("Inputted eMail: "+email);
			wd.findElement(By.name("commit")).click();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Method to verify the user is registered or not
	@Test (priority=3)
	public void verifyReg() {
		
		try {
			String regStat = wd.findElement(By.xpath("//div[@class='flashes']/div")).getText();
			Assert.assertEquals(regStat, "User was successfully created.");
			Reporter.log("Registration Status Message: "+regStat);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//Method to verify the added customer details
	@Test (priority=4)
	public void verifyCustomer() {
		
		try {
			String regUserName = wd.findElement(By.xpath("//tr[@class='row row-username']/td")).getText();
			String regUserMail = wd.findElement(By.xpath("//tr[@class='row row-email']/td")).getText();
			Assert.assertEquals(regUserName, username);
			Assert.assertEquals(regUserMail, email);
			
			Reporter.log("Registration UserName: "+regUserName);
			Reporter.log("Registration eMail: "+regUserMail);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//Method to quit the driver
	@AfterSuite
	public void closeBrowser() {		
		wd.quit();		
	}
	
	
	
	
	
	
	
	
	
}
