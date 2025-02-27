package com.hr_software_project.hr_management.method;

import com.hr_software_project.hr_management.dto.CreateCompanyRequestDTO;
import com.hr_software_project.hr_management.entity.CompanyDO;
import com.hr_software_project.hr_management.entity.UserDO;
import com.hr_software_project.hr_management.enums.Role;
import com.hr_software_project.hr_management.error.ServiceErrorCodes;
import com.hr_software_project.hr_management.error.ServiceException;
import com.hr_software_project.hr_management.repository.CompanyRepository;
import com.hr_software_project.hr_management.repository.UserRepository;
import com.hr_software_project.hr_management.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Transactional
@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CompanyRepository companyRepository;


    public CompanyDO getCompanyDetails(){

        List<CompanyDO> companies = companyRepository.findAll();

        if(companies.isEmpty()){
            throw new ServiceException(ServiceErrorCodes.COMPANY_NOT_FOUND);
        }

        return companies.getFirst();
    }

    public CompanyDO createCompany(CreateCompanyRequestDTO req) {
        UserDO currentUser = userRepository.findById(req.getCurrentUserId())
                .orElseThrow(() -> new ServiceException(ServiceErrorCodes.USER_NOT_FOUND));

        if (currentUser.getRole().equals(Role.ADMIN)) {
            CompanyDO company = new CompanyDO();
            company.setCompanyName(req.getCompanyName());
            company.setCompanyRegistrationNumber(req.getCompanyRegistrationNumber());
            company.setCompanyNumber(req.getCompanyNumber());

            company.setCompanyAddress(req.getCompanyAddress());
            company.setCompanyCity(req.getCompanyCity());
            company.setCompanyState(req.getCompanyState());
            company.setCompanyCountry(req.getCompanyCountry());
            company.setCompanyPostalCode(req.getCompanyPostalCode());

            company.setCompanyPhoneNumber(req.getCompanyPhoneNumber());
            company.setCompanyWebsite(req.getCompanyWebsite());
            company.setCompanyFaxNumber(req.getCompanyFaxNumber());
            company.setCompanyEmail(req.getCompanyEmail());

            company.setCutOffDate(req.getCutOffDate());

            return companyRepository.save(company);

        } else {
            throw new ServiceException(ServiceErrorCodes.UNAUTHORIZED);
        }
    }

    public CompanyDO update(CompanyDO req, Long currentUserId) {
        UserDO currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ServiceException(ServiceErrorCodes.USER_NOT_FOUND));

        if (currentUser.getRole().equals(Role.ADMIN)) {
            return companyRepository.save(req);
        }
        throw new ServiceException(ServiceErrorCodes.UNAUTHORIZED);
    }

    public void delete(Long id, Long currentUserId) {
        UserDO currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ServiceException(ServiceErrorCodes.USER_NOT_FOUND));

        if (currentUser.getRole().equals(Role.ADMIN)) {
            companyRepository.deleteById(id);
        }
        throw new ServiceException(ServiceErrorCodes.UNAUTHORIZED);
    }

}
