package com.banking.chestnut.commoncontrollers;

import com.banking.chestnut.commonservices.IUserService;
import com.banking.chestnut.models.Users;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private IUserService userService;
    private Gson gson;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
        this.gson = new Gson();
    }

    @GetMapping(value = "/getbyid/{id}")
    public @ResponseBody
    ResponseEntity getUserById(@PathVariable Integer id){
        try {
            Optional<Users> user = this.userService.findById(id);
            if(user.isPresent())
                return new ResponseEntity<>(gson.toJson(user.get()), HttpStatus.OK);
            else
                return new ResponseEntity<>(gson.toJson("User not found"), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(gson.toJson(e), HttpStatus.BAD_REQUEST);
        }
    }
}
