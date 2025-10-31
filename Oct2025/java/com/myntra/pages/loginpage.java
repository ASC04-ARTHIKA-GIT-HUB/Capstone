package com.myntra.pages;

 
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
 
public class loginpage {
 
    WebDriver driver;
 
    By searchBox = By.xpath("//input[@class='desktop-searchBar']");
    By productList = By.xpath("//ul[contains(@class,'results-base')]/li");
    By firstProduct = By.xpath("(//ul[contains(@class,'results-base')]/li)[1]");
 
    public loginpage(WebDriver driver) {
        this.driver = driver;
    }
 
    public void searchProduct(String productName) {
        driver.findElement(searchBox).sendKeys(productName, Keys.ENTER);
    }
 
    public void openFirstProduct() {
        driver.findElement(firstProduct).click();
    }
 
    public boolean isProductListDisplayed() {
        return driver.findElements(productList).size() > 0;
    }
}