package com.technokratos.kirillakhmetov.controller;

import com.technokratos.kirillakhmetov.form.EmployeeForm;
import com.technokratos.kirillakhmetov.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class StaffController {
    private final StaffService staffService;

    @GetMapping("/staff")
    public String staff() {
        staffService.getStaffByOwnerId(100000L);
        return "staff";
    }

    @PostMapping("/staff")
    public String saveEmployee(@ModelAttribute("employeeForm") EmployeeForm employeeForm) {
        staffService.saveEmployee(employeeForm);
        return "redirect:/staff";
    }

    @DeleteMapping("/staff/{employeeId}")
    public void deleteEmployee(@PathVariable Long employeeId) {
        staffService.deleteEmployeeById(employeeId);
    }
}
