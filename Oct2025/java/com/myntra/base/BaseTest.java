package com.myntra.base;
 
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.myntra.utilities.ExtentManager;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

    protected static WebDriver driver;
    protected static ExtentReports extent;
    protected static ExtentTest test;

    // 🔹 Initialize Extent Report before the entire suite
    @BeforeSuite(alwaysRun = true)
    public void setupReport() {
        extent = ExtentManager.getInstance1();
        WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        System.out.println("✅ Browser launched successfully (BeforeSuite)");
    }

    // 🔹 Flush the report and quit browser only once at the end
    @AfterSuite(alwaysRun = true)
    public void tearDownSuite() {
        if (driver != null) {
            driver.quit();
            System.out.println("🛑 Browser closed successfully (AfterSuite)");
        }
        if (extent != null) {
            extent.flush();
            System.out.println("📄 Extent report flushed successfully");
        }
    }

    // 🔹 This method can be used by all tests to navigate
    public void navigateUrl(String url) {
        driver.get(url);
        System.out.println("🌐 Navigated to: " + url);
    }
}
