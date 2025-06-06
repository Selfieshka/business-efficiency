package com.technokratos.kirillakhmetov.mvc;

import com.technokratos.kirillakhmetov.dto.response.OwnerResponse;
import com.technokratos.kirillakhmetov.form.EmployeeForm;
import com.technokratos.kirillakhmetov.security.UserContextHolder;
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
    private final UserContextHolder userContextHolderImpl;

    @GetMapping
    public String staff(Model model) {
        OwnerResponse owner = ownerServiceImpl.getProfileInfo(userContextHolderImpl
                .getUserIdFromSecurityContext());
        model.addAttribute("owner", owner);
        model.addAttribute("staff", staffServiceImpl.getStaffByOwnerId(userContextHolderImpl
                .getUserIdFromSecurityContext()));
        return "staff";
    }

    @PostMapping
    public String saveEmployee(@ModelAttribute("employeeForm") EmployeeForm employeeForm) {
        staffServiceImpl.saveEmployee(userContextHolderImpl
                .getUserIdFromSecurityContext(), employeeForm);
        return "redirect:/staff";
    }
}
