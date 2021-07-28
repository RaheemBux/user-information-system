package com.sapphire.testsapphire.transformer;

import com.sapphire.testsapphire.dto.UserDTO;
import com.sapphire.testsapphire.entity.UserEntity;
import com.sapphire.testsapphire.util.CommonUtil;
import com.sapphire.testsapphire.util.EncoderDecoder;

import java.util.ArrayList;
import java.util.List;

public class UserTransformer {

    public static UserDTO getUserDTO(UserEntity userEntity){
        UserDTO userDTO=new UserDTO();
        if(userEntity.getId()!=null){
            userDTO.setId(userEntity.getId().toString());
        }
        if(userEntity.getUserName()!=null){
            userDTO.setUserName(userEntity.getUserName());
        }
        if(userEntity.getFirstName()!=null){
            userDTO.setFirstName(userEntity.getFirstName());
        }
        if(userEntity.getLastName()!=null){
            userDTO.setLastName(userEntity.getLastName());
        }
        if(userEntity.getEmail()!=null){
            userDTO.setEmail(userEntity.getEmail());
        }
        if(userEntity.getMobileNo()!=null){
            userDTO.setMobileNo(userEntity.getMobileNo());
        }
        if(userEntity.getLoginDate()!=null){
            userDTO.setLoginDate(userEntity.getLoginDate().toString());
        }
        // five columns
        if(userEntity.getCreatedBy()!=null){
            userDTO.setCreatedBy(userEntity.getCreatedBy().toString());
        }
        if(userEntity.getModifiedBy()!=null){
            userDTO.setModifiedBy(userEntity.getModifiedBy().toString());
        }
        if(userEntity.getCreatedDate()!=null){
            userDTO.setCreatedDate(userEntity.getCreatedDate().toString());
        }
        if(userEntity.getModifiedDate()!=null){
            userDTO.setModifiedDate(userEntity.getModifiedDate().toString());
        }
        if(userEntity.getStatus()!=null){
            userDTO.setStatus(userEntity.getStatus().toString());
        }
        return userDTO;
    }
    public static UserEntity getUserEntity(UserDTO userDTO){
        UserEntity userEntity=new UserEntity();

        if(userDTO.getId()!=null){
            userEntity.setId(Long.parseLong(userDTO.getId()));
        }
        if(userDTO.getUserName()!=null){
            userEntity.setUserName(userDTO.getUserName());
        }
        if (userDTO.getPassword() != null && !userDTO.getPassword().trim().equals("")) {
            userEntity.setPassword(EncoderDecoder.getEncryptedSHA1Password(userDTO.getPassword().trim()));
        }
        if(userDTO.getFirstName()!=null){
            userEntity.setFirstName(userDTO.getFirstName());
        }
        if(userDTO.getLastName()!=null){
            userEntity.setLastName(userDTO.getLastName());
        }
        if(userDTO.getEmail()!=null){
            userEntity.setEmail(userDTO.getEmail());
        }
        if(userDTO.getMobileNo()!=null){
            userEntity.setMobileNo(userDTO.getMobileNo());
        }
        userEntity.setLoginDate(CommonUtil.getCurrentTimestamp());
        // five columns
        if(userDTO.getCreatedBy()!=null){
            userEntity.setCreatedBy(Long.parseLong(userDTO.getCreatedBy()));
        }
        if(userDTO.getModifiedBy()!=null){
            userEntity.setModifiedBy(Long.parseLong(userDTO.getModifiedBy()));
        }
        userEntity.setCreatedDate(CommonUtil.getCurrentTimestamp());
        userEntity.setModifiedDate(CommonUtil.getCurrentTimestamp());

        if(userDTO.getStatus()!=null){
            userEntity.setStatus(Boolean.parseBoolean(userDTO.getStatus()));
        }
        return userEntity;
    }
    public static List<UserDTO> getUserDTOs(List<UserEntity> userEntityList) {
        List<UserDTO> userDTOS = new ArrayList<>();
        userEntityList.forEach(userEntity -> {
            userDTOS.add(UserTransformer.getUserDTO(userEntity));
        });
        return userDTOS;
    }
}
