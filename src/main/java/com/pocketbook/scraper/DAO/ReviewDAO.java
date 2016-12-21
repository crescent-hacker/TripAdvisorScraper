package com.pocketbook.scraper.DAO;

import com.pocketbook.scraper.model.Review;

import java.util.List;

public interface ReviewDAO {
    /**
     * create a review record
     */
    boolean saveReviews(List<Review> reviews);

    /**
     * find review list by id
     */
    List<Review> findReviews(int id,int page);
}