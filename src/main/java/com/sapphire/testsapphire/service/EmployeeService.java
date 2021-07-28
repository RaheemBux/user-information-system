package com.sapphire.testsapphire.service;

import com.sapphire.testsapphire.entity.EmployeeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface EmployeeService {
    List<EmployeeEntity> findAll();
    EmployeeEntity create(EmployeeEntity employeeEntity) ;
    EmployeeEntity delete(EmployeeEntity employeeEntity);
    EmployeeEntity update(EmployeeEntity employeeEntity);
    EmployeeEntity findById(Long id);
    Page<EmployeeEntity> findAllByFilterWithPaging(Specification<EmployeeEntity> specification, Pageable pageable);
}
