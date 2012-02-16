package org.cvut.skischool.back;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.cvut.skischool.beans.AccountManagementLocal;
import org.cvut.skischool.beans.UserManagementLocal;
import org.cvut.skischool.core.NamingConstants;
import org.cvut.skischool.model.Account;
import org.cvut.skischool.model.Instructor;

/**
 *
 * @author matej
 */
@ManagedBean(name = "securityBean")
@RequestScoped
public class SecurityBean {

    @EJB
    private AccountManagementLocal accountManagement;
    @EJB
    private UserManagementLocal userManagement;

    public SecurityBean() {
    }

    public boolean isInRoleAdministrator() {
        return FacesContext.getCurrentInstance().getExternalContext().isUserInRole(NamingConstants.ROLE_ADMINISTRATOR);
    }

    public boolean isInRoleInstructor() {
        return FacesContext.getCurrentInstance().getExternalContext().isUserInRole(NamingConstants.ROLE_INSTRUCTOR);
    }

    public String getCurrentUsername() {
        return FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
    }

    public Instructor getCurrentInstructor() {
        Account account = accountManagement.getAccount(getCurrentUsername());
        Instructor instructor = userManagement.getInstructor(account.getPerson().getId());
        return instructor;
    }

    public String logout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.invalidate();
        return "login.xhtml";
    }
}
