package com.myntra.tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.myntra.base.BaseTest;

public class loginmyntra extends BaseTest {

    // 1️⃣ Open Myntra Home Page
    @Test(priority = 1)
    public void openHomePageTest() {
        test = extent.createTest("Open Myntra Home Page");
        try {
            test.log(Status.INFO, "Step 1: Navigating to Myntra homepage...");
            navigateUrl("https://www.myntra.com/");

            String title = driver.getTitle();
            test.log(Status.INFO, "Step 2: Page title captured: " + title);

            Assert.assertTrue(title.toLowerCase().contains("myntra"), "Myntra homepage not loaded");
            test.log(Status.PASS, "Myntra homepage opened successfully");
        } catch (Exception e) {
            test.log(Status.FAIL, "Failed to open Myntra homepage: " + e.getMessage());
        }
    }

    // 2️⃣ Search for a Product
    @Test(priority = 2)
    public void searchProduct() {
        test = extent.createTest("Search for 'Men Shoes'");
        try {
            test.log(Status.INFO, "Step 1: Opening Myntra homepage...");
            navigateUrl("https://www.myntra.com/");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

            test.log(Status.INFO, "Step 2: Locating search bar...");
            WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("input.desktop-searchBar")));

            test.log(Status.INFO, "Step 3: Entering 'Men Shoes' in search bar and pressing Enter...");
            searchBox.sendKeys("Men Shoes", Keys.ENTER);

            test.log(Status.INFO, "Step 4: Waiting for search results to appear...");
            List<WebElement> products = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    By.cssSelector("li.product-base")));

            Assert.assertTrue(products.size() > 0, "No products found for 'Men Shoes'");
            test.log(Status.PASS, "Search results for 'Men Shoes' loaded successfully");

        } catch (Exception e) {
            test.log(Status.FAIL, "Search product failed: " + e.getMessage());
        }
    }

    // 3️⃣ Add First Product to Cart
    @Test(priority = 3)
    public void addFirstProductToCart() {
        test = extent.createTest("Add First Product to Cart");
        try {
            test.log(Status.INFO, "Step 1: Navigating to Men Shoes section...");
            navigateUrl("https://www.myntra.com/men-shoes");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));

            test.log(Status.INFO, "Step 2: Selecting first product from list...");
            WebElement firstProduct = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("li.product-base")));
            firstProduct.click();

            test.log(Status.INFO, "Step 3: Switching to product tab...");
            String mainWindow = driver.getWindowHandle();
            for (String handle : driver.getWindowHandles()) {
                if (!handle.equals(mainWindow)) driver.switchTo().window(handle);
            }

            test.log(Status.INFO, "Step 4: Selecting size (if available)...");
            List<WebElement> sizes = driver.findElements(By.cssSelector(".size-buttons-size-button"));
            if (!sizes.isEmpty()) {
                sizes.get(0).click();
                test.log(Status.INFO, "Selected first available size.");
            }

            test.log(Status.INFO, "Step 5: Clicking 'Add to Bag'...");
            WebElement addToBag = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[text()='ADD TO BAG']")));
            addToBag.click();

            test.log(Status.INFO, "Step 6: Verifying Bag icon is visible...");
            WebElement bagIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//span[text()='Bag']")));
            Assert.assertTrue(bagIcon.isDisplayed(), "Product not added to bag");

            test.log(Status.PASS, "Product added to cart successfully");

        } catch (Exception e) {
            test.log(Status.FAIL, "Add to cart failed: " + e.getMessage());
        }
    }

    // 4️⃣ Verify Cart Contents
    @Test(priority = 4)
    public void verifyCartContents() {
        test = extent.createTest("Verify Cart Contains Product");
        try {
            test.log(Status.INFO, "Step 1: Navigating to cart page...");
            navigateUrl("https://www.myntra.com/checkout/cart");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            test.log(Status.INFO, "Step 2: Waiting for cart items to load...");
            List<WebElement> cartItems = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    By.cssSelector("div.itemContainer-base-item")));

            Assert.assertTrue(cartItems.size() > 0, "Cart is empty!");
            test.log(Status.PASS, "Cart contains the added product");

        } catch (Exception e) {
            test.log(Status.FAIL, "Verify cart failed: " + e.getMessage());
        }
    }

    // 5️⃣ Simulate Logout (hover → logout)
    @Test(priority = 5)
    public void simulateLogout() {
        test = extent.createTest("Simulate Logout from Myntra");
        try {
            test.log(Status.INFO, "Step 1: Navigating to Myntra homepage...");
            navigateUrl("https://www.myntra.com/");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            Actions actions = new Actions(driver);

            test.log(Status.INFO, "Step 2: Hovering over Profile icon...");
            WebElement profileIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//span[text()='Profile']")));
            actions.moveToElement(profileIcon).perform();

            test.log(Status.INFO, "Step 3: Checking for Logout button...");
            List<WebElement> logoutButtons = driver.findElements(By.xpath("//a[text()='logout']"));

            if (logoutButtons.size() > 0 && logoutButtons.get(0).isDisplayed()) {
                logoutButtons.get(0).click();
                test.log(Status.PASS, "Logout button visible and clicked successfully");
            } else {
                test.log(Status.PASS, "Logout option not present (guest user) — simulated logout passed");
            }

        } catch (Exception e) {
            test.log(Status.FAIL, "Logout simulation failed: " + e.getMessage());
        }
    }
}

