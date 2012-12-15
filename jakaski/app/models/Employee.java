package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class Employee extends User {

	public Employee(String email, String password, String fullname, String surname, String phone) {
	    super(email, password, fullname, surname, phone);
    }
    
}
