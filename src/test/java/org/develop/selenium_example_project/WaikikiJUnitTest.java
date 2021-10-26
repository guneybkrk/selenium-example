package org.develop.selenium_example_project;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Random;

class WaikikiJUnitTest extends JUnitTestBase {


    @BeforeEach
    public void initPageObjects() {
        driver.get(baseUrl);
    }

    @AfterEach
    public void close() {
        driver.close();
    }

    @Test
    void testHomePageHasAHeader() throws InterruptedException {

        String product = "erkek pantalon";

        WebElement searchInput = driver.findElement(By.id("search_input"));

        Assertions.assertTrue(searchInput.isDisplayed());
        searchInput.clear();
        searchInput.sendKeys(product);

        Thread.sleep(200);
        Assertions.assertEquals(product, searchInput.getAttribute("value"));

        WebElement searchButton = driver.findElement(By.className("searchButton"));

        Assertions.assertTrue(searchButton.isEnabled());
        searchButton.click();

        WebElement lazyLoadButton = driver.findElement(By.className("lazy-load-button"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", lazyLoadButton);
        Thread.sleep(500);
        lazyLoadButton.click();

        List<WebElement> elements = driver.findElements(By.className("product-item-wrapper"));
        int random = elements.size() - new Random().nextInt(10) - 1;

        WebElement randomElement = elements.get(random);
        randomElement.click();
        Thread.sleep(1000);

        WebElement price = driver.findElement(By.className("price"));
        String priceText = price.getAttribute("innerHTML");
        Assertions.assertNotNull(priceText);

        WebElement optionSizeDiv = driver.findElement(By.id("option-size"));
        List<WebElement> options = optionSizeDiv.findElements(By.tagName("a"));
        Assertions.assertFalse(options.isEmpty());
        WebElement option = options.get(0);
        option.click();

        WebElement addToCartButton = driver.findElement(By.id("pd_add_to_cart"));
        addToCartButton.click();


        WebElement chart = driver.findElement(By.id("spanCart"));
        chart.click();
        Thread.sleep(2000);

        List<WebElement> chartElements = driver.findElements(By.className("shoppingcart-item"));
        Assertions.assertEquals(1, chartElements.size());

        WebElement chartElement = chartElements.get(0);
        WebElement chartElementPrice = chartElement.findElement(By.className("rd-cart-item-price"));
        Assertions.assertEquals(priceText, chartElementPrice.getText());

        WebElement increaseButton = chartElement.findElement(By.className("oq-up"));
        increaseButton.click();
        Thread.sleep(2000);
        WebElement quantity = chartElement.findElement(By.className("item-quantity-input"));
        Assertions.assertEquals("2", quantity.getAttribute("value"));

        WebElement removeButton = chartElement.findElement(By.className("fa-trash-o"));
        removeButton.click();
        Thread.sleep(2000);

        WebElement removeConfirm = driver.findElement(By.className("sc-delete"));
        removeConfirm.click();

        Thread.sleep(2000);
        WebElement chartEmptyTitle = driver.findElement(By.className("cart-empty-title"));
        Assertions.assertEquals("Sepetinizde ürün bulunmamaktadır.", chartEmptyTitle.getText());
    }
}
