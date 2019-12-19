package challenge.stadiumGoods;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class stadiumGoodsLogin {

	  WebDriver driver;
	  String url;
	  String urlmain;
	  WebDriverWait wait;
	 
	    @BeforeTest
	    public void beforeTest() {
	        System.out.println("-------------------Execution Started----------------");
	        System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
	        driver = new ChromeDriver();
	        driver.manage().window().maximize();
	        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	        urlmain = "https://stage.stadiumgoods.cloud/";
	        url = "https://stage.stadiumgoods.cloud/customer/account/login/";
	    }
	    
	    @DataProvider
	    public Object[][] dp() {
	        String FilePath = "datasets\\creds.xlsx";
	        String SheetName = "Sheet1";
	 
	        Object [][] excelData = null;
	 
	        try {
	            excelData = getExcelData(FilePath, SheetName);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	 
	        return excelData;
	    }
	    
	    @Test(dataProvider = "dp")
	    public void login(String username, String password) throws Exception {
	 
	        System.out.println("Searching for credential = " + username + password );
	        driver.get(urlmain);
	        Thread.sleep(20000);
	        driver.get(url);
	        
	        By usernamee = By.xpath("//input[@id='email']");
	        By pwd =By.xpath("//input[@id='pass']");
	        WebElement usernameTextBlock = driver.findElement(usernamee);
	        WebElement passwordTextBlock = driver.findElement(pwd);
	        usernameTextBlock.sendKeys(username);
	        passwordTextBlock.sendKeys(password);
	        
	        driver.findElement(By.id("send2")).click();
	        Thread.sleep(100000); //to handle captcha

	      
            driver.findElement(By.xpath("//div[@class='dashboard']")).isDisplayed();
            System.out.println("Login successfull using the credential");
            driver.findElement(By.xpath("//a[contains(text(),'Log Out')]")).click();
     
	    }
	    
	    
	    public static String[][] getExcelData (String fileName, String sheetName) throws IOException {
	        String[][] arrayExcelData = null;
	        Workbook wb = null;
	        try {
	            File file = new File(fileName);     
	            FileInputStream fs = new FileInputStream(file);
	            if
	            (fileName.substring(fileName.indexOf(".")).equals(".xlsx"))
	            {
	                wb = new XSSFWorkbook(fs);
	            }
	            else if
	            (fileName.substring(fileName.indexOf(".")).equals(".xls"))
	            {
	                wb = new HSSFWorkbook(fs);
	            }
	 
	            if (wb==null)
	            {
	                //Error Sheet name not found
	                Exception exp = new Exception("WORKBOOK CREATION ERROR - May be File **NOT** found " + sheetName );
	                throw exp;
	            }       
	 
	            Sheet sh = wb.getSheet(sheetName);
	            if (sh==null)
	            {
	                Exception exp = new Exception("Sheet Name **NOT** found " + sheetName );
	                throw exp;
	            }
	 
	            int totalNoOfRows = sh.getPhysicalNumberOfRows();
	            int totalNoOfCols = 
	                    sh.getRow(0).getPhysicalNumberOfCells();
	            
	            
	 
	            System.out.println("totalNoOfRows="+totalNoOfRows+"," + " totalNoOfCols="+totalNoOfCols);
	            arrayExcelData =  new String[totalNoOfRows-1][totalNoOfCols];
	            for (int i= 1 ; i <= totalNoOfRows-1; i++) {
	                for (int j=0; j <= totalNoOfCols-1; j++) {
	                    sh.getRow(i).getCell(j).setCellType(1);
	                    arrayExcelData[i-1][j] = sh.getRow(i).getCell(j).getStringCellValue().toString();
	                }
	            }
	        } catch (Exception e) {
	            System.out.println("EXCEPTION error in getExcelData()");
	            System.out.println(e.getMessage());
	            if (arrayExcelData==null)
	            {
	                IOException exp = new IOException(e.getMessage());
	                throw exp;
	            }
	        }
	        return arrayExcelData;
	    }
	 
	    @AfterTest
	    public void afterTest() {
	        driver.quit();
	    }
}
