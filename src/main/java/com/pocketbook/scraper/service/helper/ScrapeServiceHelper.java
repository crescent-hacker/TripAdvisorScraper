package com.pocketbook.scraper.service.helper;

import com.pocketbook.scraper.config.ConstantConfig;
import com.pocketbook.scraper.model.PageInfo;
import com.pocketbook.scraper.model.Review;
import org.apache.log4j.Logger;
import org.apache.tools.ant.filters.StringInputStream;
import org.apache.tools.ant.taskdefs.MacroDef;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: Difan Chen
 * Date: 20/12/2016
 * Helper for scrape service
 */
public class ScrapeServiceHelper {
    //logger
    private static final Logger logger = Logger.getLogger(ScrapeServiceHelper.class);

    /**
     * Get page info include max_page_num,modelLocId,modelGeoId,review ids and so on
     *
     * @param html input html content
     * @param url
     */
    public static PageInfo getPageInfo(String html, String url) {
        //1.get review id list
        List<String> reviewIds = getCurrentPageReviewIds(html);
        //2.get max page number
        int maxPageNumber = -1;
        String reg = "data-page-number=\"([0-9]+)\"";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(html);
        while (matcher.find()) {
            int pageNumber = Integer.parseInt(matcher.group(1));
            if (pageNumber > maxPageNumber)
                maxPageNumber = pageNumber;
        }
        //3.create pageInfo and return
        return new PageInfo(reviewIds, maxPageNumber, url);
    }

    /**
     * Get page info include max_page_num,modelLocId,modelGeoId,review ids and so on
     *
     * @param html input html content
     */
    public static List<String> getCurrentPageReviewIds(String html) {
        //return review id list
        List<String> reviewIds = new ArrayList<String>();
        //find review ids
        String reg = "<div id=\"review_?([0-9]+)\" class=\"reviewSelector";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(html);
        while (matcher.find()) {
            reviewIds.add(matcher.group(1));
        }
        return reviewIds;
    }

    /**
     * Pre-processing html before dom4j can analyze
     *
     * @param html input html
     * @return processed html
     */
    public static String preprocessing(String html) {
        //1.remove script
        html = html.replaceAll("(?is)<script[^>]*?>.*?</script>", "");

        //2. fix error a link block: <a onclick=>......</div>
        String errorALink = "<a onclick=>";
        String fixedALink = "<a onclick=\"\">";
        html = html.replaceAll(errorALink, fixedALink);

        //3. remove unclosed image
        html = html.replaceAll("<img(.*?)></img>", "");
        html = html.replaceAll("<img(.*?)>", "");

        //4. remove &nbsp;
        html = html.replaceAll("&nbsp;", "");

        //5. add root
        html = "<reviews>" + html + "</reviews>";

        return html;
    }

    /**
     * Get the expanded reviews' info
     *
     * @param html input html content
     */
    public static List<Review> getExpandReviews(String html, int reviewAmount) {
        //create Review container
        List<Review> reviews = new ArrayList<Review>();
        for (int i = 0; i < reviewAmount; i++) {
            reviews.add(new Review());
        }
        //string stream
        try {
            Iterator iter;
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(new StringInputStream(html));

            // Index that need to ignore which is actually owner response
            // Because the structure
            Map<Integer, Boolean> ignoreIdxMap = new HashMap<Integer, Boolean>();

            //1. get title
            List titleList = document.selectNodes("//*[contains(@id,\"r\")]/span[contains(@class,'noQuotes')]");
            iter = titleList.iterator();
            for (int i = 0, j = 0; iter.hasNext(); j++) {
                Element element = (Element) iter.next();
                String title = element.getText();
                if (title.equals("Owner response")) {
                    ignoreIdxMap.put(j, true);
                } else {
                    ignoreIdxMap.put(j, false);
                    reviews.get(i).setTitle(title);
                    i++;
                }
            }

            //2. get description
            List descList = document.selectNodes("//*[contains(@class,\"entry\")]/p");
            iter = descList.iterator();
            for (int i = 0,j=0; iter.hasNext(); j++) {
                Element element = (Element) iter.next();
                //ignore the date of owner response
                if (!ignoreIdxMap.get(j)) {
                    reviews.get(i).setDescription(element.getText().replaceAll("\n", ""));
                    i++;
                }
            }

            //3. get Date of review
            List dateList = document.selectNodes("//span[contains(@class,\"ratingDate\")]");
            iter = dateList.iterator();
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");

            for (int i = 0, j = 0; iter.hasNext(); j++) {
                Element element = (Element) iter.next();
                String dateStr;
                //handle diff situation of rating date
                Attribute titleAttr = element.attribute("title");
                if (titleAttr != null) {
                    dateStr = titleAttr.getValue().replaceAll("Reviewed ", "").replaceAll("\n", "");
                } else {
                    dateStr = element.getText().replaceAll("Reviewed ", "").replaceAll("\n", "");
                }

                //ignore the date of owner response
                if (!ignoreIdxMap.get(j)) {
                    reviews.get(i).setDateOfReview(sdf.parse(dateStr));
                    i++;
                }
            }

            //4. get Name of reviewer
            List nameList = document.selectNodes("//div[contains(@class,\"username\")]");
            iter = nameList.iterator();
            for (int i = 0,j=0; iter.hasNext()&&j<nameList.size(); j++) {
                if (!ignoreIdxMap.get(j)) {
                    Element element = (Element) iter.next();
                    //check div.span first, if not found then check div
                    List spanList = element.selectNodes("./span");
                    if(spanList.size()>0){
                        Element span = (Element) spanList.get(0);
                        reviews.get(i).setNameOfReviewer(span.getText());
                    }else{
                        reviews.get(i).setNameOfReviewer(element.getText());
                    }
                    i++;
                }
            }

            for (int i = 0; i < reviews.size(); i++) {
                if(reviews.get(i).getNameOfReviewer()==null){
                    logger.error("##########Error#######   "+i);
                }
            }
        } catch (Exception ex) {
            logger.error(ex);
        }

        return reviews;
    }
}
