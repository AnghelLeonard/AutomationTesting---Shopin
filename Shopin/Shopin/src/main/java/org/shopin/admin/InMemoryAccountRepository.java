package org.shopin.admin;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryAccountRepository implements AccountRepository {

    private final String user;
    private final String password;

    private final List<Account> accounts;

    public InMemoryAccountRepository(final @Value("${admin.email}") String user, final @Value("${admin.password}") String password) {
        this.user = user;
        this.password = password;

        accounts = new ArrayList<>();
        init();
    }

    private void init() {
        Account account = new Account();
        account.setFirstName("-");
        account.setLastName("-");

        account.setEmail(user);
        account.setPassword(password);
        accounts.add(account);
    }

    @Override
    public Account findByEmail(String email) {
        Account result = null;
        for (Account account : accounts) {
            if (account.getEmail().equals(email)) {
                result = account;
                break;
            }
        }
        return result;
    }
}
