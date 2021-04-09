package bbs_3dm;

import constant.Contstant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import utils.ReplyUtils;
import utils.RequestUtils;
import utils.TimeUtils;
import utils.XmlUtils;
import java.util.List;


public class BBS_3DMTaskUtils {
    final static Logger logger = LogManager.getLogger(BBS_3DMTaskUtils.class);
    public static boolean runtask(WebDriver driver){
        if(BBS_3DMTaskUtils.chargeIsSignToday()){
            logger.info("今日已签到成功");
            return true;
        }
        logger.info("-------------------申请任务中-------------------");
        if(!BBS_3DMTaskUtils.applyTask(driver)){
            return false;
        }
        logger.info("-------------------回复帖子中-------------------");
        BBS_3DMTaskUtils.replayTask(driver);
        logger.info("-------------------完成任务中-------------------");
        BBS_3DMTaskUtils.drawTask(driver);
        XmlUtils.update("../conf/params.xml","issign",
                TimeUtils.millis2String(System.currentTimeMillis(),TimeUtils.getFormat("yyyyMMdd")));
        logger.info("-------------------任务完成-------------------");
        return true;
    }

    public static boolean applyTask(WebDriver driver){
        List<String> taskList = XmlUtils.getFileToList("../conf/task.xml");
        if(taskList == null || taskList.isEmpty()){
            logger.info("任务列表为空");
            return false;
        }
        logger.info("执行总任务ID" + taskList);
        RequestUtils.request(Contstant.TaskApplyUrl,taskList,driver,1,"接受任务");
        return true;
    }

    public static boolean replayTask(WebDriver driver){
        ReplyUtils.replayByUrl("https://bbs.3dmgame.com/thread-5751666-1-1.html",driver,1);
        ReplyUtils.replayByUrl("https://bbs.3dmgame.com/thread-5836648-1-1.html",driver,6);
        ReplyUtils.replayByUrl("https://bbs.3dmgame.com/thread-5860870-1-1.html",driver,2);
        ReplyUtils.replayByUrl("https://bbs.3dmgame.com/thread-5573492-1-1.html",driver,1);
        return true;
    }

    public static boolean drawTask(WebDriver driver){
        List<String> taskList = XmlUtils.getFileToList("../conf/task.xml");
        if(taskList == null || taskList.isEmpty()){
            logger.info("任务文件为空");
            return false;
        }
        logger.info("执行总任务ID" + taskList);
        RequestUtils.request(Contstant.TaskDrawUrl,taskList,driver,1,"完成任务");
        return true;
    }

    public static boolean delTask(WebDriver driver){
        List<String> taskList = XmlUtils.getFileToList("../conf/task.xml");
        if(taskList == null || taskList.isEmpty()){
            return false;
        }
        logger.info("执行总任务ID" + taskList);
        RequestUtils.request(Contstant.TaskDelUrl,taskList,driver,1,"放弃任务");
        return true;
    }


    public static boolean chargeIsSignToday(){
        String today = TimeUtils.millis2String(System.currentTimeMillis(),TimeUtils.getFormat("yyyyMMdd"));
        String signDay = XmlUtils.getNodeValue("../conf/params.xml","issign");
        if(signDay == null || signDay.length() == 0){
            logger.info("文件或节点为空");
            return false;
        }
        logger.info("签到日期为:" + signDay);
        logger.info("当前日期为:" + today);
        if(today.equals(signDay)){
            return true;
        }
        return false;
    }
}
