package org.seleniumdemo.tests;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ShadowDOMExample {

    WebDriver driver;

    @BeforeTest
    public void setUp() {
        System.out.println("Opening chrome browser");
        System.setProperty("webdriver.chrome.driver","C:\\SelfStudy\\SeleniumDemo\\src\\main\\resources\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        //options.addArguments("disable-infobars");
        options.addArguments("--disable-extensions");
        driver = new ChromeDriver(options);
        //driver = new ChromeDriver();
    }

    @Test
    public void testGetText_Click_FromShadowDOMElements() {
        System.out.println("Open Chrome downloads");
        driver.get("chrome://downloads/");


        JavascriptExecutor jse = (JavascriptExecutor) driver;

        WebElement eleDownloads = (WebElement) jse.executeScript("return document.querySelector('downloads-manager').shadowRoot.querySelector('downloads-toolbar#toolbar').shadowRoot.querySelector('cr-toolbar#toolbar').shadowRoot.querySelector('div#leftContent div#leftSpacer h1')");
        String header = eleDownloads.getText();

        System.out.println("downloads page header text= "+header);


        WebElement search_box1 = (WebElement) jse.executeScript("return document.querySelector('downloads-manager').shadowRoot.querySelector('downloads-toolbar#toolbar').shadowRoot.querySelector('cr-toolbar#toolbar').shadowRoot.querySelector('cr-toolbar-search-field#search').shadowRoot.querySelector('div#searchTerm input#searchInput')");
        System.out.println("\nsearch tagName= "+search_box1.getTagName());
        //search_box1.clear();

        System.out.println("\nsetting value to searchTerm ");
        String js1 = "arguments[0].setAttribute('value','Satish')";
        ((JavascriptExecutor) driver).executeScript(js1, search_box1);

        WebElement search_box2 = (WebElement) jse.executeScript("return document.querySelector('downloads-manager').shadowRoot.querySelector('downloads-toolbar#toolbar').shadowRoot.querySelector('cr-toolbar#toolbar').shadowRoot.querySelector('cr-toolbar-search-field#search').shadowRoot.querySelector('div#searchTerm input#searchInput')");
        String js2 =search_box2.getAttribute("value");
        System.out.println("\nRetrieving value from searchTerm ");
        System.out.println(js2);

        System.out.println("\nBefore Click on More actions");

        WebElement moreActions = (WebElement) jse.executeScript("return document.querySelector('downloads-manager').shadowRoot.querySelector('downloads-toolbar#toolbar').shadowRoot.querySelector('cr-icon-button#moreActions.dropdown-trigger')");
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", moreActions);

        System.out.println("\nAfter Click on More actions");

    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
