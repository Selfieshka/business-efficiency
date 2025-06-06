package com.technokratos.kirillakhmetov.controller;

import com.technokratos.kirillakhmetov.api.StaffApi;
import com.technokratos.kirillakhmetov.security.UserContextHolder;
import com.technokratos.kirillakhmetov.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StaffControllerApi implements StaffApi {
    private final StaffService staffServiceImpl;
    private final UserContextHolder userContextHolderImpl;

    @Override
    public void deleteEmployee(Long employeeId) {
        staffServiceImpl.deleteEmployeeById(userContextHolderImpl
                .getUserIdFromSecurityContext(), employeeId);
    }
}
