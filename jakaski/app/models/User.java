package models;

import play.*;
import play.data.validation.Email;
import play.data.validation.MinSize;
import play.data.validation.Password;
import play.data.validation.Phone;
import play.data.validation.Required;
import play.data.validation.Unique;
import play.db.jpa.*;

import javax.persistence.*;

import models.deadbolt.Role;
import models.deadbolt.RoleHolder;

import java.util.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "SkiSchoolUser")
public class User extends Model implements RoleHolder {

	@Email
	@Required
	@Unique
	public String email;
	@Password
	@Required
	@MinSize(5)
	public String password;
	@Required
	public String firstname;
	public String surname;
	@Phone
	public String phone;
	@Required
	@ManyToOne
	public ApplicationRole role;

	public User(String email, String password, String fullname, String surname, String phone) {
		this.email = email;
		this.password = password;
		this.firstname = fullname;
		this.surname = surname;
		this.phone = phone;
	}

	public static User connect(String email, String password) {
		return find("byEmailAndPassword", email, password).first();
	}
	
    public static User getByEmail(String userName)
    {
        return find("byEmail", userName).first();
    }

    @Override
	public String toString() {
		return firstname+" "+surname;
	}

	@Override
    public List<? extends Role> getRoles() {
		return Arrays.asList(this.role);
    }

}
