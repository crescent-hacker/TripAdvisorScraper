package com.pocketbook.scraper.controller;

import com.pocketbook.scraper.config.ConstantConfig;
import com.pocketbook.scraper.config.MessageConfig;
import com.pocketbook.scraper.model.Review;
import com.pocketbook.scraper.model.ScrapeHistory;
import com.pocketbook.scraper.service.ScrapeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Difan Chen
 * Date: 20/12/2016
 */
@Controller
@RequestMapping("/")
public class ScrapeController {
    //logger
    private static final Logger logger = Logger.getLogger(ScrapeController.class);
    @Autowired
    private ScrapeService scrapeService;

    /**
     * get home page
     */
    @RequestMapping("")
    public String index() {
        return "index";
    }

    /**
     * Launch scraping
     *
     * @param history model to receive url
     * @param errors  binding errors
     * @return result json
     */
    @RequestMapping(value = "launchScrape", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> launchScrape(@Valid ScrapeHistory history, Errors errors) {
        //return map
        Map<String, Object> retMap = new HashMap<String, Object>();
        //validation
        if (errors.hasErrors()) {
            retMap.put(ConstantConfig.RETURN_SUCCESS, false);
            retMap.put(ConstantConfig.RETURN_MSG, MessageConfig.VALID_INVALID_URL);
            return retMap;
        }
        //create history
        scrapeService.launchScrape(retMap, history);
        logger.info("launchScrape ~ success:" + retMap.get(ConstantConfig.RETURN_SUCCESS));

        return retMap;
    }

    /**
     * Get scrape histories
     *
     * @return result json
     */
    @RequestMapping(value = "getHistories", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getHistories() {
        //call service to get histories
        List<ScrapeHistory> histories = scrapeService.getScrapeHistories();
        //return map
        Map<String, Object> retMap = new HashMap<String, Object>();
        retMap.put(ConstantConfig.RETURN_SUCCESS, histories.size() != 0);
        retMap.put(ConstantConfig.RETURN_LIST, histories);
        return retMap;
    }

    /**
     * Get reviews by history id
     *
     * @return result json
     */
    @RequestMapping(value = "getReviews", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getReviews(int shId,int pageNum) {
        //call service to get histories
        List<Review> reviews = scrapeService.getReviewsByHistoryIdx(shId,pageNum);
        //return map
        Map<String, Object> retMap = new HashMap<String, Object>();
        retMap.put(ConstantConfig.RETURN_SUCCESS, reviews.size() != 0);
        retMap.put(ConstantConfig.RETURN_LIST, reviews);
        return retMap;
    }


}
