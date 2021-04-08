import bbs_3dm.BBS_3DMCookieUtils;
import bbs_3dm.BBS_3DMTaskUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import utils.ReplyUtils;
import utils.XmlUtils;

public class Main {
    public static void main(String[] args) {
        try{
            System.setProperty("webdriver.chrome.driver", "../conf/chromedriver.exe");
            final WebDriver driver = new ChromeDriver();
            driver.get("https://bbs.3dmgame.com/");
            if(!BBS_3DMCookieUtils.setCookieIfExist(driver)){
                BBS_3DMCookieUtils.writeCookieToFile(driver);
            }
            System.out.println("已检测到Cookie,执行后续操作");
            BBS_3DMTaskUtils.runtask(driver);
            if(XmlUtils.getNodeValue("../conf/params.xml","needReplayPages").equals("true")){
                System.out.println("已配置回复版面帖子");
                ReplyUtils.replayByPages(driver,"../conf/replayPages.xml");
            }else {
                System.out.println("未配置回复版面帖子，结束运行");
            }
            driver.close();
            System.exit(0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
