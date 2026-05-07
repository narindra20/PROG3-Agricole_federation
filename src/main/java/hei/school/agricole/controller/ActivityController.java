package hei.school.agricole.controller;

import hei.school.agricole.dto.ActivityMemberAttendance;
import hei.school.agricole.dto.CreateActivity;
import hei.school.agricole.dto.CreateActivityMemberAttendance;
import hei.school.agricole.entity.Activity;
import hei.school.agricole.service.ActivityService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/collectivites")
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @PostMapping("/{id}/activities")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Activity> addActivities(@PathVariable String id, @RequestBody List<CreateActivity> activities) {
        return activityService.createActivities(id, activities);
    }

    @GetMapping("/{id}/activities")
    public List<Activity> getActivities(@PathVariable String id) {
        return activityService.getActivities(id);
    }

    @PostMapping("/{id}/activities/{activityId}/attendance")
    public List<ActivityMemberAttendance> recordAttendance(
            @PathVariable String id,
            @PathVariable String activityId,
            @RequestBody List<CreateActivityMemberAttendance> records) {
        return activityService.recordAttendance(id, activityId, records);
    }

    @GetMapping("/{id}/activities/{activityId}/attendance")
    public List<ActivityMemberAttendance> getAttendance(
            @PathVariable String id,
            @PathVariable String activityId) {
        return activityService.getAttendance(id, activityId);
    }
}