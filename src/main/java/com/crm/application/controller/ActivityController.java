package com.crm.application.controller;

import com.crm.application.model.Activity;
import com.crm.application.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ActivityController {

    private ActivityService activityService;

    @Autowired
    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @PostMapping(value = "/activity")
    public ResponseEntity createActivity(@RequestBody Activity activity, BindingResult bindingResult, Long clientId, Long userId) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        } else {
            activityService.saveActivity(clientId, userId, activity);
            return new ResponseEntity(HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/activity/{id}")
    public ResponseEntity deleteActivity(@PathVariable("id") long id) {
        activityService.deleteActivity(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "activity/user/{id}")
    public ResponseEntity getActivitiesByUserId(@PathVariable("id") Long id) {
        List<Activity> activities = activityService.getActivitiesByUserId(id);

        if (activities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(activities, HttpStatus.OK);
    }

    @GetMapping(value = "activity/client/{id}")
    public ResponseEntity getActivitiesByClientId(@PathVariable("id") Long id) {
        List<Activity> activities = activityService.getActivitiesByClientId(id);

        if (activities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(activities, HttpStatus.OK);
    }

    @GetMapping(value = "/activity/{id}")
    public ResponseEntity getActivity(@PathVariable("id") Long id) {
        Activity activity = activityService.getActivityById(id);
        if (activity == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.OK);
    }


}
