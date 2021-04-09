package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import static utils.TimeUtils.*;

public class StringUtils {
    final static Logger logger = LogManager.getLogger(StringUtils.class);
    public static String getReplyRandomString(WebDriver webDriver){
        Random random=new Random();
        int type=random.nextInt(50);
        String retrunString = "";
        if(type < 13){
            retrunString =  getStaticString(type);
        }else if(type < 15){
            retrunString = getRandomString(type);
        }else if(type < 30){
            retrunString = getStringByTime(System.currentTimeMillis());
        }else {
            retrunString = getStringTitle(webDriver);
        }
        logger.info("回复内容为:" + retrunString);
        return retrunString;
    }

    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    public static String getStaticString(int position){
        List<String> list = new ArrayList<String>();
        list.add("于是你就水了一贴");
        list.add("你们有见过这么整齐的十五个字吗");
        list.add("他们都说打出十五字才是最标准的");
        list.add("我也没办法因为我要打十五个字啊");
        list.add("我说了多少次了不要只打十五个字");
        list.add("为了经验我没办法只能遇贴就灌水");
        list.add("灌水也要讲技术保证句句是十五字");
        list.add("如今发帖有困难整不好就被删贴了");
        list.add("只能够这样用十五字来混混经验了");
        list.add("用十五字混经验的有谁比我厉害呢");
        list.add("有前排就要占没前排也要灌一下水");
        list.add("有前排不占或者不灌水是会后悔的");
        list.add("因为为了一句话万一这贴子火了呢");
        return list.get(position);
    }

    public static String getStringByTime(final long millis) {
        return millis2String(millis, getSimpleFormat()) + "-回复楼主帖子=.=";
    }

    public static String getStringTitle(WebDriver driver){
        try{
            WebElement text = driver.findElement(By.id("thread_subject"));
            return text.getText();
        }catch (Exception e){
            e.printStackTrace();
            return "6666666666";
        }
    }

    public static int getRandom(int min,int max){
        return min+(int)(Math.random()*(max+1-min));
    }
}
