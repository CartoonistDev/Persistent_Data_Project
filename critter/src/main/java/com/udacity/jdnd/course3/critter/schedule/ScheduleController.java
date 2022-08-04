package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.service.ScheduleServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleServices scheduleServices;
    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        try {
            Schedule schedule = new Schedule();
            schedule.setDate(scheduleDTO.getDate());
            schedule.setActivities(scheduleDTO.getActivities());
            return myScheduleDTO(scheduleServices.saveAllSchedule(schedule, scheduleDTO.getEmployeeIds(), scheduleDTO.getPetIds()));
        } catch (UnsupportedOperationException e){
            throw new UnsupportedOperationException();
        }
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        try {
            List<Schedule> schedules = scheduleServices.getAllSchedules();
            return schedules.stream().map(this::myScheduleDTO).collect(Collectors.toList());
        } catch (UnsupportedOperationException e){
            throw new UnsupportedOperationException();
        }
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        try {
            List<Schedule> schedules = scheduleServices.getPetSchedule(petId);
            return schedules.stream().map(this::myScheduleDTO).collect(Collectors.toList());
        } catch (UnsupportedOperationException e){
            throw new UnsupportedOperationException();
        }    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        try {
            List<Schedule> schedules = scheduleServices.getEmployeeSchedule(employeeId);
            return schedules.stream().map(this::myScheduleDTO).collect(Collectors.toList());
        } catch (UnsupportedOperationException e){
            throw new UnsupportedOperationException();
        }    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        try {
            List<Schedule> schedules = scheduleServices.getCustomerSchedule(customerId);
            return schedules.stream().map(this::myScheduleDTO).collect(Collectors.toList());
        } catch (UnsupportedOperationException e){
            throw new UnsupportedOperationException();
        }
    }

    private ScheduleDTO myScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setId(schedule.getId());
        scheduleDTO.setEmployeeIds(schedule.getEmployees().stream().map(Employee::getEmployeeId).collect(Collectors.toList()));
        scheduleDTO.setPetIds(schedule.getPets().stream().map(Pet::getId).collect(Collectors.toList()));
        scheduleDTO.setDate(schedule.getDate());
        scheduleDTO.setActivities(schedule.getActivities());
        return scheduleDTO;
    }
}
