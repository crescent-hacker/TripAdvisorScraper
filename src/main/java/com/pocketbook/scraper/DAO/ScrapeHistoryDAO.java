package com.pocketbook.scraper.DAO;

import com.pocketbook.scraper.model.ScrapeHistory;

import java.util.List;

public interface ScrapeHistoryDAO {
    /**
     * create a scrape history
     */
    boolean save(ScrapeHistory history);

    /**
     * query all histories
     */
    List<ScrapeHistory> findAll();
}