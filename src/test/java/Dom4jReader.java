import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

public class Dom4jReader {

    public void getInfo(InputStream is) {
        try {
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(is);
            //get title
//            List titleList = document.selectNodes("//*[contains(@id,\"r\")]/span[contains(@class,'noQuotes')]");
//            Iterator iter = titleList.iterator();
//            while (iter.hasNext()) {
//                Element element = (Element) iter.next();
//                System.out.println("title:" + element.getText());
//            }
            //get description
//            List descList = document.selectNodes("//*[contains(@class,\"entry\")]/p");
//            Iterator iter = descList.iterator();
//            while (iter.hasNext()) {
//                Element element = (Element) iter.next();
//                System.out.println("description:\n" + element.getText());
//            }
            //get Date of review
//            List dateList = document.selectNodes("//span[contains(@class,\"ratingDate relativeDate\")]");
//            Iterator iter = dateList.iterator();
//            while (iter.hasNext()) {
//                Element element = (Element) iter.next();
//                System.out.println("date:" + element.attribute("title").getValue());
//            }
            //get Name of reviewer
            List nameList = document.selectNodes("//div[contains(@class,\"username mo\")]/span");
            Iterator iter = nameList.iterator();
            while (iter.hasNext()) {
                Element element = (Element) iter.next();
                System.out.println("name:" + element.getText());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}