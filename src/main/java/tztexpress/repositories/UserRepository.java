package tztexpress.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import tztexpress.models.User;

public interface UserRepository extends CrudRepository<User, Long> {
    @Query("select u from User u where u.email = ?1")
    User findByEmailAddress(String emailAddress);
}