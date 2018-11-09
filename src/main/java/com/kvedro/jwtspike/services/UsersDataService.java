package com.kvedro.jwtspike.services;


import com.kvedro.jwtspike.MongoDBRepository;
import com.kvedro.jwtspike.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersDataService {

    @Autowired
    MongoDBRepository mongoDBRepository;

    public Users save(Users users){
        return mongoDBRepository.save(users);
    }

}
