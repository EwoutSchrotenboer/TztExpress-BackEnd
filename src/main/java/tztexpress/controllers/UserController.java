package tztexpress.controllers;

import tztexpress.models.Package;
import tztexpress.models.User;
import tztexpress.services.IPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import tztexpress.services.IUserService;

import java.util.List;

@Controller
@RequestMapping("/api/user")
public class UserController {
    private IUserService userService;

    @Autowired
    public void setUserService(IUserService userService) {
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

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseBody User saveOrUpdateUser(@RequestBody User user){
        return userService.saveOrUpdate(user);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable String id){
        userService.delete(Long.valueOf(id));
        return "Ok";
    }
}
