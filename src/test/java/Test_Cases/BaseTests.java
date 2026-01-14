package Test_Cases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import junit.framework.Assert;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


import java.time.Duration;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTests {
    private WebDriver driver;
    public static ExtentTest test;
    public static ExtentReports extent = new ExtentReports();

    @BeforeAll
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        ExtentSparkReporter spark = new ExtentSparkReporter("reports/Spark.html");
        extent.attachReporter((spark));


    }

    @AfterAll
    public void closure(){
        driver.close();
        driver.quit();
        extent.flush();
    }

    @Test
    @DisplayName("Text Limit")
    void Case1(){
        test = extent.createTest("Verify Text Input Length","This test validates if the text is under expected limit.");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.get("https://www.mobizo.in/");

        driver.findElement(By.xpath("//html/body/div[1]/header/div/a[2]/button")).click();
        driver.findElement(By.xpath("//html/body/div[1]/section[1]/div[2]/div/button")).click();
        driver.findElement(By.xpath("//html/body/div[1]/div/div[2]/div[2]/div/div/div[2]/p/a")).click();

        //The text which will be sent
        final String text = "Example of text which is of more than 15 letters";

        //Expected Length of the text field
        final int expectedLength = 15;


        WebElement element = driver.findElement(By.name("firstName"));
        element.sendKeys(text);

        WebElement ele = driver.findElement(By.xpath("//html/body/div[1]/div/div[2]/div[2]/div/form/div[1]/div[1]/input"));
        String actual = ele.getAttribute("value");
        //System.out.println(actual);
        int actualLength = actual.length();

        if(expectedLength == actualLength){
            test.log(Status.PASS,"The text is within 15 letters");;
        } else {
            test.log(Status.FAIL,"The text exceeds 15 letters by: " + actualLength);
        }

        Assert.assertEquals("Text is longer!", expectedLength, actualLength);


    }
}
