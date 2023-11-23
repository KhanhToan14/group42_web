package com.web.recruitment.service;


import com.web.recruitment.api.dto.company.CompanyInsert;
import com.web.recruitment.api.dto.company.CompanyUpdate;
import com.web.recruitment.persistence.dto.Company;
import com.web.recruitment.persistence.mapper.CompanyMapper;
import com.web.recruitment.utils.ImageUtils;
import com.web.recruitment.utils.ValidationUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.web.recruitment.utils.ConstantMessages.*;
import static com.web.recruitment.utils.ValidationUtils.*;

@Slf4j
@Service
public class CompanyServiceImpl implements CompanyService{
    @Resource
    private final CompanyMapper companyMapper;

    public CompanyServiceImpl(CompanyMapper companyMapper) {
        this.companyMapper = companyMapper;
    }

    @Override
    public Map<String, Object> select(int id) throws Exception{
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> subResError = new HashMap<>();
        Map<String, Object> resError = new HashMap<>();
        Company company = companyMapper.select(id);
        if(company == null) {
            subResError.put(ID, ID_NOT_EXIST_ERROR);
            resError.put(MESSAGE, NOT_FOUND_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        } else {
            response.put(COMPANY, company);
            return response;
        }
    }
    @Override
    public Map<String, Object> listCompany(Map<String, Object> filter) throws Exception {
        List<Company> retList;
        Map<String, Object> reqInfo = new HashMap<>();
        int pageSize = (int) filter.get(PAGE_SIZE);
        int currentPage = (int) filter.get(CURRENT_PAGE);
        if (currentPage <= 0) {
            currentPage = 1;
        }
        if (pageSize <= 0) {
            pageSize = 30;
        }
        int total = companyMapper.total(filter);
        int limit = pageSize;
        int offset = pageSize * (currentPage - 1);
        filter.put(LIMIT, limit);
        filter.put(OFFSET, offset);
        String sortBy = (String) filter.get(SORT_BY);
        String sortType = (String) filter.get(SORT_TYPE);
        if (sortBy == null || sortBy.isBlank()) {
            sortBy = "time";
        } else {
            sortBy = sortBy.trim();
            if (!sortBy.equals("name") && !sortBy.equals("time")) {
                sortBy = "time";
            }
        }
        if (sortType == null || sortType.isBlank()) {
            if (sortBy.equals("time")) {
                sortType = "desc";
            } else {
                sortType = "asc";
            }
        } else {
            sortType = sortType.trim();
            if (!sortType.equals("asc") && !sortType.equals("desc")) {
                sortType = "asc";
            }
        }
        filter.replace(SORT_BY, sortBy);
        filter.replace(SORT_TYPE, sortType);
        retList = companyMapper.list(filter);
        reqInfo.put(CURRENT_PAGE, currentPage);
        reqInfo.put(PAGE_SIZE, pageSize);
        reqInfo.put(DATA, retList);
        reqInfo.put(TOTAL, total);
        return reqInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> insert(CompanyInsert companyInsert) throws Exception{
        Map<String, Object> subResError = new HashMap<>();
        Map<String, Object> resError = new HashMap<>();
        String code = companyInsert.getCode();
        if (code == null || code.isBlank()){
            subResError.put(CODE, CODE_MUST_NOT_NULL);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        } else {
            Map<String, Object> map = new HashMap<>();
            map.put(CODE, code);
            if(companyMapper.selectCompanyByCode(map) != 0){
                subResError.put(CODE, CODE_NOT_EXIST_ERROR);
                resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
                resError.put(ERRORS, subResError);
                return resError;
            }
        }
        companyInsert.setName(autoCorrectFormatName(companyInsert.getName()));
        String name = companyInsert.getName();
        if(name == null || name.isBlank()){
            subResError.put(NAME, NAME_MUST_NOT_NULL);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        }
        String description = companyInsert.getDescription();
        if(description != null && !description.trim().equals("")){
            companyInsert.setDescription(autoCorrectFormatName(description));
        }else {
            companyInsert.setDescription(null);
        }
        String website = companyInsert.getWebsite();
        if (website != null ){
            if(validateWebsite(website)){
                companyInsert.setWebsite(autoCorrectFormatName(website));
                Map<String, Object> map = new HashMap<>();
                map.put(WEBSITE, companyInsert.getWebsite());
                if(companyMapper.selectCompanyByWebsite(map) != 0){
                    subResError.put(WEBSITE, WEBSITE_NOT_EXIST_ERROR);
                    resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
                    resError.put(ERRORS, subResError);
                    return resError;
                }
            } else{
                subResError.put(WEBSITE, WEBSITE_INVALID);
                resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
                resError.put(ERRORS, subResError);
                return resError;
            }

        }


        /*String logo = companyInsert.getLogo();
        if (logo == null || logo.isBlank()) {
            companyInsert.setLogo(null);
        } else {
            // validate logo
            if (!ValidationUtils.validateImageBase64(logo)){
                subResError.put(LOGO, LOGO_INVALID_ERROR);
                resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
                resError.put(ERRORS, subResError);
                return resError;
            } else if (!ValidationUtils.validateImageSize(logo)){
                subResError.put(LOGO, LOGO_INVALID_SIZE_ERROR);
                resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
                resError.put(ERRORS, subResError);
                return resError;
            } else {
                try {
                    companyInsert.setLogo(ImageUtils.resize(logo));
                } catch (IOException ex) {
                    resError.put(ERRORS, SOME_THING_WENT_WRONG_MESSAGE);
                }
            }
        }*/
        String phone = companyInsert.getPhone();
        if(phone == null || phone.isBlank()){
            subResError.put(PHONE, PHONE_NOT_NULL);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        } else {
            companyInsert.setPhone(validateVietnamesePhoneNumber(phone));
            if(companyInsert.getPhone() == null){
                subResError.put(PHONE, PHONE_INVALID);
                resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
                resError.put(ERRORS, subResError);
                return resError;
            }
        }
        String email = companyInsert.getEmail();
        if(email == null || email.isBlank()){
            subResError.put(EMAIL, EMAIL_NOT_NULL);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        } else {
            if(!validateEmail(email)){
                subResError.put(EMAIL, EMAIL_INVALID);
                resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
                resError.put(ERRORS, subResError);
                return resError;
            }
        }
        companyMapper.insert(companyInsert);
        resError.put(MESSAGE, SUCCESS_INSERT_COMPANY);
        return resError;
    }

    @Override
    public Map<String, Object> update(CompanyUpdate companyUpdate) throws Exception{
        Map<String, Object> subResError = new HashMap<>();
        Map<String, Object> resError = new HashMap<>();
        if(companyUpdate.getId() == null){
            subResError.put(ID, ID_MUST_NOT_NULL);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        }
        if(companyMapper.select(companyUpdate.getId()) == null){
            subResError.put(ID, ID_NOT_EXIST_ERROR);
            resError.put(MESSAGE, NOT_FOUND_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        }
        String code = companyUpdate.getCode();
        if (code == null || code.isBlank()){
            subResError.put(CODE, CODE_MUST_NOT_NULL);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        } else {
            Map<String, Object> map = new HashMap<>();
            map.put(CODE, code);
            if(companyMapper.selectCompanyByCode(map) != 0){
                subResError.put(CODE, CODE_NOT_EXIST_ERROR);
                resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
                resError.put(ERRORS, subResError);
                return resError;
            }
        }
        companyUpdate.setName(autoCorrectFormatName(companyUpdate.getName()));
        String name = companyUpdate.getName();
        if(name == null || name.isBlank()){
            subResError.put(NAME, NAME_MUST_NOT_NULL);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        }
        String description = companyUpdate.getDescription();
        if(description != null && !description.trim().equals("")){
            companyUpdate.setDescription(autoCorrectFormatName(description));
        }else {
            companyUpdate.setDescription(null);
        }
        String website = companyUpdate.getWebsite();
        if (website != null ){
            if(validateWebsite(website)){
                companyUpdate.setWebsite(autoCorrectFormatName(website));
            } else{
                subResError.put(WEBSITE, WEBSITE_INVALID);
                resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
                resError.put(ERRORS, subResError);
                return resError;
            }
        }
        String phone = companyUpdate.getPhone();
        companyUpdate.setPhone(validateVietnamesePhoneNumber(phone));
        companyMapper.update(companyUpdate);
        resError.put(MESSAGE, SUCCESS_UPDATE_COMPANY);
        return resError;
    }

    @Override
    public Map<String, Object> delete (int id) throws Exception{
        Map<String, Object> subResError = new HashMap<>();
        Map<String, Object> resError = new HashMap<>();
        if(companyMapper.select(id) == null){
            subResError.put(ID, ID_NOT_EXIST_ERROR);
            resError.put(MESSAGE, NOT_FOUND_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        }
        companyMapper.delete(id);
        resError.put(MESSAGE, SUCCESS_DELETE_COMPANY);
        return resError;
    }
    @Override
    public Map<String, Object> deleteChoice(List<Integer> ids) throws Exception{
        Map<String, Object> subResError = new HashMap<>();
        Map<String, Object> resError = new HashMap<>();
        ArrayList<Integer> idsDeleteSuccess = new ArrayList<>();
        ArrayList<Integer> idsDeleteNotSuccess = new ArrayList<>();
        int validId;
        int idsSize = ids.size();
        if(idsSize > 0){
            for(Integer id : ids){
                if(companyMapper.select(id) == null){
                    idsDeleteNotSuccess.add(id);
                } else {
                    validId = id;
                    idsDeleteSuccess.add(validId);
                    companyMapper.delete(id);
                }
            }
        } else {
            subResError.put(DELETE_IDS, DELETE_IDS_MUST_NOT_NULL_OR_EMPTY);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        }
        if(idsDeleteSuccess.size() > 0){
            if (idsDeleteNotSuccess.size() > 0){
                resError.put(LIST_ID_CAN_NOT_DELETE, idsDeleteNotSuccess);
                resError.put(LIST_ID_DELETED, idsDeleteSuccess);
                resError.put(MESSAGE, SUCCESS_DELETE_COMPANY);
            } else{
                resError.put(MESSAGE, SUCCESS_DELETE_COMPANY);
            }
        } else {
            subResError.put(DELETE_IDS, IDS_INVALID);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
        }
        return resError;
    }
}
