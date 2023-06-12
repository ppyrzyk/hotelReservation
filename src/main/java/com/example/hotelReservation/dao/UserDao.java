package com.example.hotelReservation.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao implements UserDaoInt{

    @Autowired
    private EntityManager entityManager;


    @Override
    public User findUserByEmail(String email) {

        Query<User> query = currentSession().createQuery("from User where user_email=:uEmail", User.class);
        query.setParameter("uEmail", email);

        User user = null;
        try {
            user = query.getSingleResult();
        } catch (Exception e) {
            user = null;
        }

        return user;
    }


    @Override
    public User findUserByUsername(String username) {

        Query<User> query = currentSession().createQuery("from User where user_username=:uName", User.class);
        query.setParameter("uName", username);

        User user = null;
        try {
            user = query.getSingleResult();
        } catch (Exception e) {
            user = null;
        }

        return user;
    }

    @Override
    public void saveUser(User theUser) {
        currentSession().saveOrUpdate(theUser);
    }

    private Session currentSession() {
        return entityManager.unwrap(Session.class);
    }
}
