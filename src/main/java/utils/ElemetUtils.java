package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ElemetUtils {
    final static Logger logger = LogManager.getLogger(ElemetUtils.class);
    public static List<WebElement> getElementsByTagName(WebDriver driver,String tagName){
        try{
            return driver.findElements(By.tagName(tagName));
        }catch (Exception e){
            logger.error("未找到相应元素");
            return null;
        }
    }

    public static List<WebElement> getElementsByTagName(WebElement element,String tagName){
        try{
            return element.findElements(By.tagName(tagName));
        }catch (Exception e){
            logger.error("未找到相应元素");
            return null;
        }
    }

    public static WebElement getElementByTagName(WebElement element,String tagName){
        try{
            return element.findElement(By.tagName(tagName));
        }catch (Exception e){
            logger.error("未找到相应元素");
            return null;
        }
    }

    public static WebElement getElementById(WebDriver driver,String id){
        try{
            return driver.findElement(By.id(id));
        }catch (Exception e){
            logger.error("未找到相应元素");
            return null;
        }
    }

    public static WebElement getElementByClass(WebElement element,String className){
        try{
            return element.findElement(By.className(className));
        }catch (Exception e){
            logger.error("未找到相应元素");
            return null;
        }
    }

    public static WebElement getElementByPath(WebElement element,String path){
        try{
            return element.findElement(By.xpath(path));
        }catch (Exception e){
            logger.error("未找到相应元素");
            return null;
        }
    }
}
