package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ReplyUtils {

    public static Boolean replayByUrl(String url, WebDriver driver,int times){
        try{
            driver.get(url);
            System.out.println("-------------------------------------------------");
            System.out.println("回复主题URL:" + url);
            System.out.println("回复主题标题:" + StringUtils.getStringTitle(driver));
            Thread.sleep(2000);
            for(int i = 0 ; i < times ; i++){
                WebElement text = driver.findElement(By.id("fastpostmessage"));
                if(text == null){
                    return false;
                }
                text.sendKeys(StringUtils.getReplyRandomString(driver));
                WebElement button = driver.findElement(By.id("fastpostsubmit"));
                if(button == null){
                    return false;
                }
                button.click();
                Thread.sleep(StringUtils.getRandom(31,35) * 1000);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     *
     * @param driver 驱动
     * @param pageUrl 论坛版面界面
     * @param count 回复主题数量
     * @return
     */
    public static Boolean replayByPage(WebDriver driver,String pageUrl,int count){
        System.out.println("---------------通过版面回复主题---------------");
        driver.get(pageUrl);
        List<String> urls = new ArrayList<String>();
        List<WebElement> webRootElements =ElemetUtils.getElementByTagName(driver,"tbody");
        for (WebElement w : webRootElements){
            List<WebElement> webBodyElements = ElemetUtils.getElementByTagName(w,"a");
            for(WebElement w1 : webBodyElements){
                if(w1.getAttribute("title").equals("新窗口打开") ||
                        w1.getAttribute("title").equals("有新回复 - 新窗口打开")){
                    urls.add(w1.getAttribute("href"));
                    System.out.println("版面主题URL"+w1.getAttribute("href"));
                }
            }
        }
        int max = count > urls.size() ? urls.size() : count;
        for(int i = 0 ; i <max ; i++){
            replayByUrl(urls.get(i),driver,1);
        }
        return true;
    }

    /**
     *
     * @param driver 驱动
     * @param fileUrl 文件名
     * @return
     */
    public static Boolean replayByPages(WebDriver driver,String fileUrl){
        System.out.println("---------------通过多个版面回复主题---------------");
        Map pageMap =  XmlUtils.getFileToMap(fileUrl);
        if(pageMap == null || pageMap.isEmpty()){
            System.out.println("版面为空");
            return false;
        }
        Iterator<Map.Entry<String,String>> iterator = pageMap.entrySet().iterator();
        List<String> urls = new ArrayList<String>();
        while (iterator.hasNext()){
            Map.Entry<String,String> p = iterator.next();
            String url ="https://bbs.3dmgame.com/" + p.getKey();
            System.out.println("版面链接" + url);
            driver.get(url);
            List<WebElement> webRootElements =ElemetUtils.getElementByTagName(driver,"tbody");
            for (WebElement w : webRootElements){
                List<WebElement> webBodyElements = ElemetUtils.getElementByTagName(w,"a");
                for(WebElement w1 : webBodyElements){
                    if(w1.getAttribute("title").equals("新窗口打开") ||
                            w1.getAttribute("title").equals("有新回复 - 新窗口打开")){
                        urls.add(w1.getAttribute("href"));
                        System.out.println("版面主题URL"+w1.getAttribute("href"));
                    }
                }
            }
        }
//        int max = Integer.valueOf(p.getValue()) > urls.size() ? urls.size() : Integer.valueOf(p.getValue());
        System.out.println("需要回复版面主题数量:" + urls.size());
        for(int i = 0 ; i <urls.size() ; i++){

            System.out.println("已回复主题数量: + "+ i + "....................................................");
            replayByUrl(urls.get(i),driver,1);
        }
        return true;
    }

}
