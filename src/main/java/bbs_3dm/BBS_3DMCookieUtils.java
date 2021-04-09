package bbs_3dm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import utils.XmlUtils;

import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class BBS_3DMCookieUtils {
    final static Logger logger = LogManager.getLogger(BBS_3DMCookieUtils.class);
    public static void writeCookieToFile(final WebDriver driver){
        try{
            WebDriver.Options manage = driver.manage();
            while (true){
                Thread.sleep(1000 * 5);
                if(manage.getCookieNamed("uchome_2132_auth") == null){
                    logger.error("未检测到Cookie");
                    continue;
                }else {
                    logger.info("检测到Cookie");
                    Document doc = DocumentHelper.createDocument();
                    Element cookie = doc.addElement("Cookie");
                    Set<Cookie> cookies = manage.getCookies();
                    for(Cookie c:cookies){
                        if(c.getName().contains("uchome_2132")){
                            cookie.addElement(c.getName()).addText(c.getValue());
                        }else {
                            continue;
                        }
                    }
                    OutputFormat outputFormat = OutputFormat.createPrettyPrint();
                    FileOutputStream outputStream = new FileOutputStream("../conf/cookie.xml");
                    XMLWriter writer = new XMLWriter(outputStream, outputFormat);
                    writer.write(doc);
                    writer.close();
                    outputStream.close();
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Boolean setCookieIfExist(WebDriver driver){
        try{
            logger.info("检测Cookie中");
            Map<String,String> resultMap = XmlUtils.getFileToMap("../conf/cookie.xml");
            if(resultMap == null || resultMap.isEmpty()){
                return false;
            }
            WebDriver.Options manage = driver.manage();
            manage.deleteAllCookies();
            logger.info("---------------------Cookie---------------------");
            Iterator<Map.Entry<String,String>> nodes = resultMap.entrySet().iterator();
            while (nodes.hasNext()){
                Map.Entry<String,String> n =  nodes.next();
                Cookie cookie = new Cookie(n.getKey(),n.getValue());
                logger.info(n.getKey()+" : " +n.getValue());
                manage.addCookie(cookie);
            }
            driver.navigate().refresh();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static Boolean isCookieExit(){
        logger.info("检测Cookie中");
        Map<String,String> resultMap = XmlUtils.getFileToMap("../conf/cookie.xml");
        if(resultMap == null || resultMap.isEmpty()){
            return false;
        }
        return true;
    }
}
