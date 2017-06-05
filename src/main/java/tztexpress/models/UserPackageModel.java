package tztexpress.models;

/**
 * Usermodel , to return information to the front-end, does not contain (hashed) passwords
 */
public class UserPackageModel {
    public String email;
    public String firstname;
    public String lastname;
    public String prefix;
    public Address address;
}