package com.sapphire.testsapphire.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserDTO extends AbstractDTO {
    private String id;
    private String userName;
    private String password;
    private String loginDate;
    private String firstName;
    private String lastName;
    private String mobileNo;
    private String email;
}
