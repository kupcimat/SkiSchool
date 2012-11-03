package models;

import play.*;
import play.data.validation.Email;
import play.data.validation.Password;
import play.data.validation.Phone;
import play.data.validation.Required;
import play.db.jpa.*;

import javax.persistence.*;

import java.util.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "SkischoolUser")
public class User extends Model {

	@Email
	@Required
	public String email;
	@Password
	@Required
	public String password;
	public String firstname;
	public String surname;
	@Phone
	public String phone;
	@Required
	@ManyToMany
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

	public String toString() {
		return firstname+" "+surname;
	}

}
