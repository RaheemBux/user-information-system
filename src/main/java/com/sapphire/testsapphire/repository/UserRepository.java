package com.sapphire.testsapphire.repository;

import com.sapphire.testsapphire.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {
    UserEntity findByUserName(String userName);
    UserEntity findByPassword(String password);
    UserEntity findByMobileNo(String mobileNo);
    UserEntity findByEmail(String email);
    UserEntity findByUserNameAndPassword(String userName, String password);

}
