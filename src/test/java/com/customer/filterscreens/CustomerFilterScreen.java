package com.customer.filterscreens;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class CustomerFilterScreen {

	public static WebDriver wd;
	
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//Method to verify whether the filter section exists
	@Test (priority=3) 
	public void filterTest() {
		
		try {
			if (wd.findElement(By.id("filters_sidebar_section")).isDisplayed()) {
				Reporter.log("Filter Section exists");
			} else {
				Reporter.log("Filter Section does not exists");
			}
			
			WebElement UserNameDD = wd.findElement(By.xpath("//div[@id='q_username_input']/select"));
			Select s = new Select(UserNameDD);
			s.selectByVisibleText("Starts with");
			Thread.sleep(2000);
			wd.findElement(By.xpath("//div[@id='q_username_input']/select/following::input[1]")).sendKeys("frnd");
			wd.findElement(By.name("commit")).click();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//Method to verify the filter results
	@Test (priority=4) 
	public void filterResultTest() throws InterruptedException {
		
		Thread.sleep(2000);
		
		try {
			
			WebElement resTbl = wd.findElement(By.id("index_table_users"));
			if (resTbl.isEnabled()) {
				Reporter.log("Results table exists");
			} else {
				Reporter.log("Results table does not exists");
			}
			
			Thread.sleep(3000);
			
			WebElement filResTbl = wd.findElement(By.id("index_table_users"));
			List<WebElement> tblrows = filResTbl.findElements(By.tagName("tr"));
	
			int count = 0;
			
			for (int i=1; i<tblrows.size(); i++) {
				WebElement tblcols = wd.findElement(By.xpath("//table[@id='index_table_users']/tbody/tr["+i+"]/td[3]"));
				String tblUserName = tblcols.getText();
				if (tblUserName.contains("Frnd")) {
					 count = count + 1;
				}
			}
			
			Reporter.log("Count: "+count);
			
			if (count==tblrows.size()-1) {
				Reporter.log("Search Results Success");
			} else {
				Reporter.log("Search Results not diplayed appropriately");
			}
			
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
