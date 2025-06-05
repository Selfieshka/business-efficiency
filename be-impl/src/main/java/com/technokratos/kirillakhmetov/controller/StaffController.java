package com.technokratos.kirillakhmetov.controller;

import com.technokratos.kirillakhmetov.dto.OwnerDto;
import com.technokratos.kirillakhmetov.form.EmployeeForm;
import com.technokratos.kirillakhmetov.service.OwnerService;
import com.technokratos.kirillakhmetov.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class StaffController {
    private final StaffService staffService;
    private final OwnerService ownerService;

    @GetMapping("/staff")
    public String staff(Model model) {
        OwnerDto owner = ownerService.getProfileInfo("kirill@gmail.com");
        model.addAttribute("owner", owner);
        staffService.getStaffByOwnerId(100000L);
        model.addAttribute("staff", staffService.getStaffByOwnerId(100000L));
        return "staff";
    }

    @PostMapping("/staff")
    public String saveEmployee(@ModelAttribute("employeeForm") EmployeeForm employeeForm) {
        employeeForm.setOwnerId(100000L);
        staffService.saveEmployee(employeeForm);
        return "redirect:/staff";
    }

    @DeleteMapping("/staff/{employeeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable Long employeeId) {
        staffService.deleteEmployeeById(employeeId);
    }
}
