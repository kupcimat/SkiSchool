package org.cvut.skischool.beans;

import java.util.List;
import javax.ejb.Local;
import org.cvut.skischool.model.Account;

/**
 *
 * @author sabolmi1
 */
@Local
public interface AccountManagementLocal {

    void createAccount(Account account);

    void deleteAccount(Account account);

    void updateAccount(Account account);

    Account getAccount(long id);

    Account getAccount(String username);

    List<Account> getAllAccounts();
}
