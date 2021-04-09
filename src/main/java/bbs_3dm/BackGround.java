package bbs_3dm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import utils.XmlUtils;

public class BackGround {
    static final Logger logger = LogManager.getLogger(BackGround.class);
    public static void isExit(final WebDriver driver){
        new Thread(new Runnable() {
            public void run() {
                while (true){
                    try {
                        Thread.sleep(5 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String isExit = XmlUtils.getNodeValue("../conf/params.xml","isExit");
                    if(isExit == null || isExit.length() < 0 || !isExit.equals("true")){
                        continue;
                    }
                    logger.error("检测到配置文件退出操作，执行退出!");
                    XmlUtils.update("../conf/params.xml","isExit","false");
                    driver.close();
                    System.exit(0);
                    break;
                }
            }
        }).start();
    }
}
