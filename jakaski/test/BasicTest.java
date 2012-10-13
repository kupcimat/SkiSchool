import org.junit.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import play.test.*;
import models.*;

public class BasicTest extends UnitTest {

	@Before
	public void setup() {
		Fixtures.deleteDatabase();
	}

	@Test
	public void createAndRetrieveUser() {
		// Create a new user and save it
		new User("miso@gmail.com", "secret", "Miso").save();

		// Retrieve the user with e-mail
		User miso = User.find("byEmail", "miso@gmail.com").first();

		// Test
		assertNotNull(miso);
		assertEquals("Miso", miso.fullname);
	}

	@Test
	public void tryConnectAsUser() {
		// Create a new user and save it
		new User("matej@gmail.com", "secret", "Matej").save();

		// Test
		assertNotNull(User.connect("matej@gmail.com", "secret"));
		assertNull(User.connect("matej@gmail.com", "badpassword"));
		assertNull(User.connect("michal@gmail.com", "secret"));
	}
	
	@Test
	public void addAvailability(){
	    // Create a new user and save it
	    Instructor bob = new Instructor("bob@gmail.com", "secret", "Bob").save();	
	    
	    new Availability(new Date(), new Date(), "note",bob).save();
	    new Availability(new Date(), new Date(), "note2",bob).save();
	    new Availability(new Date(), new Date(), "note3",bob).save();
	    
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
	    Instructor bob = new Instructor("bob@gmail.com", "secret", "Bob").save();
	 
	    // add Availabilities
	    bob.addAvailability(new SimpleDateFormat("dd.MM.yyyy").parse("01.09.2012"), new SimpleDateFormat("dd.MM.yyyy").parse("01.10.2012"),"ahoj" );
	    bob.addAvailability(new SimpleDateFormat("dd.MM.yyyy").parse("02.09.2012"), new SimpleDateFormat("dd.MM.yyyy").parse("02.10.2012"),"bla" );
	 
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
	    assertEquals("ahoj", bob.availabilities.get(0).note);
	    
	    // Delete the Instructor
	    bob.delete();
	    
	    // Check that all Availabilities have been deleted
	    assertEquals(0, User.count());
	    assertEquals(0, Instructor.count());
	    assertEquals(0, Availability.count());
	}
	
}
