package com.sapphire.testsapphire.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class EmployeeDTO extends AbstractDTO{

    private String id;
    private String name;
    private String salary;
    private String designation;
    private String email;
}
