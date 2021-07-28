package com.sapphire.testsapphire.serviceImpl;

import com.sapphire.testsapphire.entity.UserEntity;
import com.sapphire.testsapphire.repository.UserRepository;
import com.sapphire.testsapphire.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserEntity findById(Long id) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        return null;
    }

    @Override
    public UserEntity findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public UserEntity findByMobileNo(String mobileNo) {
        return userRepository.findByMobileNo(mobileNo);
    }

    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserEntity findByPassword(String password) {
        return userRepository.findByPassword(password);
    }

    @Override
    public UserEntity findByUserNameAndPassword(String userName, String password) {
        return userRepository.findByUserNameAndPassword(userName, password);
    }

    @Override
    public List<UserEntity> findAll() {
        List<UserEntity> userList = (List<UserEntity>) userRepository.findAll();
        return userList;
    }

    @Override
    public UserEntity create(UserEntity user) {
        UserEntity savedUser = userRepository.save(user);
        return savedUser;
    }

    @Override
    public UserEntity delete(UserEntity user) {
        if (user.getId() != null) {
            user.setStatus(false);
            userRepository.save(user);
            return user;
        }
        return null;
    }

    @Override
    public UserEntity update(UserEntity user) {
        if (user.getId() != null) {
            UserEntity persisted = findById(user.getId());
            if (persisted == null) {
                return null;
            }
            UserEntity updated = userRepository.save(user);
            return updated;
        }
        return null;
    }

    @Override
    public Page<UserEntity> findAllByFilterWithPaging(Specification<UserEntity> specification, Pageable pageable) {
        return userRepository.findAll(specification, pageable);
    }

}
