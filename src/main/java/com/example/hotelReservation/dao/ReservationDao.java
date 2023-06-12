package com.example.hotelReservation.dao;

import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

public class ReservationDao implements ReservationDaoInt{
    @Autowired
    private EntityManager entityManager;

    // retrieve all reservations for logged user from database
    @Override
    public Collection<Reservation> getReservationsByUserId(int userId) {

        // create query with HQL to get reservations list
        Query<Reservation> query = currentSession().createQuery("from Reservation where reservation_user_id=:userId",
                Reservation.class);
        query.setParameter("userId", userId);

        return query.getResultList();
    }

    // retrieve specific reservation by it's id
    @Override
    public Reservation getReservationForLoggedUserById(int resId) {

        // create query with HQL to get reservation
        Query<Reservation> query = currentSession().createQuery("from Reservation where reservation_id=:resId",
                Reservation.class);
        query.setParameter("resId", resId);

        return query.getSingleResult();
    }

    // save or update reservation in database
    @Override
    public void saveOrUpdateReservation(Reservation reservation) {
        currentSession().saveOrUpdate(reservation);
    }

    // delete reservation that stored in database
    @Override
    public void deleteReservation(Reservation reservation) {
        currentSession().delete(reservation);
    }

    // get current hibernate session
    private Session currentSession() {
        return entityManager.unwrap(Session.class);
    }

}
