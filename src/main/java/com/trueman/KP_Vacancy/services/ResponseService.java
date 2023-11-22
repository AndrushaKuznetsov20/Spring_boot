package com.trueman.KP_Vacancy.services;

import com.trueman.KP_Vacancy.models.Announcement;
import com.trueman.KP_Vacancy.models.Response;
import com.trueman.KP_Vacancy.models.User;
import com.trueman.KP_Vacancy.repositories.AnnouncementRepository;
import com.trueman.KP_Vacancy.repositories.ResponseRepository;
import com.trueman.KP_Vacancy.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ResponseService {
    @Autowired
    private ResponseRepository responseRepository;
    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private UserRepository userRepository;

    public ResponseService(ResponseRepository responseRepository, AnnouncementRepository announcementRepository, UserRepository userRepository) {
        this.responseRepository = responseRepository;
        this.announcementRepository = announcementRepository;
        this.userRepository = userRepository;
    }

    public List<Response> getAllResponses() {
        return responseRepository.findAll();
    }

    public Response createResponse(Long announcementId, Long userId) {
        Response response = new Response();
        Announcement announcement = announcementRepository.findById(announcementId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);
        response.setAnnouncement(announcement);
        response.setUser_response(user);
        Response savedResponse = responseRepository.save(response);

        announcement.getList_users().add(user);
        announcementRepository.save(announcement);
        return savedResponse;
    }

    public void deleteResponse(Long id) {
        responseRepository.deleteById(id);
    }
}
