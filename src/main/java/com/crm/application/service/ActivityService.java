package com.crm.application.service;

import com.crm.application.model.Activity;

import java.util.List;

public interface ActivityService {

    Activity getActivityById(Long id);

    List<Activity> getActivitiesByUserId(Long id);

    List<Activity> getActivitiesByClientId(Long id);

    void saveActivity(Long id, Long id2, Activity activity);

    void deleteActivity(Long id);

}
