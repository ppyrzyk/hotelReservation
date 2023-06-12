package com.example.hotelReservation.dao;

public interface UserDaoInt {

    public User findUserByEmail(String email);

    public User findUserByUsername(String username);

    public void saveUser(User user);

}
