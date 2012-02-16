package org.cvut.skischool.conv;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.cvut.skischool.beans.UserManagementLocal;
import org.cvut.skischool.model.Student;

/**
 *
 * @author matej
 */
@FacesConverter(value = "student")
public class StudentConverter implements Converter {

    UserManagementLocal userManagement = lookupUserManagementLocal();

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        long id;
        try {
            id = Long.parseLong(value);
        } catch (NumberFormatException ex) {
            return null;
        }
        return userManagement.getStudent(id);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return String.valueOf(((Student) value).getId());
    }

    private UserManagementLocal lookupUserManagementLocal() {
        try {
            Context c = new InitialContext();
            return (UserManagementLocal) c.lookup("java:global/SkiSchool/SkiSchoolEJB/UserManagement");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
