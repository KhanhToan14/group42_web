package com.web.recruitment.service;


import com.web.recruitment.api.dto.company.CompanyInsert;
import com.web.recruitment.api.dto.company.CompanyUpdate;

import java.util.List;
import java.util.Map;

public interface CompanyService {
    Map<String, Object> select(int id) throws Exception;

    Map<String, Object> listCompany(Map<String, Object> filter) throws Exception;

    Map<String, Object> insert(CompanyInsert companyInsert) throws Exception;

    Map<String, Object> update(CompanyUpdate companyUpdate) throws Exception;

    Map<String, Object> delete(int id) throws Exception;

    Map<String, Object> deleteChoice(List<Integer> ids) throws Exception;
}
