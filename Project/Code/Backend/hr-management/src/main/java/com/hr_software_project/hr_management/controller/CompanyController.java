package com.hr_software_project.hr_management.controller;

import com.hr_software_project.hr_management.dto.CreateCompanyRequestDTO;
import com.hr_software_project.hr_management.dto.CreateUserRequestDTO;
import com.hr_software_project.hr_management.entity.CompanyDO;
import com.hr_software_project.hr_management.entity.UserDO;
import com.hr_software_project.hr_management.service.CompanyService;
import com.hr_software_project.hr_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping("/getCompanyDetails")
    public CompanyDO getCompanyDetails() {
        return companyService.getCompanyDetails();
    }

    @PostMapping("/createCompany")
    public CompanyDO createCompany(
            @RequestBody CreateCompanyRequestDTO req) {
        return companyService.createCompany(req);
    }

    @PutMapping("/updateCompany")
    public CompanyDO updateCompany(
            @RequestBody CompanyDO req,
            @RequestParam Long currentUserId) {
        return companyService.update(req, currentUserId);
    }

    @DeleteMapping("/deleteCompany")
    public void deleteUser(
            @RequestParam Long id,
            @RequestParam Long currentUserId) {
        companyService.delete(id, currentUserId);
    }


}
