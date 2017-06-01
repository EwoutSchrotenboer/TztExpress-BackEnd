package tztexpress.services;

import tztexpress.models.Package;
import tztexpress.models.User;

import java.util.List;

/**
 * Created by jt on 1/10/17.
 */
public interface IUserService {

    List<User> listAll();

    User getById(long id);

    User saveOrUpdate(User user);

    void delete(long id);
}
