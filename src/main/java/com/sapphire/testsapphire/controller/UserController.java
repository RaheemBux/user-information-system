package com.sapphire.testsapphire.controller;

import com.sapphire.testsapphire.dto.PageDTO;
import com.sapphire.testsapphire.dto.StatusDTO;
import com.sapphire.testsapphire.dto.UserDTO;
import com.sapphire.testsapphire.entity.UserEntity;
import com.sapphire.testsapphire.searchFilters.UserFilter;
import com.sapphire.testsapphire.service.UserService;
import com.sapphire.testsapphire.transformer.UserTransformer;
import com.sapphire.testsapphire.util.PaginationUtil;
import com.sapphire.testsapphire.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE) //, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<StatusDTO> createUser(@ModelAttribute UserDTO userDTO, @ModelAttribute("file") MultipartFile file) { //@ModelAttribute UserDTO userDTO, @ModelAttribute("file") MultipartFile file) {
        try {
            boolean error = false;
            String errorMsg = "";

            UserEntity oldUser = userService.findByUserName(userDTO.getUserName());
            if (oldUser != null) {
                if (oldUser.getStatus()) {
                    error = true;
                    errorMsg = userDTO.getUserName() + " userName already exists! ";
                }
            }
            oldUser = userService.findByEmail(userDTO.getEmail());
            if (oldUser != null) {
                error = true;
                errorMsg += userDTO.getEmail() + " emailId already exists! ";
            }
            oldUser = userService.findByMobileNo(userDTO.getMobileNo().toUpperCase());
            if (oldUser != null) {
                error = true;
                errorMsg += userDTO.getMobileNo() + " mobile# already exists! ";
            }
            if (error) {
                return new ResponseEntity<>(new StatusDTO(0, errorMsg), HttpStatus.OK);
            }
            if(!(userDTO.getEmail().contains("@"))){
                return new ResponseEntity<>(new StatusDTO(1, "Incorrect Email"), HttpStatus.OK);
            }

            UserEntity user = UserTransformer.getUserEntity(userDTO);
            user.setStatus(true);
            userService.create(user);
            return new ResponseEntity<>(new StatusDTO(1, "User Added Successfully ",UserTransformer.getUserDTO(user)), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new StatusDTO(0, "Exception occurred! "+e), HttpStatus.OK);
        }
    }
    @PostMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE) //, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<StatusDTO> updateUser(@ModelAttribute UserDTO userDTO,@ModelAttribute("file") MultipartFile file) { //, @ModelAttribute("file") MultipartFile file) {
        try {
            boolean error = false;
            String errorMsg = "";
            UserEntity oldUser;
            UserEntity user;
            user=userService.findById(Long.parseLong(userDTO.getId()));
            if (user== null) {
                return new ResponseEntity<>(new StatusDTO(0, "User not found!"), HttpStatus.NOT_FOUND);
            } else {
                if (user.getUserName().equals(userDTO.getUserName()) && !(user.getUserName().equalsIgnoreCase(userDTO.getUserName()))) {
                    oldUser = userService.findByUserName(userDTO.getUserName());
                    if (oldUser != null) {
                        error = true;
                        errorMsg += userDTO.getUserName() + " userName already exists! ";
                    }
                }
                if (user.getEmail().equals(userDTO.getEmail()) && !(user.getEmail().equalsIgnoreCase(userDTO.getEmail()))) {
                    oldUser = userService.findByEmail(userDTO.getEmail());
                    if (oldUser != null) {
                        error = true;
                        errorMsg += userDTO.getEmail() + " emailId already exists! ";
                    }
                }
                if (user.getMobileNo().equals(userDTO.getMobileNo()) && !(user.getMobileNo().equalsIgnoreCase(userDTO.getMobileNo()))) {
                    oldUser = userService.findByMobileNo(userDTO.getMobileNo());
                    if (oldUser != null) {
                        error = true;
                        errorMsg += userDTO.getMobileNo() + " mobile# already exists! ";
                    }
                }
                if (error) {
                    return new ResponseEntity<>(new StatusDTO(0, errorMsg), HttpStatus.OK);
                }
                user = UserTransformer.getUserEntity(userDTO);
                user.setStatus(true);
                userService.update(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new StatusDTO(0, "Exception occurred! "+e), HttpStatus.OK);
        }
        return new ResponseEntity<>(new StatusDTO(1, "User updated Successfully"), HttpStatus.OK);
    }
    @GetMapping(value = "/view/{id}")
    public ResponseEntity<UserDTO> viewById(@PathVariable Long id) throws IOException {
        UserEntity user;
        UserDTO userDTO = null;
        try {
            user = userService.findById(id);
            if (user != null) {
                userDTO = UserTransformer.getUserDTO(user);
                return new ResponseEntity<>(userDTO, HttpStatus.OK);
            }
            else{
                return new ResponseEntity("User not found", HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity("Exception occurred!", HttpStatus.NOT_FOUND);
        }

    }
    @GetMapping(value = "/delete/{id}")
    public ResponseEntity<StatusDTO> deleteUser(@PathVariable Long id) {
        try {
            UserEntity user = userService.findById(id);

            if (user == null) {
                return new ResponseEntity<StatusDTO>(new StatusDTO(1, "User not found!"), HttpStatus.NOT_FOUND);
            } else {
                userService.delete(user);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new StatusDTO(0, "Exception occurred!\n" + e.getMessage()), HttpStatus.OK);

        }
        return new ResponseEntity<>(new StatusDTO(1, "User deleted Successfully"), HttpStatus.OK);
    }

    @GetMapping(value = "/getAll")
    public List<UserDTO> getAll() {
        List<UserEntity> userEntities =userService.findAll();
        return UserTransformer.getUserDTOs(userEntities);
    }

    @PostMapping(value = "/views")
    public PageDTO getAll(UserFilter filter, @ModelAttribute PaginationUtil paginationUtil) {
        Map<String, String> params=new HashMap<>();
        params.put("page",paginationUtil.getCurrentPage().toString());
        params.put("itemsPerPage",paginationUtil.getItemsPerPages().toString());
        params.put("sortBy",paginationUtil.getSortBy());
        params.put("direction",paginationUtil.getDirection());
        Page<UserEntity> page = userService.findAllByFilterWithPaging(filter, Utility.createPageRequest(params));
        return new PageDTO(UserTransformer.getUserDTOs(page.getContent()), page.getTotalElements(), page.getTotalPages());
    }

}