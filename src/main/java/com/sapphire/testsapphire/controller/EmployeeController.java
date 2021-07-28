package com.sapphire.testsapphire.controller;

import com.sapphire.testsapphire.dto.EmployeeDTO;
import com.sapphire.testsapphire.dto.StatusDTO;
import com.sapphire.testsapphire.entity.EmployeeEntity;
import com.sapphire.testsapphire.service.EmployeeService;
import com.sapphire.testsapphire.transformer.EmployeeTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @PostMapping(value = "/create") //, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<StatusDTO> create(@ModelAttribute EmployeeDTO employeeDTO) { //@ModelAttribute UserDTO userDTO, @ModelAttribute("file") MultipartFile file) {
        try {
            EmployeeEntity employeeEntity= EmployeeTransformer.getEmployeeEntity(employeeDTO);
            employeeEntity.setStatus(true);
            employeeService.create(employeeEntity);
            return new ResponseEntity<>(new StatusDTO(1, "Employee Added Successfully ",EmployeeTransformer.getEmployeeDTO(employeeEntity)), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new StatusDTO(0, "Exception occurred! "+e), HttpStatus.OK);
        }
    }
    @PostMapping(value = "/update")
    public ResponseEntity<StatusDTO> update(@ModelAttribute EmployeeDTO employeeDTO) { //@ModelAttribute UserDTO userDTO, @ModelAttribute("file") MultipartFile file) {
        try {
            EmployeeEntity employeeEntity=employeeService.findById(Long.parseLong(employeeDTO.getId()));
            if (employeeEntity== null) {
                return new ResponseEntity<>(new StatusDTO(0, "Employee not found!"), HttpStatus.NOT_FOUND);
            }
            employeeEntity= EmployeeTransformer.getEmployeeEntity(employeeDTO);
            employeeEntity.setStatus(true);
            employeeService.update(employeeEntity);
            return new ResponseEntity<>(new StatusDTO(1, "Employee Updated Successfully ",EmployeeTransformer.getEmployeeDTO(employeeEntity)), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new StatusDTO(0, "Exception occurred! "+e), HttpStatus.OK);
        }
    }
    @GetMapping(value = "/view/{id}")
    public ResponseEntity<EmployeeDTO> viewById(@PathVariable Long id) throws IOException {
        EmployeeEntity employeeEntity;
        EmployeeDTO employeeDTO = null;
        try {
            employeeEntity = employeeService.findById(id);
            if (employeeEntity != null) {
                employeeDTO = EmployeeTransformer.getEmployeeDTO(employeeEntity);
                return new ResponseEntity<>(employeeDTO, HttpStatus.OK);
            }
            else{
                return new ResponseEntity("Employee not found", HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity("Exception occurred!", HttpStatus.NOT_FOUND);
        }

    }
    @GetMapping(value = "/delete/{id}")
    public ResponseEntity<StatusDTO> delete(@PathVariable Long id) {
        try {
            EmployeeEntity employeeEntity = employeeService.findById(id);

            if (employeeEntity == null) {
                return new ResponseEntity<StatusDTO>(new StatusDTO(1, "Employee not found!"), HttpStatus.NOT_FOUND);
            } else {
                employeeService.delete(employeeEntity);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new StatusDTO(0, "Exception occurred!\n" + e.getMessage()), HttpStatus.OK);

        }
        return new ResponseEntity<>(new StatusDTO(1, "Employee deleted Successfully"), HttpStatus.OK);
    }

    @GetMapping(value = "/getAll")
    public List<EmployeeDTO> getAll() {
        List<EmployeeEntity> employeeEntities =employeeService.findAll();
        return EmployeeTransformer.getEmployeeDTOs(employeeEntities);
    }

}
