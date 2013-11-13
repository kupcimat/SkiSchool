package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class Price extends Model {

	public LessonType lessonType;
	public int lessonCount;
	public int price;

	public Price(LessonType lessonType, int lessonCount, int price) {
		this.lessonType = lessonType;
		this.lessonCount = lessonCount;
		this.price = price;
	}
	
}
