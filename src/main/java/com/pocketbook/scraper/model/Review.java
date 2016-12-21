package com.pocketbook.scraper.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Author: Difan Chen
 * Date: 20/12/2016
 */
@Entity
@Table(name="review")
public class Review implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    /**
     * The scrape history id that related to scrape_history
     */
//    @ManyToOne(targetEntity=ScrapeHistory.class)
    @Column(name = "sh_id",nullable = false)
    private int shId;

    /**
     * Title
     */
    @Column(name = "title",nullable = false)
    private String title;

    /**
     * Full review description
     */
    @Column(name = "description",columnDefinition = "TEXT",nullable = false)
    private String description;

    /**
     * Date of review
     */
    @Column(name = "review_date",columnDefinition = "DATE",nullable = false)
    private Date dateOfReview;

    /**
     * Name of reviewer
     */
    @Column(name = "reviewer_name",nullable = false)
    private String nameOfReviewer;

    /*************** Getter and setter *****************/
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShId() {
        return shId;
    }

    public void setShId(int shId) {
        this.shId = shId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateOfReview() {
        return dateOfReview;
    }

    public void setDateOfReview(Date dateOfReview) {
        this.dateOfReview = dateOfReview;
    }

    public String getNameOfReviewer() {
        return nameOfReviewer;
    }

    public void setNameOfReviewer(String nameOfReviewer) {
        this.nameOfReviewer = nameOfReviewer;
    }
}