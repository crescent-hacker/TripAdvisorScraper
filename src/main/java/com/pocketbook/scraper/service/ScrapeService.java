package com.pocketbook.scraper.service;

import com.pocketbook.scraper.model.Review;
import com.pocketbook.scraper.model.ScrapeHistory;

import java.util.List;
import java.util.Map;

public interface ScrapeService {
    /**
     * Get all histories
     */
    List<ScrapeHistory> getScrapeHistories();

    /**
     * 1. Create a scrape history for a given url
     * 2. Crawling trip advisor using given url
     */
    void launchScrape(Map<String, Object> retMap, ScrapeHistory history);

    /**
     * Get reviews by history id
     */
    List<Review> getReviewsByHistoryIdx(int shId,int pageNum);
}