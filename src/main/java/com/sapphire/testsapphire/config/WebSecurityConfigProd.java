package com.sapphire.testsapphire.config;

import com.sapphire.testsapphire.security.JwtAuthenticationTokenFilter;
import com.sapphire.testsapphire.security.UsersAuthenticationProvider;
import com.sapphire.testsapphire.security.JwtUserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Conditional(ProdProfileCondition.class)
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfigProd extends WebSecurityConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSecurityConfigProd.class);

    @Autowired
    private UsersAuthenticationProvider usersAuthenticationProvider;

    @Autowired
    private JwtUserDetailsServiceImpl jwtUserDetailsService;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationTokenFilter();
    }
    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        LOGGER.info("In Production Security mode");
        authenticationManagerBuilder
                .authenticationProvider(usersAuthenticationProvider)
                .userDetailsService(this.jwtUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // we don't need CSRF because our token is invulnerable
                .cors().and().csrf().disable()

                //.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()

                // don't create session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // allow anonymous resource requests
                .antMatchers(
                        HttpMethod.GET,
                        "/",
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/**/*.png",
                        "/*.png",
                        "/**/*.jpeg",
                        "/*.jpeg",
                        "/**/*.jpg",
                        "/*.jpg",
                        "/**/*.gif",
                        "/*.gif",
                        "/*.mp3",
                        "/**/*.mp3",
                        "/*.3gp",
                        "/**/*.3gp",
                        "/*.wav",
                        "/**/*.wav",
                        "/**/*.mp4",
                        "/*.xls",
                        "/**/*.xls",
                        "/*.csv",
                        "/**/*.csv",
                        "/*.pdf",
                        "/**/*.pdf"
                ).permitAll()
                .antMatchers("/auth").permitAll()
                .antMatchers("/logout").permitAll()

                .antMatchers("/user/*").permitAll()
                .antMatchers("/employee/*").permitAll()
                .anyRequest().authenticated();

        http
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

        // disable page caching
        http.headers().cacheControl();


    }
}