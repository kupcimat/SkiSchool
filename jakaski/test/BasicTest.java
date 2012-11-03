import org.eclipse.jdt.internal.formatter.Location;
import org.junit.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import play.test.*;
import models.*;

public class BasicTest extends UnitTest {

	//@Before
	//public void setup() {
	//	Fixtures.deleteDatabase();
	//}

	@Test
	public void createAndRetrieveUser() {
		// Create a new user and save it
		new User("miso@gmail.com" , "secret", "Miso", "Sabol", "123456789").save();

		// Retrieve the user with e-mail
		User miso = User.find("byEmail", "miso@gmail.com").first();

		// Test
		assertNotNull(miso);
		assertEquals("Miso", miso.firstname);
	}

	@Test
	public void createAndRetrieveInstructor() {
		// Create a new user and save it
		new Instructor("sas@gmail.com" , "secret", "An", "Sabol", "123456789").save();

		// Retrieve the user with e-mail
		User miso = User.find("byEmail", "sas@gmail.com").first();

		// Test
		assertNotNull(miso);
		assertEquals("An", miso.firstname);
	}
	
	@Test
	public void tryConnectAsUser() {
		// Create a new user and save it
		new User("matej@gmail.com", "secret", "Matej","Kup","123456789").save();

		// Test
		assertNotNull(User.connect("matej@gmail.com", "secret"));
		assertNull(User.connect("matej@gmail.com", "badpassword"));
		assertNull(User.connect("michal@gmail.com", "secret"));
	}
	
	@Test
	public void addAvailability(){
	    // Create a new user and save it
	    Instructor bob = new Instructor("bob@gmail.com", "secret", "Bob", "B","123").save();	
	    
	    new Availability(new Date(), new Date(), models.Location.JAHODNA,"note",bob).save();
	    new Availability(new Date(), new Date(),models.Location.JAHODNA, "note2",bob).save();
	    new Availability(new Date(), new Date(),models.Location.JAHODNA, "note3",bob).save();
	    
	    // Test that the availabilities have been created
	    assertEquals(3, Availability.count());
	    
	    // Retrieve all availabilities created by Bob
	    List<Availability> bobAvailabilities = Availability.find("byInstructor", bob).fetch();
	    
	    // Tests
	    assertEquals(3, bobAvailabilities.size());
	    Availability firstAvailability = bobAvailabilities.get(0);
	    assertNotNull(firstAvailability);
	    assertEquals(bob, firstAvailability.instructor);
	    assertEquals("note", firstAvailability.note);
	}
	
	@Test
	public void useAvailibityRelation() throws ParseException {
	    // Create a new user and save it
	    Instructor bob = new Instructor("bob@gmail.com", "secret", "Bob", "B", "1234").save();
	 
	    // add Availabilities
	    bob.addAvailability(new SimpleDateFormat("dd.MM.yyyy").parse("01.09.2012"), new SimpleDateFormat("dd.MM.yyyy").parse("01.10.2012"),models.Location.JAHODNA,"ahoj" );
	    bob.addAvailability(new SimpleDateFormat("dd.MM.yyyy").parse("02.09.2012"), new SimpleDateFormat("dd.MM.yyyy").parse("02.10.2012"),models.Location.JAHODNA,"bla" );
	 
	    // Count things
	    assertEquals(1, User.count());
	    assertEquals(2, Availability.count());
	 
	    // Retrieve Bob's availabilities
	    Availability bobAvailability =Availability.find("byInstructor", bob).first();
	    List<Availability> bobAvailabilities =Availability.find("byInstructor", bob).fetch();
	    assertNotNull(bobAvailability);
	    assertEquals(2,bobAvailabilities.size());
	 
	    // Navigate to Availabilities
	    assertEquals(2, bob.availabilities.size());
	    
	    // Delete the Instructor
	    bob.delete();
	    
	    // Check that all Availabilities have been deleted
	    assertEquals(0, User.count());
	    assertEquals(0, Instructor.count());
	    assertEquals(0, Availability.count());
	}
	
}
