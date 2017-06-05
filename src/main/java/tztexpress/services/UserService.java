package tztexpress.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tztexpress.core.Password;
import tztexpress.models.*;
import tztexpress.repositories.UserRepository;

/**
 * This service creates and updates users.
 */
@Service
public class UserService {

    private UserRepository userRepository;
    private AddressService addressService;

    @Autowired
    public UserService(UserRepository userRepository, AddressService addressService) {
        this.userRepository = userRepository;
        this.addressService = addressService;
    }


    /**
     * Gets one user by the userId
     * @param id the userId
     * @return the found user
     */
    public User getById(long id) {
        return this.userRepository.findOne(id);
    }
    /**
     * Creates the user with the corresponding information from the provided usermodel
     * @param userModel the model with the userinformation
     * @return the created user
     * @throws IllegalArgumentException if information is missing or fields are empty, this exception is thrown.
     */
    public User create(UserModelRequest userModel) throws IllegalArgumentException {

        // check if user exists in database
        User user = userRepository.findByEmailAddress(userModel.email.toLowerCase());

        if (user != null) {
            throw new IllegalArgumentException("Email-address already exists in database.");
        }

        // otherwise, create new user
        User newUser = new User();
        newUser.setFirstName(userModel.firstname);
        newUser.setLastName(userModel.lastname);
        newUser.setPrefix(userModel.prefix);
        newUser.setEmployee(userModel.isemployee);
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

    /**
     * Updates a user with the corresponding data, user is checked through email address
     * @param userModel the model with the data to adjust
     * @return the updated usermodel
     * @throws IllegalArgumentException if any of the provided information is incorrect, the exception is thrown
     * For example, when the email address isn't found in the database.
     */
    public User update(UserModelRequest userModel) throws IllegalArgumentException {
        // Get user from database:
        User user = userRepository.findByEmailAddress(userModel.email);

        if (user == null) {
            throw new IllegalArgumentException("Email-address does not exist in database.");
        }

        // update databaseuser
        if(userModel.firstname != null) {
            user.setFirstName(userModel.firstname);
        }

        if(userModel.lastname != null) {
            user.setLastName(userModel.lastname);
        }

        if(userModel.prefix != null) {
            user.setPrefix(userModel.prefix);
        }

        return userRepository.save(user);
    }

    /**
     * Updates the user password
     * @param changePasswordRequest the request with an emailaddress and the new password
     * @return the updated user
     * @throws IllegalArgumentException throws then the email address does not exist in the database.
     */
    public User updatePassword(ChangePasswordRequest changePasswordRequest) throws IllegalArgumentException {
        boolean returnValue = false;

        User user = userRepository.findByEmailAddress(changePasswordRequest.email.toLowerCase());

        if (user == null) {
            throw new IllegalArgumentException("Email-address does not exist in database.");
        }

        user.setPassword(Password.hashPassword(changePasswordRequest.password));

        return userRepository.save(user);

    }

    public UserModel UserToModel(User user) {
        UserModel returnValue = new UserModel();

        returnValue.email = user.getEmail();
        returnValue.firstname = user.getFirstName();
        returnValue.lastname = user.getLastName();
        returnValue.prefix = user.getPrefix();
        returnValue.email = user.getEmail().toLowerCase();
        returnValue.addressid = user.getAddressId();

        return returnValue;
    }
}
