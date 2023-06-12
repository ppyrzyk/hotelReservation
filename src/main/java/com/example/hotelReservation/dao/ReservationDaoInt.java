package com.example.hotelReservation.dao;

import java.util.Collection;

public interface ReservationDaoInt {

    public Reservation getReservationForLoggedUserById(int resId);

    public Collection<Reservation> getReservationsByUserId(int userId);

    public void saveOrUpdateReservation(Reservation reservation);

    public void deleteReservation(Reservation reservation);

}
