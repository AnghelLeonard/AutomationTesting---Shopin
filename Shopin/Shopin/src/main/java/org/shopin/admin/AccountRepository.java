package org.shopin.admin;

public interface AccountRepository {

    Account findByEmail(final String email);
}
