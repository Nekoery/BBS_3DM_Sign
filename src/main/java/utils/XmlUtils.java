package utils;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

public class XmlUtils {

    public static Map<String,String> getFileToMap(String filePath){
        try{
            File file = new File(filePath);
            if(file.exists() && file.length() > 0){
                SAXReader reader = new SAXReader();
                Document doc = reader.read(file);
                Element root = doc.getRootElement();
                Iterator<Element> nodes = root.elementIterator();
                Map<String,String> returnMap = new HashMap<String, String>();
                while (nodes.hasNext()){
                    Element n = nodes.next();
                    returnMap.put(n.getName(),n.getText());
                }
                return returnMap;
            }
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    public static List<String> getFileToList(String filePath){
        try{
            File file = new File(filePath);
            if(file.exists() && file.length() > 0){
                SAXReader reader = new SAXReader();
                Document doc = reader.read(file);
                Element cookieRoot = doc.getRootElement();
                Iterator<Element> nodes = cookieRoot.elementIterator();
                List<String> returnList = new ArrayList<String>();
                while (nodes.hasNext()){
                    Element n = nodes.next();
                    returnList.add(n.getText());
                }
                return returnList;
            }
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 保存set到文件
     * @param filename  文件名
     * @param map  源数据
     * @param rootName 根名称
     * @param rex 过滤器
     */
    public static void saveMapToFile(Map<String,String> map,String filename,String rootName,String rex){
        try{
            Document doc = DocumentHelper.createDocument();
            Element child = doc.addElement(rootName);
            Iterator<Map.Entry<String,String>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String,String> i = iterator.next();
                if(i.getKey().contains(rex)){
                    child.addElement(i.getKey(),i.getValue());
                }else {
                    continue;
                }

            }
            OutputFormat outputFormat = OutputFormat.createPrettyPrint();
            FileOutputStream outputStream = new FileOutputStream(filename);
            XMLWriter writer = new XMLWriter(outputStream, outputFormat);
            writer.write(doc);
            writer.close();
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 更新或添加一层节点
     * @param filename
     * @param key
     * @param value
     */
    public static void update(String filename,String key,String value){
        try{
            File file = new File(filename);
            if(file.exists() && file.length() > 0){
                Document doc = new SAXReader().read(file);
                Element node = doc.getRootElement().element(key);
                node.setText(value);
                FileOutputStream out = new FileOutputStream(filename);
                OutputFormat format = OutputFormat.createPrettyPrint();
                format.setEncoding("utf-8");
                XMLWriter writer = new XMLWriter(out,format);
                writer.write(doc);
                writer.close();
            }else {
                Map<String,String> map = new HashMap<String, String>();
                map.put(key,value);
                XmlUtils.saveMapToFile(map,filename,"Params","");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 获取一层节点text
     * @param filename
     * @param key
     * @return
     */
    public static String getNodeValue(String filename,String key){
        try{
            File file = new File(filename);
            if(file.exists() && file.length() > 0){
                Document doc = new SAXReader().read(file);
                Element node = doc.getRootElement().element(key);
                if(node == null){
                    return null;
                }else {
                    return node.getText();
                }
            }else {
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
