package com.cognizant.loginservice.controller;

import java.util.*;


import com.cognizant.loginservice.exception.UserAlreadyExistsException;
import com.cognizant.loginservice.exception.UsernameNotFound;
import com.cognizant.loginservice.service.AppUserDetailsService;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import com.cognizant.loginservice.entity.User;

import javax.validation.Valid;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_OK;


@Slf4j
@Api(tags="Login API")
@CrossOrigin
@RestController
@ComponentScan
public class AuthenticationController {

    @Autowired
    AppUserDetailsService appUserDetailsService;

    @ApiOperation(value = "Posts the user input data")
    @ApiResponses(value = { @ApiResponse(code = SC_OK, message = "ok"),
            @ApiResponse(code = SC_BAD_REQUEST, message = "An unexpected error occurred")
    })
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody @Valid User user)  {
        appUserDetailsService.saveUser(user);

    }

    @ApiOperation(value="Gets the user data to authenticate")
    @ApiResponses(value={@ApiResponse(code = SC_OK, message = "ok"),
            @ApiResponse(code = SC_BAD_REQUEST, message = "An unexpected error occurred")})
    @GetMapping("/authenticate")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> authenticate(@RequestHeader(value = "Authorization") String authHeader) throws UsernameNotFound {
            log.info("inside controller");
            try {
                User user = appUserDetailsService.userData(getUser(authHeader));
                HashMap<String, String> map = new HashMap<String, String>();
                String JWT = generateJwt((getUser(authHeader)));
                String role=user.getRole();
                log.debug("JWT:{}", JWT);
                map.put("token", JWT);
                map.put("id",Long.toString(user.getId()));
                map.put("role", role);
                map.put("userName", user.getUsername());
                log.info("END OF AUTH FUNCTION");
                return map;
            }
            catch(BadCredentialsException e){
                throw new UsernameNotFound("Invalid credentials");
            }
    }
    private String getUser(String authHeader) {
        String encodedCredentials = authHeader.split(" ", 2)[1];
        String decoded = new String(Base64.getDecoder().decode(encodedCredentials));
        String user = decoded.substring(0, decoded.indexOf(':'));
        return user;
    }

    private String getPassword(String authHeader) {
        String encodedCredentials = authHeader.split(" ", 2)[1];
        String decoded = new String(Base64.getDecoder().decode(encodedCredentials));
        String pwd = decoded.substring(decoded.indexOf(':') + 1);
        return pwd;
    }

    private String generateJwt(String user) {
        JwtBuilder builder = Jwts.builder();
        builder.setSubject(user);

        builder.setIssuedAt(new Date());

        // Set the token expiry as 20 minutes from now
        builder.setExpiration(new Date((new Date()).getTime() + 1200000));
        builder.signWith(SignatureAlgorithm.HS256, "secretkey");

        String token = builder.compact();
        log.info(token);
        return token;
    }


}