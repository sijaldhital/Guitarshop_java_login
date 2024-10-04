package com.sijal.guitarshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sijal.guitarshop.entity.UserEnt;



@Repository
public interface MyUserRepository extends JpaRepository<UserEnt, Long>{
    UserEnt findByUsername(String username);
}
