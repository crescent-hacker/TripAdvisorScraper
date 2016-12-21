package com.pocketbook.scraper.service.impl;

import com.pocketbook.scraper.DAO.ReviewDAO;
import com.pocketbook.scraper.DAO.ScrapeHistoryDAO;
import com.pocketbook.scraper.config.ConstantConfig;
import com.pocketbook.scraper.model.PageInfo;
import com.pocketbook.scraper.model.Review;
import com.pocketbook.scraper.model.ScrapeHistory;
import com.pocketbook.scraper.service.ScrapeService;
import com.pocketbook.scraper.service.helper.ScrapeServiceHelper;
import com.pocketbook.scraper.tool.HttpConnector;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ScrapeServiceImpl implements ScrapeService {
    //logger
    private static final Logger logger = Logger.getLogger(ScrapeServiceHelper.class);
    @Autowired
    ScrapeHistoryDAO scrapeHistoryDAO;
    @Autowired
    ReviewDAO reviewDAO;

    @Override
    public List<ScrapeHistory> getScrapeHistories() {
        return scrapeHistoryDAO.findAll();
    }

    @Override
    public void launchScrape(Map<String, Object> retMap, ScrapeHistory history) {
        List<Review> reviews = new ArrayList<Review>();
        //1. Create history for this launch
        boolean isHistoryCreated = scrapeHistoryDAO.save(history);
        int insertId = history.getId();

        //2. Crawling the target page
        //  2.1 get source page
        String sourceUrl = history.getUrl();
        String sourceHtml = HttpConnector.getUrlHtml(sourceUrl);
        //  2.2 Get page info
        PageInfo pageInfo = ScrapeServiceHelper.getPageInfo(sourceHtml,history.getUrl());
        //  2.3 Get expanded page
        String expandedHtml = HttpConnector.getUrlHtml(pageInfo.genExpandedUrl());
        //  2.4 Preprocessing before analyse
        expandedHtml = ScrapeServiceHelper.preprocessing(expandedHtml);
        //  2.4 Get reviews for current page info
        reviews.addAll(ScrapeServiceHelper.getExpandReviews(expandedHtml,pageInfo.getReviewIds().size()));

        //  2.5 Repeat steps above to fetch all pages and get their expanded pages
        for (int i = 1; i < pageInfo.getMaxPageNum(); i++) {
            sourceUrl = pageInfo.genUrlByPageNum(i);
            sourceHtml = HttpConnector.getUrlHtml(sourceUrl);
            logger.info("Scraping "+i+" page related to "+sourceUrl);
            pageInfo.setReviewIds(ScrapeServiceHelper.getCurrentPageReviewIds(sourceHtml));
            expandedHtml = HttpConnector.getUrlHtml(pageInfo.genExpandedUrl());
            expandedHtml = ScrapeServiceHelper.preprocessing(expandedHtml);
            reviews.addAll(ScrapeServiceHelper.getExpandReviews(expandedHtml,pageInfo.getReviewIds().size()));
        }

        //3. save reviews
        for (int i = 0; i < reviews.size(); i++) {
            reviews.get(i).setShId(history.getId());
        }
        boolean isReviewsSaved = reviewDAO.saveReviews(reviews);

        //return
        retMap.put(ConstantConfig.RETURN_SUCCESS, isHistoryCreated && isReviewsSaved);
        retMap.put("insertId", insertId);
    }

    @Override
    public List<Review> getReviewsByHistoryIdx(int shId,int pageNum) {
        return reviewDAO.findReviews(shId,pageNum);
    }
}