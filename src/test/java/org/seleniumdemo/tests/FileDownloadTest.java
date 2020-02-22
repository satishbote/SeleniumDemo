package org.seleniumdemo.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.seleniumdemo.utility.FileDownload;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class FileDownloadTest {

    public WebDriver driver;
    File folder;

    @BeforeMethod
    public void setUp()
    {
        //folder=new File(UUID.randomUUID().toString());
        folder=new File(System.getProperty("user.dir")+"\\src\\main\\resources\\reports");
        folder.mkdir();
        System.setProperty("webdriver.chrome.driver","C:\\SelfStudy\\SeleniumDemo\\src\\main\\resources\\chromedriver.exe");

        ChromeOptions options= new ChromeOptions();
        Map<String, Object> prefs= new HashMap<String,Object>();
        prefs.put("profile.default_content_setting.popups",0);
        prefs.put("download.default_directory",folder.getAbsolutePath());

        options.setExperimentalOption("prefs",prefs);
        //DesiredCapabilities cap=DesiredCapabilities.chrome();
        //cap.setCapability(ChromeOptions.CAPABILITY,options);

       driver=new ChromeDriver(options);
    }


    @Test
    public void downloadFileTest() throws InterruptedException {

        driver.get("http://the-internet.herokuapp.com/download");
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        //Thread.sleep(5000);
        TimeUnit.SECONDS.sleep(5);

        driver.findElement(By.xpath("//a[text()='some-file.txt']")).click();

        //Thread.sleep(5000);
        TimeUnit.SECONDS.sleep(5);
        Assert.assertTrue(FileDownload.isFileDownloaded(folder.getAbsolutePath(), "some-file.txt"), "Failed to download Expected document");
        System.out.println("File Downloaded");

        File getLatestFile = FileDownload.getLatestFilefromDir(folder.getAbsolutePath());
        String fileName = getLatestFile.getName();
        System.out.println("latest file= "+fileName);
        Assert.assertTrue(fileName.startsWith("some-file"), "Downloaded file name is not matching with expected file name");
        //System.out.println("latest file= "+fileName);
    }


    @AfterMethod
    public void tearDown() {
        driver.quit();
    }



}
