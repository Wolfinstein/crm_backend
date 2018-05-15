package com.crm.application.service.serviceImpl;

import com.crm.application.model.Activity;
import com.crm.application.repository.ActivityRepository;
import com.crm.application.repository.ClientRepository;
import com.crm.application.repository.UserRepository;
import com.crm.application.service.ActivityService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;

    public ActivityServiceImpl(ActivityRepository activityRepository, UserRepository userRepository, ClientRepository clientRepository) {
        this.activityRepository = activityRepository;
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public Activity getActivityById(Long id) {
        return activityRepository.findById(id);
    }

    @Override
    public List<Activity> getActivitiesByUserId(Long id) {
        return activityRepository.findAllByUserId(id);
    }

    @Override
    public List<Activity> getActivitiesByClientId(Long id) {
        return activityRepository.findAllByClientId(id);
    }

    @Override
    public void saveActivity(Long clientId, Long userId, Activity activity) {
        Activity temp = new Activity();
        temp.setType_activity(activity.getType_activity());
        temp.setDescription_activity(activity.getDescription_activity());
        temp.setUser(userRepository.findOne(userId));
        temp.setClient(clientRepository.findOne(clientId));
        activityRepository.save(temp);
    }

    @Override
    public void deleteActivity(Long id) {
        activityRepository.delete(id);
    }

}
