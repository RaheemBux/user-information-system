package com.sapphire.testsapphire.security;

import com.sapphire.testsapphire.entity.UserEntity;
import com.sapphire.testsapphire.repository.UserRepository;
import com.sapphire.testsapphire.util.EncoderDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class UsersAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String loginId = authentication.getName().trim();
        String password = EncoderDecoder.getEncryptedSHA1Password(authentication.getCredentials().toString().trim());

        Authentication auth = null;
        UserEntity umUsers = userRepository.findByUserNameAndPassword(loginId, password);

        if (umUsers != null && umUsers.getStatus() != false) {
            Collection<GrantedAuthority> grantedAuths = null;
            JwtUser appUser = new JwtUser(Long.parseLong(umUsers.getId() + ""), null, null, null, umUsers.getUserName(), umUsers.getPassword(), null, true, null);
            auth = new UsernamePasswordAuthenticationToken(appUser, password, grantedAuths);
            return auth;
        } else {
            return null;
        }

    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
