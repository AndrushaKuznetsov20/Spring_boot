package com.trueman.KP_Vacancy.controllers;

import com.trueman.KP_Vacancy.models.Announcement;
import com.trueman.KP_Vacancy.models.Response;
import com.trueman.KP_Vacancy.repositories.ResponseRepository;
import com.trueman.KP_Vacancy.services.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8092")
@RestController
@RequestMapping("/api_responses")
public class ResponseController {
    @Autowired
    private ResponseService responseService;

    @Autowired
    private ResponseRepository responseRepository;

    public ResponseController(ResponseService responseService) {
        this.responseService = responseService;
    }


    @GetMapping("/responses")
    public ResponseEntity<List<Response>> getAllResponses()
    {
        try
        {
            List<Response> responses = new ArrayList<Response>();
            responses = responseService.getAllResponses();

            if (responses.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(responses,HttpStatus.OK);
        }
        catch (Exception exception)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/my_responses/{userId}")
    public ResponseEntity<List<Response>> getAllMy_responses(@PathVariable("userId") Long userId)
    {

        List<Response> responses = new ArrayList<Response>();
        responses = responseRepository.findByUserId(userId);
        return new ResponseEntity<>(responses, HttpStatus.OK);

    }

    @PostMapping("/addResponses/{announcementId}/{user_responseId}")
    public ResponseEntity<String> createResponse(@PathVariable("announcementId") Long announcementId, @PathVariable("user_responseId") Long user_responseId)
    {

        List<Response> existingResponses = responseRepository.findByAnnouncementIdAndUser_responseId(announcementId, user_responseId);
        if(existingResponses.isEmpty())
        {
            responseService.createResponse(announcementId,user_responseId);
            return ResponseEntity.ok("Отклик успешно оставлен!");
        }
        else
        {
            return ResponseEntity.ok("Вы уже оставили отклик на данное объявление !");
        }
    }

    @DeleteMapping("/delete/{announcementId}")
    public ResponseEntity<String> deleteResponse(@PathVariable("announcementId") Long announcementId) {
        responseService.deleteResponse(announcementId);
        return ResponseEntity.ok("Объявление удалено из избранных!");
    }
}
