package com.pocketbook.scraper.model;

import com.pocketbook.scraper.config.ConstantConfig;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: Difan Chen
 * Date: 21/12/2016
 * Page info model - for TripAdvisor review page
 */
public class PageInfo {
    private String url;
    private String geoInfo;
    private String context;
    private String expand;
    private String servlet;
    private List<String> reviewIds;
    private String urlPrefix;
    private int maxPageNum;

    /**
     * Generate expanded url
     *
     * @return url
     */
    public String genExpandedUrl() {
        StringBuilder sb = new StringBuilder();
        sb.append(ConstantConfig.TRIP_ADVISOR_DOMAIN)
                .append(this.urlPrefix)
                .append(this.geoInfo)
                .append("?target=")
                .append(this.reviewIds.get(0))
                .append("&context=")
                .append(this.context)
                .append("&reviews=");
        for (int i = 0; i < reviewIds.size(); i++) {
            sb.append(reviewIds.get(i));
            if (i < reviewIds.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("&servlet=" + servlet)
                .append("&expand=")
                .append(this.expand);
        return sb.toString();
    }

    /**
     * Generate url by page number
     *
     * @param pageNum page number
     * @return url
     */
    public String genUrlByPageNum(int pageNum) {
        if (pageNum <= 0 || pageNum >= maxPageNum)
            return this.url;
        String keyword = "Reviews";
        String pageKeyWord = keyword + "-or" + (pageNum) * ConstantConfig.PAGE_RECORD_NUM;
        return this.url.replaceAll(keyword, pageKeyWord);
    }

    /**
     * ctor
     */
    public PageInfo(List<String> reviewIds, int maxPageNum, String url) {
        this.urlPrefix = "/ExpandedUserReviews";
        this.context = "1";
        this.expand = "1";
        this.servlet = "Attraction_Review";
        this.reviewIds = reviewIds;
        this.maxPageNum = maxPageNum;
        this.url = url.replaceAll("-or([0-9]+)", "");
        //find geo info string
        String reg = "-g[0-9]+-d[0-9]+";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(this.url);
        if (matcher.find()) {
            this.geoInfo = matcher.group();
        }
    }

    public String getUrl() {
        return url;
    }

    public String getGeoInfo() {
        return geoInfo;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getExpand() {
        return expand;
    }

    public void setExpand(String expand) {
        this.expand = expand;
    }

    public String getServlet() {
        return servlet;
    }

    public void setServlet(String servlet) {
        this.servlet = servlet;
    }

    public List<String> getReviewIds() {
        return reviewIds;
    }

    public void setReviewIds(List<String> reviewIds) {
        this.reviewIds = reviewIds;
    }

    public String getUrlPrefix() {
        return urlPrefix;
    }

    public void setUrlPrefix(String urlPrefix) {
        this.urlPrefix = urlPrefix;
    }

    public int getMaxPageNum() {
        return maxPageNum;
    }

    public void setMaxPageNum(int maxPageNum) {
        this.maxPageNum = maxPageNum;
    }
}
