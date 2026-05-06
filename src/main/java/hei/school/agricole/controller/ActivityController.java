package hei.school.agricole.controller;

import hei.school.agricole.dto.ActivityMemberAttendance;
import hei.school.agricole.dto.CreateActivity;
import hei.school.agricole.dto.CreateActivityMemberAttendance;
import hei.school.agricole.entity.Activity;
import hei.school.agricole.service.ActivityService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/collectivities")
public class ActivityController {

    private final ActivityService service;

    public ActivityController(ActivityService service) {
        this.service = service;
    }

    @PostMapping("/{id}/activities")
    public List<Activity> createActivities(@PathVariable String id, @RequestBody List<CreateActivity> activities) {
        return service.createActivities(id, activities);
    }

    @GetMapping("/{id}/activities")
    public List<Activity> getActivities(@PathVariable String id) {
        return service.getActivities(id);
    }

    @PostMapping("/{id}/activities/{activityId}/attendance")
    public List<ActivityMemberAttendance> recordAttendance(@PathVariable String id, @PathVariable String activityId, @RequestBody List<CreateActivityMemberAttendance> records) {
        return service.recordAttendance(id, activityId, records);
    }

    @GetMapping("/{id}/activities/{activityId}/attendance")
    public List<ActivityMemberAttendance> getAttendance(@PathVariable String id, @PathVariable String activityId) {
        return service.getAttendance(id, activityId);
    }
}