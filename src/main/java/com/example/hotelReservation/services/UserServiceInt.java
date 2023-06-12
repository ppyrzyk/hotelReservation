package com.example.hotelReservation.services;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserServiceInt  extends UserDetailsService {

        public User findUserByEmail(String email);

        public void saveUser(CurrentUser currentUser);

        public int getLoggedUserId();
}
