package com.hr_software_project.hr_management.controller;

import com.hr_software_project.hr_management.dto.CreateCompanyRequestDTO;
import com.hr_software_project.hr_management.dto.CreateUserRequestDTO;
import com.hr_software_project.hr_management.entity.CompanyDO;
import com.hr_software_project.hr_management.entity.JrxmlDO;
import com.hr_software_project.hr_management.entity.UserDO;
import com.hr_software_project.hr_management.service.CompanyService;
import com.hr_software_project.hr_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    // insert jrxml file
    @PostMapping("/upload")
    public ResponseEntity<String> uploadJrxmlFile(@RequestParam("file") MultipartFile file) {
        try {
            companyService.saveJrxmlFile(file);
            return ResponseEntity.ok("JRXML file uploaded successfully.");
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Failed to upload JRXML file: " + e.getMessage());
        }
    }

    @GetMapping("/getJrxmlFiles")
    public List<JrxmlDO> getJrxmlFiles() {
        return companyService.getJrxmlFiles();
    }

    @DeleteMapping("/deleteJrxmlFile")
    public void deleteJrxmlFile(@RequestParam String fileName) {
        companyService.deleteJrxmlFile(fileName);
    }


}
