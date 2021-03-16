package hr.tvz.keepthechange.repository;

import hr.tvz.keepthechange.entity.Authority;
import hr.tvz.keepthechange.entity.Wallet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * Contains methods for performing database queries related to {@link Wallet} entities.
 */
@Repository
public interface WalletRepository extends CrudRepository<Wallet, Long> {
    ArrayList<Wallet> findByUsername(String username);
}