package tztexpress.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tztexpress.core.GenericResultHandler;
import tztexpress.models.*;
import tztexpress.services.AuthenticationService;
import tztexpress.services.UserService;

import java.util.List;

@Controller
@RequestMapping("/api/user")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public @ResponseBody List<User> listUsers(){
        return userService.listAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody User getUser(@PathVariable String id){
        return userService.getById(Long.valueOf(id));
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody
    GenericResult<User> createUser(@RequestBody UserModelRequest userModelRequest){

        try {
            User user = userService.create(userModelRequest);
            return GenericResultHandler.GenericResult(user);
        } catch (Exception ex) {
            return GenericResultHandler.GenericExceptionResult(ex);
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody
    GenericResult<UserModel> updateUser(@RequestHeader HttpHeaders headers, @RequestBody UserModelRequest userModelRequest) {
        // should be authenticated before this is possible
        if (AuthenticationService.ValidateToken(headers.getValuesAsList("Authentication"))) {
            try {
                User user = userService.update(userModelRequest);
                UserModel userModel = this.userService.UserToModel(user);
                return GenericResultHandler.GenericResult(userModel);
            } catch (Exception ex) {
                return GenericResultHandler.GenericExceptionResult(ex);
            }
        } else {
            return GenericResultHandler.GenericExceptionResult("Invalid authentication token");
        }

    }

    @RequestMapping(value = "/updatepassword", method = RequestMethod.POST)
    public @ResponseBody
    GenericResult<UserModel> updatePassword (@RequestHeader HttpHeaders headers, @RequestBody ChangePasswordRequest changePasswordRequest) {
        // should be authenticated before this is possible
        if (AuthenticationService.ValidateToken(headers.getValuesAsList("Authentication"))) {

            try {
                User updatedUser = userService.updatePassword(changePasswordRequest);
                UserModel userModel = this.userService.UserToModel(updatedUser);
                return GenericResultHandler.GenericResult(userModel);
            } catch (Exception ex) {
                return GenericResultHandler.GenericExceptionResult(ex);
            }
        } else {
            return GenericResultHandler.GenericExceptionResult("Invalid authentication token");
        }
    }
}
