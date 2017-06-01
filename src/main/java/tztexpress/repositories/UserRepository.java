package tztexpress.repositories;

import org.springframework.data.repository.CrudRepository;
import tztexpress.models.User;

public interface UserRepository extends CrudRepository<User, Long> {
}