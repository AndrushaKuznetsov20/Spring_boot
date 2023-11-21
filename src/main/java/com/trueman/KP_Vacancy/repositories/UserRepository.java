package com.trueman.KP_Vacancy.repositories;

import com.trueman.KP_Vacancy.models.Announcement;
import com.trueman.KP_Vacancy.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByName(String name);
    User findByName(String name);
    User findByPassword(String password);
    User findByEmail(String email);

    List<User> findAll();
}

