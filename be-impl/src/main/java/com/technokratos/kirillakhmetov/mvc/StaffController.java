package com.technokratos.kirillakhmetov.mvc;

import com.technokratos.kirillakhmetov.form.EmployeeForm;
import com.technokratos.kirillakhmetov.service.OwnerService;
import com.technokratos.kirillakhmetov.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/staff")
@RequiredArgsConstructor
public class StaffController {
    private final StaffService staffServiceImpl;
    private final OwnerService ownerServiceImpl;

    @GetMapping
    public String staff(Model model) {
        model.addAttribute("owner", ownerServiceImpl.getProfileInfo("kirill@gmail.com"));
        staffServiceImpl.getStaffByOwnerId(100000L);
        model.addAttribute("staff", staffServiceImpl.getStaffByOwnerId(100000L));
        return "staff";
    }

    @PostMapping
    public String saveEmployee(@ModelAttribute("employeeForm") EmployeeForm employeeForm) {
        employeeForm.setOwnerId(100000L);
        staffServiceImpl.saveEmployee(employeeForm);
        return "redirect:/staff";
    }
}
