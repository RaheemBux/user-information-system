package com.sapphire.testsapphire.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Raza Muhammad Dahri on 16/1/2020
 * @project Reinsurance
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
public class StatusDTO {
    private int code;
    private String message;
    private Object entity;
    private ArrayList<String> errorList;
    private List response = new ArrayList();

    public StatusDTO(int code, String message) {
        this.code = code;
        this.message = message;
    }
    public StatusDTO(int code, Object entity) {
        this.code = code;
        this.entity = entity;
    }
    public StatusDTO(int code, String message, Object entity) {
        this.code = code;
        this.message = message;
        this.entity = entity;
    }
    public StatusDTO(int code, String message, List response) {
        this.code = code;
        this.message = message;
        this.response = response;
    }
    public StatusDTO(int code, ArrayList<String> errorList, Object entity) {
        this.code = code;
        this.errorList = errorList;
        this.entity = entity;
    }
    public StatusDTO(int code, ArrayList<String> errorList) {
        this.code = code;
        this.errorList = errorList;
    }
}
