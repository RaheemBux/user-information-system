package com.sapphire.testsapphire.security;


import com.sapphire.testsapphire.config.ProdProfileCondition;
import com.sapphire.testsapphire.entity.UserEntity;
import com.sapphire.testsapphire.service.UserService;
import com.sapphire.testsapphire.util.CommonUtil;
import com.sapphire.testsapphire.util.MacAddress;
import com.sapphire.testsapphire.util.MyPrint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Conditional(ProdProfileCondition.class)
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);

    @Autowired
    private JwtUserDetailsServiceImpl jwtUserDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {

            String url = request.getRequestURI().replace(servletContext.getContextPath() + "/", "").replaceAll("(([/]+)([0-9])+)", "");
            System.out.println("Original Url: " + request.getRequestURI());
            System.out.println("Url: " + url);
            ArrayList<String> authUrlList = new ArrayList<>();
            authUrlList.add("auth");
            authUrlList.add("user/create");
            authUrlList.add("user/update");
            authUrlList.add("user/delete");
            authUrlList.add("user/view");
            authUrlList.add("user/delete");
            authUrlList.add("user/getAll");
            authUrlList.add("user/views");

            if (authUrlList.contains(url)) {
                System.out.println("username: " + request.getParameter("username"));
                System.out.println("Password: " + request.getParameter("password"));
                System.out.println("DoFilter: " + Boolean.TRUE);
                chain.doFilter(request, response);
            } else {
                System.out.println("ELSE URL LIST NOT CONTAINS: " + url);
                String authToken = request.getHeader("Authorization");

                LOGGER.info("Token:::" + authToken);
                System.out.println("Token   "+authToken);
                if (authToken != null && !authToken.trim().isEmpty() && !authToken.equals("Bearer")) {//AFTER
                    if (jwtTokenUtil.isTokenExpired(authToken)) {
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token Expired");
                    } else {
                        System.out.println("Not Expired");
                    }
                    System.out.println("=======================");
                    System.out.println("authToken: " + authToken);
                    System.out.println("ExpDate: " + jwtTokenUtil.getExpirationDateFromToken(authToken));
                    System.out.println("=======================");
                    MyPrint.println("Auth Token :::: " + authToken);

                    String username = jwtTokenUtil.getUsernameFromToken(authToken);

                    logger.info(jwtTokenUtil + "checking authentication für user " + username);

                    if (username != null) {
                        logger.info("checking authentication für user ::: 2" + username);
                        // It is not compelling necessary to load the use details from the database. You could also store the information
                        // in the token and read it from it. It's up to you ;)
                        UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
                        LOGGER.info("IP:" + request.getRemoteAddr() + ",MAC:" + MacAddress.getMac(request.getRemoteAddr())
                                + ",DATE:" + CommonUtil.getCurrentTimestamp().toString() + ",User:" + username + ",URL:" + request.getRequestURI());
                        if (username == null && username.isEmpty()) {
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User Not Found!!!");
                        }
                        UserEntity users = this.userService.findByUserName(username);
                        if (users.getStatus() == false) {
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User Not Found!!!");

                        }
//
                        System.out.println("============================================");
                        System.out.println("UserName: " + username);
                        System.out.println("Status: " + users.getStatus());
                        System.out.println("Url: " + url);

                        if (!authUrlList.contains(url)) {
                            String[] urlSplitter = url.split("/");
                            if (urlSplitter.length > 1) {
                                url = urlSplitter[0] + "/" + urlSplitter[1];
                            } else {
                                url = urlSplitter[0];
                            }
                            ArrayList<String> utilPermissionList = jwtTokenUtil.getPermissionList(authToken);



                            utilPermissionList.add("employee/create");
                            utilPermissionList.add("employee/update");
                            utilPermissionList.add("employee/delete");
                            utilPermissionList.add("employee/view");
                            utilPermissionList.add("employee/delete");
                            utilPermissionList.add("employee/getAll");

                            if (utilPermissionList.contains(url)) {
                                System.out.println("Authorize:---- " + Boolean.TRUE);
                                System.out.println("============================================\n");
                                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                                SecurityContextHolder.getContext().setAuthentication(authentication);
                                if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                                    logger.info("Valid Token");
                                } else {
                                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                                }

                            } else {
                                System.out.println("Authorize:NOT " + Boolean.FALSE);
                                System.out.println("============================================\n");
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                            }
                        } else {
                            System.out.println("URL NOT FOUND ");
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");

                        }
                    } else {
                        System.out.println("User is null");
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                    }
                } else {
                    System.out.println("Token is Null");
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                }
                System.out.println("============================================");
                System.out.println("LAST POINT");
                System.out.println("============================================\n");
                chain.doFilter(request, response);
            }


        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception:", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }
}
