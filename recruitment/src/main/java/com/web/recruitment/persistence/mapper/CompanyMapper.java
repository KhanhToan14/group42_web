package com.web.recruitment.persistence.mapper;

import com.web.recruitment.api.dto.company.CompanyInsert;
import com.web.recruitment.api.dto.company.CompanyUpdate;
import com.web.recruitment.persistence.dto.Company;

import java.util.List;
import java.util.Map;

public interface CompanyMapper {
    List<Company> list(Map<String, Object> reqMap) throws Exception;

    Company select(int id) throws Exception;

    int total(Map<String, Object> reqMap) throws Exception;

    int insert(CompanyInsert companyInsert) throws Exception;

    int selectByName(Map<String, Object> reqMap) throws Exception;

    int update(CompanyUpdate companyUpdate) throws Exception;

    int delete(int id) throws Exception;

    int deleteChoice(List<Integer> id) throws Exception;

    int selectCompanyByCode(Map<String, Object> reqMap) throws Exception;

    int selectCompanyByWebsite(Map<String, Object> reqMap) throws Exception;

    int selectCompanyByEmail(Map<String, Object> reqMap) throws Exception;
}
