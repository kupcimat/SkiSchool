package models;

import javax.persistence.Entity;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Lang extends Model {

	@Required
	public String name;

	public Lang(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}
