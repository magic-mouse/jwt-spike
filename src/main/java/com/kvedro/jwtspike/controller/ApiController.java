package com.kvedro.jwtspike.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kvedro.jwtspike.Users;
import com.kvedro.jwtspike.services.LoginDataUserMapperService;
import com.kvedro.jwtspike.services.ObjectMapperService;
import com.kvedro.jwtspike.services.UsersDataService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

@Configuration
@RestController
@RequestMapping("/api")
public class ApiController {

    @Value("${com.kvedro.jwt.public.key}")
    private String key;

    @Autowired
    ObjectMapperService objectMapperService;
    @Autowired
    LoginDataUserMapperService loginDataUserMapperService;
    @Autowired
    UsersDataService usersDataService;


    @RequestMapping(value = "/jwt/{test}",method = RequestMethod.GET)
    public String jwtOut(@PathVariable String test){
        return Jwts.builder().signWith(SignatureAlgorithm.HS256, key).setSubject(test).compact();
    }

    @RequestMapping(value = "/jwt",method = RequestMethod.POST)
    public String jwtIn(@RequestParam(value = "jwt") String jwt){
        return Jwts.parser().setSigningKey(key).parseClaimsJws(jwt).getBody().getSubject();
    }

    @RequestMapping(value = "/registeruser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Users> registerUser(@RequestBody LoginModel loginData){
        Users userToRegister = loginDataUserMapperService.loginDataToUser(loginData);
        Users registeredUser = usersDataService.save(userToRegister);
        return new ResponseEntity<>(registeredUser, HttpStatus.OK);
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> authenticate(@RequestBody LoginModel loginData){
        String loginDataJson = null;
        try {
            loginDataJson = objectMapperService.convertToJson(loginData);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>("Spomething did not go as expected, due to security reasons the errormessage is hidden please contact the support if the problem continiues.", HttpStatus.BAD_REQUEST);
        }
        String status = "{\"token\":\"" + Jwts.builder().signWith(SignatureAlgorithm.HS256, key).setSubject(loginDataJson).compact() + "\"}";
        return new ResponseEntity<>(status, HttpStatus.ACCEPTED);

    }

}
