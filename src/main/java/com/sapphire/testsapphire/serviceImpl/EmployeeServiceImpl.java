package com.sapphire.testsapphire.serviceImpl;

import com.sapphire.testsapphire.entity.EmployeeEntity;
import com.sapphire.testsapphire.repository.EmployeeRepository;
import com.sapphire.testsapphire.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public List<EmployeeEntity> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public EmployeeEntity create(EmployeeEntity employeeEntity) {
        return employeeRepository.save(employeeEntity);
    }

    @Override
    public EmployeeEntity delete(EmployeeEntity employeeEntity) {
        if (employeeEntity.getId() != null) {
            employeeEntity.setStatus(false);
            employeeRepository.save(employeeEntity);
            return employeeEntity;
        }
        return null;
    }

    @Override
    public EmployeeEntity update(EmployeeEntity employeeEntity) {
        if (employeeEntity.getId() != null) {
            EmployeeEntity persisted = findById(employeeEntity.getId());
            if (persisted == null) {
                return null;
            }
            EmployeeEntity updated = employeeRepository.save(employeeEntity);
            return updated;
        }
        return null;
    }

    @Override
    public EmployeeEntity findById(Long id) {
        Optional<EmployeeEntity> optional = employeeRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public Page<EmployeeEntity> findAllByFilterWithPaging(Specification<EmployeeEntity> specification, Pageable pageable) {
        return employeeRepository.findAll(specification, pageable);
    }


}
