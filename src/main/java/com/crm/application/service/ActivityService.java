package com.crm.application.service;

import com.crm.application.model.Activity;

public interface ActivityService {

    Activity getActivityById(Long id);

    void saveActivity(Long id, Long id2, Activity activity);

    void deleteActivity(Long id);

}
