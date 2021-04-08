package utils;

import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;

public class RequestUtils {
    public static void request(final String url,final WebDriver driver, final int timeDelay){
        List<String> list = new ArrayList<String>();
        list.add(url);
        request(list,driver,timeDelay);
    }

    public static void request(final List<String> url, final WebDriver driver, final int timeDelay){
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(timeDelay * 1000);
                    for(String u:url){
                        driver.get(u);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void request(final String baseUrl,final List<String> id, final WebDriver driver, final int timeDelay,final String des){
        try {
            for(String i:id){
                Thread.sleep(timeDelay * 1000);
                System.out.println(des +" 执行URL:" + baseUrl + i);
                driver.get(baseUrl + i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
