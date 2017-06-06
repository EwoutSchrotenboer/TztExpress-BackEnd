package tztexpress.models;

import java.util.List;

/**
 * TraincourierModel for Packages in backoffice application
 */
public class TraincourierModel {
    public Long id;
    public Boolean vogapproved;
    public String identification;
    public String email;
    public UserPackageModel user;
    public List<PackageModel> packages;
}
