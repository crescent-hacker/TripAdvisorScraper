package com.pocketbook.scraper.config;

/**
 * Author: Difan Chen
 * Date: 20/12/2016
 */
public class ConstantConfig {
    /**
     * The size of batch operation in hibernate as setup in hibernate.properties
     */
    final static public int HIBERNATE_BATCH_SIZE = 20;

    /**
     * The key that represents result of request
     */
    final static public String RETURN_SUCCESS = "success";

    /**
     * The key that represents error message
     */
    final static public String RETURN_MSG = "msg";

    /**
     * The key that represents results as a list
     */
    final static public String RETURN_LIST = "list";

    /**
     * The trip advisor domain
     */
    final static public String TRIP_ADVISOR_DOMAIN = "https://www.tripadvisor.com.au";

    /**
     * The pattern to match trip advisor domain
     */
    final static public String TRIP_ADVISOR_URL_PATTERN = "^https://www.tripadvisor.com.au/.*Reviews.*";

    /**
     * The record in each page of trip advisor review
     */
    final static public int PAGE_RECORD_NUM = 10;

    /**
     * Http client connection timeout
     */
    final static public int TIME_OUT = 10000;
}
