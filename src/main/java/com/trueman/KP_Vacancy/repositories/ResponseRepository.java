package com.trueman.KP_Vacancy.repositories;

import com.trueman.KP_Vacancy.models.Announcement;
import com.trueman.KP_Vacancy.models.Response;
import com.trueman.KP_Vacancy.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResponseRepository extends JpaRepository<Response, Long> {

    @Query("SELECT r FROM Response r WHERE r.user_response.id= :userId")
    List<Response> findByUserId(@Param("userId") Long userId);
//    boolean existsByUserResponseIdAndAnnouncementId(Long user_ResponseId, Long announcementId);
    void deleteByAnnouncementId(Long id);
//    @Query("SELECT r FROM Response r WHERE r.announcement.id= :id")
//    void deleteResponse(@Param("id") Long id);

    @Query("SELECT r FROM Response r WHERE r.user_response.id= :userId AND r.announcement.id = :announcementId")
    List<Response> findByAnnouncementIdAndUser_responseId(@Param("announcementId") Long announcementId, @Param("userId") Long userId);
}
