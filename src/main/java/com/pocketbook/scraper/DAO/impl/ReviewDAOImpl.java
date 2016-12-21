package com.pocketbook.scraper.DAO.impl;

import com.pocketbook.scraper.DAO.ReviewDAO;
import com.pocketbook.scraper.config.ConstantConfig;
import com.pocketbook.scraper.model.Review;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReviewDAOImpl implements ReviewDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public boolean saveReviews(List<Review> reviews) {
        int result = 0;
        //batch insertion start
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        for ( int i=0; i<reviews.size(); i++ ) {
            //if any save return 0, the result will be always 0
            if((Integer) session.save(reviews.get(i))>0)
            result += 1;
            //same as the JDBC batch size
            if ( i % ConstantConfig.HIBERNATE_BATCH_SIZE == 0 ) {
                //flush a batch of inserts and release memory
                session.flush();
                session.clear();
            }
        }

        tx.commit();
        session.close();
        return result == reviews.size();
    }

    public List<Review> findReviews(int id,int page){
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Review.class);
        criteria.add(Restrictions.eq("shId",id));
        criteria.addOrder(Order.desc("dateOfReview"));
        return criteria.list();
    }
}