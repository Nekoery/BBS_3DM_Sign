package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ElemetUtils {
    public static List<WebElement> getElementByTagName(WebDriver driver,String tagName){
        try{
            return driver.findElements(By.tagName(tagName));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static List<WebElement> getElementByTagName(WebElement element,String tagName){
        try{
            return element.findElements(By.tagName(tagName));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

//    public static List<WebElement> getElementByClass(WebElement element,String tagName){
//        try{
//            return element.findElements(By.ByClassName(tagName));
//        }catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
//    }
}
