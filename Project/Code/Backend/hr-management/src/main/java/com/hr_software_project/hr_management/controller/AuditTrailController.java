package com.hr_software_project.hr_management.controller;

import com.hr_software_project.hr_management.dto.CreateAuditTrailRequestDTO;
import com.hr_software_project.hr_management.entity.AuditTrailDO;
import com.hr_software_project.hr_management.service.AuditTrailService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit-trail")

public class AuditTrailController {

    @Autowired
    private AuditTrailService auditTrailService;


    @Operation
    @GetMapping("/getAuditTrailAll")
    public List<AuditTrailDO> getAuditTrailAll(
            @RequestParam Long currentUserId) {
        return auditTrailService.getAuditTrailAll(currentUserId);
    }

    @GetMapping("/getAuditTrailForUser")
    public List<AuditTrailDO> getAuditTrailForUser(
            @RequestParam Long currentUserId,
            @RequestParam Long userId) {
        return auditTrailService.getAuditTrailForUser(currentUserId, userId);
    }

    @PostMapping("/createAuditTrail")
    public AuditTrailDO createAuditTrail(
            @RequestBody CreateAuditTrailRequestDTO req) {
        return auditTrailService.createAuditTrail(req);
    }



}
