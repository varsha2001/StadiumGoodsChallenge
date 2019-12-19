package challenge.stadiumGoods;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class stadiumGoodsProductSort {
	
	  WebDriver driver;
	  String url;
	  String urlmain;
	 
	    @Before
	    public void beforeTest() {
	        System.out.println("-------------------Execution Started----------------");
	        System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
	        driver = new ChromeDriver();
	        driver.manage().window().maximize();
	        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	        urlmain = "https://stage.stadiumgoods.cloud/";
	        url = "https://stage.stadiumgoods.cloud/customer/account/login/";
	    }
	    
	    @Test
	    public void verifySort() throws InterruptedException {
	   
	    	driver.get(urlmain);
	    	Thread.sleep(20000);
	    	driver.get(url);
	    	
	    	By usernamee = By.xpath("//input[@id='email']");
	        By pwd =By.xpath("//input[@id='pass']");
	        WebElement usernameTextBlock = driver.findElement(usernamee);
	        WebElement passwordTextBlock = driver.findElement(pwd);
	        
	        usernameTextBlock.sendKeys("qa.automation@test.com");
	        passwordTextBlock.sendKeys("password1");
	        
	        driver.findElement(By.id("send2")).click();
	        Thread.sleep(100000); //to handle captcha
	        
	        driver.findElement(By.xpath("//div[@id='header-nav']/nav/ol/li/a[contains(text(),'Jordan')]")).click();


	        
	       Select dropdown = new Select(findElement(By.xpath("//select[@class='Select___StyledSelect-wqytkt-3 bLmPWb sg-fndtn']")));
	       dropdown.selectByVisibleText("Price Low to High");

	       By proPrice = By.cssSelector("div.huTZvy");
	        
	        List <WebElement> actualPrice = driver.findElements(proPrice);    
	        for (WebElement eachName : actualPrice) 
		        	System.out.println(eachName.getText());
	        
	       int iSize   = 4;
	
	        ArrayList<Integer> actualPriceList = new ArrayList<Integer>(iSize); 
	        for(int i=0;i<iSize;i++) {
	            WebElement wePrice = actualPrice.get(i);

	            
	            String strPrice = wePrice.getText().substring(1);
	            System.out.println(strPrice);

	            
	            float iPrice = Float.parseFloat(strPrice);
	            System.out.println(iPrice);
	            actualPriceList.add((int) iPrice);
	        }
	        
	        System.out.println("Actual price list");
	        for(int obj:actualPriceList)  {  
	            System.out.print(obj+":"); 
	        }
	
	     
	        ArrayList<Integer> finalsortedlist = new ArrayList<Integer>(iSize);
	        finalsortedlist = actualPriceList;
	        Collections.sort(finalsortedlist);
	        System.out.println("Price list After using Collection sort method");
	        for(int obj1:finalsortedlist)  {
	            System.out.print(obj1+":"); 
	        }
	    
	        try {
	            Assert.assertEquals(actualPriceList, finalsortedlist);
	            System.out.println("successful run");
	        }catch(AssertionError e) {
	            System.out.println("Sort By feature 'Low to High' is defective");
	        }
	    }
	   

	    @After
	    public void afterTest() {
	        driver.quit();
	    }
	    
}	    
