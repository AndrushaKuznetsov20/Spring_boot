package com.trueman.KP_Vacancy.services;

import com.trueman.KP_Vacancy.models.Announcement;
import com.trueman.KP_Vacancy.models.User;
import com.trueman.KP_Vacancy.repositories.AnnouncementRepository;
import com.trueman.KP_Vacancy.repositories.ResponseRepository;
import com.trueman.KP_Vacancy.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AnnouncmentService {
    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private ResponseRepository responseRepository;
    @Autowired
    private UserRepository userRepository;

    public AnnouncmentService(AnnouncementRepository announcementRepository, UserRepository userRepository) {
        this.announcementRepository = announcementRepository;
        this.userRepository = userRepository;
    }

    public List<Announcement> getAllAnnouncements() {
        return announcementRepository.findAll();
    }
    public List<Announcement> getAllAnnouncements_Status_OK() {
        String contract_status = "Модерировано!";
        return announcementRepository.findAll(contract_status);
    }
    public Announcement getAnnouncementById(Long id) {
        return announcementRepository.findById(id).orElse(null);
    }

    public Announcement createAnnouncement(Announcement announcement, Long id) {
        User user = userRepository.findById(id).orElse(null);
        String status_default = "Не модерировано!";
        announcement.setContract_status(status_default);
        announcement.setUser(user);
       // user.getAnnouncements().add(announcement);
       // userRepository.save(user);
        return announcementRepository.save(announcement);
    }
    //public Announcement createAnnouncementUser(Announcement announcement, User user) {
    //    String status_default = "Не модерировано";
    //    announcement.setContract_status(status_default);
    //    announcement.setUser(user);
    //    return announcementRepository.save(announcement);
    //}
    public Announcement updateAnnouncement(Long id, Announcement announcement) {
        Announcement existingAnnouncement = announcementRepository.findById(id).orElse(null);
        if (existingAnnouncement != null) {
            existingAnnouncement.setName(announcement.getName());
            existingAnnouncement.setDescription(announcement.getDescription());
            existingAnnouncement.setConditions_and_requirements(announcement.getConditions_and_requirements());
            return announcementRepository.save(existingAnnouncement);
        }
        return null;
    }
    @Transactional
    public void deleteAnnouncement(Long id) {
        responseRepository.deleteByAnnouncementId(id);
        announcementRepository.deleteById(id);
    }
}
