package org.cvut.skischool.back;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import org.cvut.skischool.Messages;
import org.cvut.skischool.beans.AccountManagementLocal;
import org.cvut.skischool.beans.UserManagementLocal;
import org.cvut.skischool.core.DigestTools;
import org.cvut.skischool.core.NamingConstants;
import org.cvut.skischool.model.Account;
import org.cvut.skischool.model.Instructor;

/**
 *
 * @author sabolmi1
 */
@ManagedBean(name = "instructorsBean")
@SessionScoped
public class InstructorsBean implements Serializable {

    @EJB
    private AccountManagementLocal accountManagement;
    @EJB
    private UserManagementLocal instructorManagement;
    private Instructor instructor;
    private boolean isNewInstructor;
    private String accountPassword;

    public InstructorsBean() {
        isNewInstructor = true;
    }

    public boolean isNewInstructor() {
        return isNewInstructor;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public void setAccountPassword(String password) {
        this.accountPassword = password;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public List<Instructor> getAllInstructors() {
        return instructorManagement.getAllInstructors();
    }

    public String newInstructor() {
        isNewInstructor = true;
        instructor = new Instructor();
        Account account = new Account();

        account.setPerson(instructor);
        instructor.setAccount(account);
        instructor.setDisabled(false);
        instructor.setActive(true);

        return "createinstructor?faces-redirect=true";
    }

    public String editInstructor(Instructor instructor) {
        isNewInstructor = false;
        this.instructor = instructor;

        return "createinstructor?faces-redirect=true";
    }

    public String saveInstructor() {
        // check whether username exists
        Account conflictAccount = accountManagement.getAccount(instructor.getAccount().getLogin());
        if (isNewInstructor && (conflictAccount != null)) {
            Messages.showErrorMessage("Zadané užívateľské meno už existuje");
            //Messages.showErrorMessage("Username already exists");
            return null;
        }
        // check whether user changed password
        if ((accountPassword != null) && (!accountPassword.isEmpty())) {
            String passwordHash = DigestTools.SHA1(accountPassword);
            instructor.getAccount().setPassword(passwordHash);
            accountPassword = null;
        } else if (isNewInstructor) {
            Messages.showErrorMessage("Zabudli ste vypniť heslo užívateľa");
            //Messages.showErrorMessage("You must specify user password");
            return null;
        }

        if (!isNewInstructor) {
            accountManagement.updateAccount(instructor.getAccount());
        }
        instructorManagement.updateInstructor(instructor);

        return "index?faces-redirect=true";
    }

    public List<SelectItem> getRoles() {
        List<SelectItem> roles = new ArrayList<SelectItem>(2);

        roles.add(new SelectItem(NamingConstants.ROLE_INSTRUCTOR, "Instructor"));
        roles.add(new SelectItem(NamingConstants.ROLE_ADMINISTRATOR, "Administrator"));

        return roles;
    }
}
