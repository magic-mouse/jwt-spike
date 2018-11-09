package com.kvedro.jwtspike.services;

import com.kvedro.jwtspike.Users;
import com.kvedro.jwtspike.controller.LoginModel;
import org.springframework.stereotype.Service;

@Service
public class LoginDataUserMapperService {

    public Users loginDataToUser(LoginModel loginModel){
        Users users = new Users();
        users.setUsername(loginModel.getUsername());
        users.setPassword(loginModel.getPassword());

        return users;
    }


}
