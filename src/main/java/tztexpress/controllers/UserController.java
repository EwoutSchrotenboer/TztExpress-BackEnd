package tztexpress.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tztexpress.core.GenericResultHandler;
import tztexpress.models.*;
import tztexpress.services.AuthenticationService;
import tztexpress.services.ModelProviderService;
import tztexpress.services.UserService;

@Controller
@RequestMapping("/api/user")
public class UserController {
    private UserService userService;
    private ModelProviderService modelProviderService;

    @Autowired
    public UserController(UserService userService, ModelProviderService modelProviderService) {
        this.userService = userService;
        this.modelProviderService = modelProviderService;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody
    GenericResult<UserModel> createUser(@RequestBody UserModelRequest userModelRequest){

        try {
            User user = userService.create(userModelRequest);
            UserModel userModel = this.modelProviderService.UserToModel(user);
            return GenericResultHandler.GenericResult(userModel);
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
                UserModel userModel = this.modelProviderService.UserToModel(user);
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
                UserModel userModel = this.modelProviderService.UserToModel(updatedUser);
                return GenericResultHandler.GenericResult(userModel);
            } catch (Exception ex) {
                return GenericResultHandler.GenericExceptionResult(ex);
            }
        } else {
            return GenericResultHandler.GenericExceptionResult("Invalid authentication token");
        }
    }
}
