package com.example.hotelReservation.dao;

import javax.management.relation.Role;

public interface RoleDaoInt {

    public Role findRoleByName(String roleName);
}
