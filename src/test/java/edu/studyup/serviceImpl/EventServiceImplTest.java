package edu.studyup.serviceImpl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import edu.studyup.entity.Event;
import edu.studyup.entity.Location;
import edu.studyup.entity.Student;
import edu.studyup.util.DataStorage;
import edu.studyup.util.StudyUpException;

class EventServiceImplTest {

	EventServiceImpl eventServiceImpl;
	private Event event;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		eventServiceImpl = new EventServiceImpl();
		//Create Student
		Student student = new Student();
		student.setFirstName("John");
		student.setLastName("Doe");
		student.setEmail("JohnDoe@email.com");
		student.setId(1);
		
		//Create Event1
		Event event = new Event();
		event.setEventID(1);
		event.setDate(new Date());
		event.setName("Event 1");
		Location location = new Location(-122, 37);
		event.setLocation(location);
		List<Student> eventStudents = new ArrayList<>();
		eventStudents.add(student);
		event.setStudents(eventStudents);
		
		DataStorage.eventData.put(event.getEventID(), event);
	}

	@AfterEach
	void tearDown() throws Exception {
		DataStorage.eventData.clear();
	}

	@Test
	void testUpdateEventName_GoodCase() throws StudyUpException {
		int eventID = 1;
		eventServiceImpl.updateEventName(eventID, "Renamed Event 1");
		assertEquals("Renamed Event 1", DataStorage.eventData.get(eventID).getName());
	}
	
	@Test
	void testUpdateEvent_WrongEventID_badCase() {
		int eventID = 3;
		Assertions.assertThrows(StudyUpException.class, () -> {
			eventServiceImpl.updateEventName(eventID, "Renamed Event 3");
		  });
	}
	
	@Test
	void testEventNameLength () throws StudyUpException {
		int eventID = 1;
		eventServiceImpl.updateEventName(eventID, "Name that is way too long to fit");
	}
	
	@Test
	void testActiveEvents() throws StudyUpException {
		for (Integer key : DataStorage.eventData.keySet())
		{
			if (!DataStorage.eventData.containsKey(key))
				throw new StudyUpException("No event found at key");
			assertTrue(eventServiceImpl.getActiveEvents().contains(DataStorage.eventData.get(key)));
		}
	}
	
	Event addEventTest() {
		// adding a new event with one student
		//adding student Jack Black
		
		Student student = new Student();
		student.setFirstName("Jack");
		student.setLastName("Black");
		student.setEmail("JackBlack@email.com");
		student.setId(2);
		
		// setting the event date
		
		Date setDate = new Date(28, 5, 22, 12, 40, 1);
		
		Event event = new Event();
		event.setEventID(2);
		event.setDate(setDate);
		event.setName("Event 2");
		Location location = new Location(-122, 37);
		event.setLocation(location);
		List<Student> eventStudents = new ArrayList<>();
		eventStudents.add(student);
		event.setStudents(eventStudents);
		return event;
	}
	
	
	//tests whether if a past event is added, it counts as an active event
	@Test
	void testPastActiveEvents() throws StudyUpException {
		Event event = new Event();
		event = addEventTest();
		DataStorage.eventData.put(event.getEventID(), event);
		assertFalse(eventServiceImpl.getActiveEvents().contains(event));
	}
		
	@Test
	void addEventTestID() throws StudyUpException {
		Event event = new Event();
		event = addEventTest();
		DataStorage.eventData.put(event.getEventID(), event);
		assertEquals(DataStorage.eventData.get(2).getEventID(), 2);
	}
	
	@Test
	void addEventTestDate() throws StudyUpException {
		Event event = new Event();
		event = addEventTest();
		DataStorage.eventData.put(event.getEventID(), event);
		assertEquals(DataStorage.eventData.get(2).getDate(), event.getDate());
	
	}
	
	@Test
	void addEventTestLocation() throws StudyUpException {
		Event event = new Event();
		event = addEventTest();
		DataStorage.eventData.put(event.getEventID(), event);
		assertEquals(DataStorage.eventData.get(2).getLocation(), event.getLocation());
	}
	
	@Test
	void addEventTestEventName() throws StudyUpException {
		Event event = new Event();
		event = addEventTest();
		DataStorage.eventData.put(event.getEventID(), event);
		assertEquals(DataStorage.eventData.get(2).getName(), "Event 2");
	}
	
	@Test
	void addEventTestStudent() throws StudyUpException {
		Student student = new Student();
		student.setFirstName("Jack");
		student.setLastName("Black");
		student.setEmail("JackBlack@email.com");
		student.setId(1);
		Event event = new Event();
		event.setEventID(1);
		event.setDate(new Date());
		event.setName("Event 1");
		Location location = new Location(-122, 37);
		event.setLocation(location);
		List<Student> eventStudents = new ArrayList<>();
		eventStudents.add(student);
		event.setStudents(eventStudents);
		DataStorage.eventData.put(event.getEventID(), event);
		// tests email then last name then first name
		assertEquals(true, DataStorage.eventData.get(1).getStudents().contains(student));
		
	}
	
	@Test
	void eventDelete() throws StudyUpException {
		Event event = new Event();
		event = addEventTest();
		DataStorage.eventData.put(event.getEventID(), event);
		eventServiceImpl.deleteEvent(2);
		if (DataStorage.eventData.get(2) != null)
		{
			throw new StudyUpException("Deletion failed");
		}
	}
	
	@Test
	void checkPastEvent() throws StudyUpException {
		Event event = new Event();
		event = addEventTest();
		DataStorage.eventData.put(event.getEventID(), event);
		assertTrue(eventServiceImpl.getPastEvents().contains(event));
	}
	
}
