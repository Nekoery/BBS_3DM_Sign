import bbs_3dm.BBS_3DMCookieUtils;
import bbs_3dm.BBS_3DMTaskUtils;
import bbs_3dm.BackGround;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import utils.ReplyUtils;
import utils.XmlUtils;

public class Main {
    final static Logger logger = LogManager.getLogger(Main.class);
    public static void main(String[] args) {
        try{
            String osName = System.getProperty("os.name").toLowerCase();
            if(osName.startsWith("win")){
                logger.info("当前系统为Windows:" + osName);
                System.setProperty("webdriver.chrome.driver", "../conf/chromedriver.exe");
            }else {
                logger.info("当前系统为Linux:" + osName);
                System.setProperty("webdriver.chrome.driver", "../conf/chromedriver");
            }
            logger.info("根据Cookie设置驱动模式");
            WebDriver driver = null;
            if (BBS_3DMCookieUtils.isCookieExit()){
                logger.info("Cookie已存在，设置无界面模式");
                ChromeOptions chromeOptions=new ChromeOptions();
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments("--headless");
                driver = new ChromeDriver(chromeOptions);
            }else {
                if(osName.startsWith("win")){
                    logger.info("Cookie不存在，Windows模式，设置界面模式");
                    driver = new ChromeDriver();
                }else {
                    logger.info("Cookie不存在，Linux模式，无法操作，正在退出");
                    return;
                }
            }

            driver.get("https://bbs.3dmgame.com/");
            BackGround.isExit(driver);
            if(!BBS_3DMCookieUtils.setCookieIfExist(driver)){
                BBS_3DMCookieUtils.writeCookieToFile(driver);
            }
            logger.info("已检测到Cookie,执行后续操作");
            BBS_3DMTaskUtils.runtask(driver);
            if(XmlUtils.getNodeValue("../conf/params.xml","needReplayPages").equals("true")){
                logger.info("已配置回复版面帖子");
                ReplyUtils.replayByPages(driver,"../conf/replayPages.xml");
            }else {
                logger.error("未配置回复版面帖子，结束运行");
            }
            driver.close();
            System.exit(0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
