package models;

import play.*;
import play.data.validation.Email;
import play.data.validation.Required;
import play.db.jpa.*;

import javax.persistence.*;

import java.util.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="user")
public class User extends Model {

	@Email
	@Required
	public String email;

	@Required
	public String password;

	public String fullname;

	public User(String email, String password, String fullname) {
		this.email = email;
		this.password = password;
		this.fullname = fullname;
	}

	public static User connect(String email, String password) {
		return find("byEmailAndPassword", email, password).first();
	}

	public String toString() {
		return email;
	}

}
