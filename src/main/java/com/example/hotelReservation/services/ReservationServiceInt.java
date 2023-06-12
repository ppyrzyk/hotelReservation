package com.example.hotelReservation.services;

import com.example.hotelReservation.entity.Reservation;

import java.util.Collection;

public interface ReservationServiceInt {

    public Reservation getReservationForLoggedUserById(int resId);

    public Collection<Reservation> getReservationsForLoggedUser();

    public void saveOrUpdateReservation(CurrentReservation currentReservation);

    public void deleteReservation(int resId);

    public CurrentReservation reservationToCurrentReservationById(int resId);

}
