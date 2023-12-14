package com.trueman.KP_Vacancy.controllers;

import com.trueman.KP_Vacancy.models.Announcement;
import com.trueman.KP_Vacancy.models.User;
import com.trueman.KP_Vacancy.repositories.AnnouncementRepository;
import com.trueman.KP_Vacancy.repositories.UserRepository;
import com.trueman.KP_Vacancy.services.AnnouncmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8092")
@RestController
@RequestMapping("/api_announcements")
public class AnnouncementController {

    @Autowired
    private AnnouncmentService announcmentService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AnnouncementRepository announcementRepository;

    public AnnouncementController(AnnouncmentService announcmentService, UserRepository userRepository, AnnouncementRepository announcementRepository) {
        this.announcmentService = announcmentService;
        this.userRepository = userRepository;
        this.announcementRepository = announcementRepository;
    }

    @GetMapping("/announcements")
    public ResponseEntity<List<Announcement>> getAllAnnouncements()
    {
        try
        {
            List<Announcement> announcements = new ArrayList<Announcement>();
            announcements = announcmentService.getAllAnnouncements();

            if (announcements.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(announcements,HttpStatus.OK);
        }
        catch (Exception exception)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/announcements_status_ok")
    public ResponseEntity<List<Announcement>> getAllAnnouncementsStatusOK()
    {
        try
        {
            List<Announcement> announcements_status_ok = new ArrayList<Announcement>();
            announcements_status_ok = announcmentService.getAllAnnouncements_Status_OK();

            if (announcements_status_ok.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(announcements_status_ok,HttpStatus.OK);
        }
        catch (Exception exception)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/my_announcements/{userId}")
    public ResponseEntity<List<Announcement>> getAllMy_announcements(@PathVariable("userId") Long userId)
    {

        List<Announcement> announcement = new ArrayList<Announcement>();
        announcement = announcementRepository.findByUserId(userId);

        return new ResponseEntity<>(announcement,HttpStatus.OK);

    }

    @PostMapping("/addAnnouncements/{id}")
    public ResponseEntity<String> createAnnouncementUser(@RequestBody Announcement announcement, @PathVariable("id") Long id)
    {
        Announcement createdAnnouncement = announcmentService.createAnnouncement(announcement, id);
        return ResponseEntity.ok("Объявление успешно создано!");
    }

    @GetMapping("/announcement/{id}")
    public ResponseEntity<Announcement> getAnnouncementById(@PathVariable Long id) {
        Announcement announcement = announcementRepository.findById(id).orElse(null);
        if (announcement != null) {
            return new ResponseEntity<>(announcement, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/moder/{id}")
    public ResponseEntity<String> moderData(@PathVariable("id") Long id)
    {
        Announcement announcement = announcementRepository.findAnnouncementById(id);

        announcement.setContract_status("Опубликовано!");
        announcementRepository.save(announcement);

        return ResponseEntity.ok("Объявление успешно модерировано!");
    }
    @PutMapping("/block/{id}")
    public ResponseEntity<String> blockData(@PathVariable("id") Long id)
    {
        Announcement announcement = announcementRepository.findAnnouncementById(id);

        announcement.setContract_status("Заблокировано!");
        announcementRepository.save(announcement);

        return ResponseEntity.ok("Объявление успешно заблокировано!");
    }
    //@PostMapping("/AnnouncementsCheck_ok")
    //public ResponseEntity<String> checkAnnouncementOk(@RequestBody Announcement announcement)
    //{
      //  Announcement createdAnnouncement = announcmentService.createAnnouncement(announcement);
       // return ResponseEntity.ok("Объявление успешно создано!");
   // }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateAnnouncement(@PathVariable("id") Long id, @RequestBody Announcement announcement) {
        Announcement updatedAnnouncement = announcmentService.updateAnnouncement(id, announcement);
        if (updatedAnnouncement != null) {
            return ResponseEntity.ok("Объявление успешно обновлено!");
        } else {
            return ResponseEntity.ok("Объявление не найдено!");
        }
    }

    @DeleteMapping("/delete/{announcementId}")
    public ResponseEntity<String> deleteAnnouncement(@PathVariable("announcementId") Long announcementId) {
        announcmentService.deleteAnnouncement(announcementId);
        return ResponseEntity.ok("Объявление успешно удалено!");
    }
}
