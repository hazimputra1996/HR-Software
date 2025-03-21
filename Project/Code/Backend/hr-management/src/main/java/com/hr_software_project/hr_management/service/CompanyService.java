package com.hr_software_project.hr_management.service;

import com.hr_software_project.hr_management.dto.CreateCompanyRequestDTO;
import com.hr_software_project.hr_management.dto.CreateUserRequestDTO;
import com.hr_software_project.hr_management.entity.CompanyDO;
import com.hr_software_project.hr_management.entity.JrxmlDO;
import com.hr_software_project.hr_management.entity.UserDO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Service
public interface CompanyService {


    CompanyDO getCompanyDetails();
    CompanyDO createCompany(CreateCompanyRequestDTO req);
    CompanyDO update(CompanyDO req, Long currentUserId);
    void delete(Long id, Long currentUserId);
    void saveJrxmlFile(MultipartFile file) throws IOException;
    List<JrxmlDO> getJrxmlFiles();
    void deleteJrxmlFile(String fileName);
}
