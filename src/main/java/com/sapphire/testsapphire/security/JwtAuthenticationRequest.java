package com.sapphire.testsapphire.security;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class  JwtAuthenticationRequest implements Serializable {
    private static final long serialVersionUID = -8445943548965154778L;
    private String username;
    private String password;
}
