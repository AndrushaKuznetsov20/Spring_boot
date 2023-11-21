package com.trueman.KP_Vacancy.repositories;

import com.trueman.KP_Vacancy.models.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {


    @Query("SELECT a FROM Announcement a WHERE a.contract_status = :contract_status")
    List<Announcement> findAll(@Param("contract_status") String contract_status);

    @Query("SELECT a FROM Announcement a WHERE a.id = :id")
    Announcement findAnnouncementById(@Param("id") Long id);
    @Query("SELECT a FROM Announcement a WHERE a.user.id= :userId")
    List<Announcement> findByUserId(@Param("userId") Long userId);
}
