package hr.tvz.keepthechange.repository;

import hr.tvz.keepthechange.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Contains methods for performing database queries related to {@link User} entities.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    boolean existsByUsername(String username);
}
