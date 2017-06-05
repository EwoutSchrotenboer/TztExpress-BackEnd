package tztexpress.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import tztexpress.models.User;

/**
 * Contains the data to adjust Users in the database.
 */
public interface UserRepository extends CrudRepository<User, Long> {
    /**
     * Queries database-users and returns the user with a specific email address, instead of an Id
     * @param emailAddress the email address
     * @return the user with the specific email address
     */
    @Query("select u from User u where u.email = ?1")
    User findByEmailAddress(String emailAddress);
}