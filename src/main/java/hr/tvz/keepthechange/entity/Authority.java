package hr.tvz.keepthechange.entity;

import hr.tvz.keepthechange.enumeration.Roles;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Represents a database entity from the AUTHORITIES table.
 */
@Entity
@Table(name = "authorities")
public class Authority {
    @Id
    private String username;
    private String authority;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(Roles authority) {
        this.authority = authority.toString();
    }
}
