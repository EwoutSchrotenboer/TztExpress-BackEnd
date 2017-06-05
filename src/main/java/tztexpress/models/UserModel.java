package tztexpress.models;

/**
 * Usermodel , to return information to the front-end, does not contain (hashed) passwords
 */
public class UserModel {
    public String Email;
    public String FirstName;
    public String LastName;
    public String Prefix;
    public long AddressId;
}
