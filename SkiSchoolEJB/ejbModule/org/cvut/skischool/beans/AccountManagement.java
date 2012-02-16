package org.cvut.skischool.beans;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.cvut.skischool.model.Account;

/**
 *
 * @author sabolmi1
 */
@Stateless
public class AccountManagement implements AccountManagementLocal {

    @PersistenceContext
    EntityManager em;

    @Override
    public void createAccount(Account account) {
        em.persist(account);
    }

    @Override
    public void updateAccount(Account account) {
        em.merge(account);
    }

    @Override
    public void deleteAccount(Account account) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Account getAccount(long id) {
        Account account = em.find(Account.class, id);
        return account;
    }

    @Override
    public Account getAccount(String username) {
        Account account = null;

        try {
            Query q = em.createNamedQuery("Account.getAccountByUsername");
            q.setParameter("username", username);
            account = (Account) q.getSingleResult();
        } catch (NoResultException ex) {
        }

        return account;
    }

    @Override
    public List<Account> getAllAccounts() {
        List<Account> accounts = (List<Account>) em.createNamedQuery("Account.getAllAccounts").getResultList();
        return accounts;
    }
}
