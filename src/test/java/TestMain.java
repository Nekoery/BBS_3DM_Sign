import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import bbs_3dm.BBS_3DMCookieUtils;
import utils.ReplyUtils;

public class TestMain {
//    public static void main(String[] args) {
//        try{
//            System.setProperty("webdriver.chrome.driver", "../conf/chromedriver.exe");
//            final WebDriver driver = new ChromeDriver();
//            driver.get("https://bbs.3dmgame.com/");
//            if(!BBS_3DMCookieUtils.setCookieIfExist(driver)){
//                BBS_3DMCookieUtils.writeCookieToFile(driver);
//            }else {
//                System.out.println("已检测到Cookie,执行后续操作");
//                BBS_3DMTaskUtils.runtask(driver);
//                ReplyUtils.replayByUrl("https://bbs.3dmgame.com/thread-6162180-1-1.html",driver,1);
//                ReplyUtils.replayByPages(driver,"../conf/replayPages.xml");
//                driver.close();
//                System.exit(0);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }
}
