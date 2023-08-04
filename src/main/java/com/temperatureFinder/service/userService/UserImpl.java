package com.temperatureFinder.service.userService;

import com.temperatureFinder.model.User;
import com.temperatureFinder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserImpl implements Iuser {

    @Autowired
    UserRepository userRepo;


    @Override
    public boolean UserSave(User user) {
        User info =  userRepo.save(user);

        boolean IsSuccess = info.getId()>0;

        if (!IsSuccess){
            return false;
        }else {
            return true;
        }

    }
}
