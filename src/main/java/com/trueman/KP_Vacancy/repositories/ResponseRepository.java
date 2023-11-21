package com.trueman.KP_Vacancy.repositories;

import com.trueman.KP_Vacancy.models.Announcement;
import com.trueman.KP_Vacancy.models.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResponseRepository extends JpaRepository<Response, Long> {

    @Query("SELECT r FROM Response r WHERE r.user_response.id= :userId")
    List<Response> findByUserId(@Param("userId") Long userId);
}
