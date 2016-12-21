package com.pocketbook.scraper.DAO.impl;

import com.pocketbook.scraper.DAO.ScrapeHistoryDAO;
import com.pocketbook.scraper.model.ScrapeHistory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ScrapeHistoryDAOImpl implements ScrapeHistoryDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public boolean save(ScrapeHistory history) {
        return (Integer) sessionFactory.getCurrentSession().save(history)> 0;
    }

    public List<ScrapeHistory> findAll() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ScrapeHistory.class);
        criteria.addOrder(Order.desc("scrapeDate"));
        return criteria.list();
    }
}