package com.pocketbook.scraper.model;

import com.pocketbook.scraper.config.ConstantConfig;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

/**
 * Author: Difan Chen
 * Date: 20/12/2016
 */
@Entity
@Table(name="scrape_history")
public class ScrapeHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    /**
     * The given url to scrape
     */
    @Column(name="url",nullable = false)
    @Pattern(regexp=ConstantConfig.TRIP_ADVISOR_URL_PATTERN)
    private String url;

    /**
     * The date of url scraping
     */
    @Column(name="scrape_date",columnDefinition="TIMESTAMP")
    private String scrapeDate;


    /*************** Getter and setter *****************/
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getScrapeDate() {
        return scrapeDate;
    }

    public void setScrapeDate(String scrapeDate) {
        this.scrapeDate = scrapeDate;
    }
}
