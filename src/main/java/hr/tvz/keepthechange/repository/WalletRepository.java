package hr.tvz.keepthechange.repository;

import hr.tvz.keepthechange.entity.Authority;
import hr.tvz.keepthechange.entity.Wallet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Contains methods for performing database queries related to {@link Wallet} entites.
 */
@Repository
public interface WalletRepository extends CrudRepository<Wallet, Long> {
}