package com.sapphire.testsapphire.service;

import com.sapphire.testsapphire.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface UserService {
    UserEntity findById(Long id);
    UserEntity findByUserName(String userName);
    UserEntity findByMobileNo(String mobileNo);
    UserEntity findByEmail(String email);
    UserEntity findByPassword(String password);
    UserEntity findByUserNameAndPassword(String userName, String password);
    List<UserEntity> findAll();
    UserEntity create(UserEntity UserEntity) ;
    UserEntity delete(UserEntity UserEntity);
    UserEntity update(UserEntity UserEntity);
    Page<UserEntity> findAllByFilterWithPaging(Specification<UserEntity> specification, Pageable pageable);

}
