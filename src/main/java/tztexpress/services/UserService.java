package tztexpress.services;

import com.sun.media.sound.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import tztexpress.core.Password;
import tztexpress.models.*;
import tztexpress.repositories.UserRepository;

import javax.naming.ConfigurationException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;
    private AddressService addressService;

    @Autowired
    public UserService(UserRepository userRepository, AddressService addressService) {
        this.userRepository = userRepository;
        this.addressService = addressService;
    }

    public List<User> listAll() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    public User getById(long id) {
        return null;
    }

    public User create(UserModelRequest userModel) throws InvalidDataException {

        // check if user exists in database
        User user = userRepository.findByEmailAddress(userModel.email.toLowerCase());

        if (user != null) {
            throw new InvalidDataException("Email-address already exists in database.");
        }

        // otherwise, create new user
        User newUser = new User();
        newUser.setFirstName(userModel.firstName);
        newUser.setLastName(userModel.lastName);
        newUser.setPrefix(userModel.prefix);
        newUser.setEmployee(userModel.isEmployee);
        newUser.setEmail(userModel.email.toLowerCase());

        Address address = this.addressService.getAddress(userModel.address);

        if (address != null) {
            newUser.setAddressId(address.getId());
        } else {
            Address newAddress = this.addressService.createAddress(userModel.address);
            newUser.setAddressId(newAddress.getId());
        }

            // encrypt password
            newUser.setPassword(Password.hashPassword(userModel.password));

            return userRepository.save(newUser);
    }

    public User update(UserModelRequest userModel) throws InvalidDataException {
        // Get user from database:
        User user = userRepository.findByEmailAddress(userModel.email);

        if (user == null) {
            throw new InvalidDataException("Email-address does not exist in database.");
        }

        // update databaseuser
        if(userModel.firstName != null) {
            user.setFirstName(userModel.firstName);
        }

        if(userModel.lastName != null) {
            user.setLastName(userModel.lastName);
        }

        if(userModel.prefix != null) {
            user.setPrefix(userModel.prefix);
        }

        return userRepository.save(user);
    }

    public User updatePassword(ChangePasswordRequest changePasswordRequest) throws InvalidDataException {
        boolean returnValue = false;

        User user = userRepository.findByEmailAddress(changePasswordRequest.Email.toLowerCase());

        if (user == null) {
            throw new InvalidDataException("Email-address does not exist in database.");
        }

        user.setPassword(Password.hashPassword(changePasswordRequest.Password));

        return userRepository.save(user);

    }

    public UserModel UserToModel(User user) {
        UserModel returnValue = new UserModel();

        returnValue.Email = user.getEmail();
        returnValue.FirstName = user.getFirstName();
        returnValue.LastName = user.getLastName();
        returnValue.Prefix = user.getPrefix();
        returnValue.Email = user.getEmail().toLowerCase();
        returnValue.AddressId = user.getAddressId();

        return returnValue;
    }
}
