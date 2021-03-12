package hr.tvz.keepthechange.repository;

import hr.tvz.keepthechange.entity.Authority;
import hr.tvz.keepthechange.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Contains methods for performing database queries related to {@link Authority} entites.
 */
@Repository
public interface AuthorityRepository extends CrudRepository<Authority, Long> {
}