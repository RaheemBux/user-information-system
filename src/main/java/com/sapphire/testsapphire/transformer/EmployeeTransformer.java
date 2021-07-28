package com.sapphire.testsapphire.transformer;

import com.sapphire.testsapphire.dto.EmployeeDTO;
import com.sapphire.testsapphire.entity.EmployeeEntity;
import com.sapphire.testsapphire.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;

public class EmployeeTransformer {

    public static EmployeeDTO getEmployeeDTO(EmployeeEntity employeeEntity){
        EmployeeDTO employeeDTO=new EmployeeDTO();
        if(employeeEntity.getId()!=null){
            employeeDTO.setId(employeeEntity.getId().toString());
        }
        if(employeeEntity.getName()!=null){
            employeeDTO.setName(employeeEntity.getName());
        }
        if(employeeEntity.getDesignation()!=null){
            employeeDTO.setDesignation(employeeEntity.getDesignation());
        }
        if(employeeEntity.getEmail()!=null){
            employeeDTO.setEmail(employeeEntity.getEmail());
        }
        if(employeeEntity.getSalary()!=null){
            employeeDTO.setSalary(employeeEntity.getSalary().toString());
        }
        // five columns
        if(employeeEntity.getCreatedBy()!=null){
            employeeDTO.setCreatedBy(employeeEntity.getCreatedBy().toString());
        }
        if(employeeEntity.getModifiedBy()!=null){
            employeeDTO.setModifiedBy(employeeEntity.getModifiedBy().toString());
        }
        if(employeeEntity.getCreatedDate()!=null){
            employeeDTO.setCreatedDate(employeeEntity.getCreatedDate().toString());
        }
        if(employeeEntity.getModifiedDate()!=null){
            employeeDTO.setModifiedDate(employeeEntity.getModifiedDate().toString());
        }
        if(employeeEntity.getStatus()!=null){
            employeeDTO.setStatus(employeeEntity.getStatus().toString());
        }

        return employeeDTO;
    }
    public static EmployeeEntity getEmployeeEntity(EmployeeDTO employeeDTO){
        EmployeeEntity employeeEntity=new EmployeeEntity();
        if(employeeDTO.getId()!=null){
            employeeEntity.setId(Long.parseLong(employeeDTO.getId()));
        }
        if(employeeDTO.getName()!=null){
            employeeEntity.setName(employeeDTO.getName());
        }
        if(employeeDTO.getDesignation()!=null){
            employeeEntity.setDesignation(employeeDTO.getDesignation());
        }
        if(employeeDTO.getEmail()!=null){
            employeeEntity.setEmail(employeeDTO.getEmail());
        }
        if(employeeDTO.getSalary()!=null){
            employeeEntity.setSalary(Double.parseDouble(employeeDTO.getSalary()));
        }
        // five columns
        if(employeeDTO.getCreatedBy()!=null){
            employeeEntity.setCreatedBy(Long.parseLong(employeeDTO.getCreatedBy()));
        }
        if(employeeDTO.getModifiedBy()!=null){
            employeeEntity.setModifiedBy(Long.parseLong(employeeDTO.getModifiedBy()));
        }
        employeeEntity.setCreatedDate(CommonUtil.getCurrentTimestamp());
        employeeEntity.setModifiedDate(CommonUtil.getCurrentTimestamp());

        if(employeeDTO.getStatus()!=null){
            employeeEntity.setStatus(Boolean.parseBoolean(employeeDTO.getStatus()));
        }
        return employeeEntity;
    }
    public static List<EmployeeDTO> getEmployeeDTOs(List<EmployeeEntity> employeeEntities) {
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        employeeEntities.forEach(employeeEntity-> {
            employeeDTOS.add(EmployeeTransformer.getEmployeeDTO(employeeEntity));
        });
        return employeeDTOS;
    }
}
