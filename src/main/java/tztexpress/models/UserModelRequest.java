package tztexpress.models;

/**
 * Usermodel , to adjust or create users.
 */
public class UserModelRequest {
    public String firstName;
    public String lastName;
    public String prefix;
    public String password;
    public String email;
    public AddressModel address;
    public boolean isEmployee;
}
