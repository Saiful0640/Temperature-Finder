package com.temperatureFinder.controller;

import com.temperatureFinder.model.SaveResponseMessage;
import com.temperatureFinder.model.User;
import com.temperatureFinder.service.userService.Iuser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/v1")
public class UserController {

    @Autowired
    Iuser iuserService;

    @PostMapping("/saveUser")
    public ResponseEntity<SaveResponseMessage> saveUser(@RequestBody User user){

        boolean userSave = iuserService.UserSave(user);

        if(!userSave){
            SaveResponseMessage saveResponseMessage = new SaveResponseMessage();
            saveResponseMessage.setResponseMessage("user Saving faild");
            return ResponseEntity.ok(saveResponseMessage);
        }else {
            SaveResponseMessage saveResponseMessage = new SaveResponseMessage();
            saveResponseMessage.setResponseCode(200);
            saveResponseMessage.setResponseMessage("user Saved successfull");

            return ResponseEntity.status(HttpStatus.CREATED).body(saveResponseMessage);
        }



    }
}
