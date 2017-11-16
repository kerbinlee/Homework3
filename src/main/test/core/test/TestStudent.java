package core.test;

import org.junit.Before;
import org.junit.Test;

import core.api.IAdmin;
import core.api.IInstructor;
import core.api.IStudent;
import core.api.impl.Admin;
import core.api.impl.Instructor;
import core.api.impl.Student;

import static org.junit.Assert.*;

public class TestStudent {

    private IStudent student;
    private IInstructor instructor;
    private IAdmin admin;

    @Before
    public void setup() {
        this.student = new Student();
        this.instructor = new Instructor();
        this.admin = new Admin();
    }
    
    // register student provided this class exists and has not met its enrollment capacity
    // test ability to register when all criteria are met
    @Test
    public void testRegisterForClass() {
    	this.admin.createClass("Test1", 2017, "Instructor", 15);
    	this.student.registerForClass("Student1", "Test1", 2017);
    	assertTrue(this.student.isRegisteredFor("Student1", "Test1", 2017));
    }
    
    @Test
    public void testRegisterForClass1() {
    	this.admin.createClass("Test1", 2017, "Instructor", 1);
    	this.student.registerForClass("Student1", "Test1", 2017);
    	assertTrue(this.student.isRegisteredFor("Student1", "Test1", 2017));
    }
    
    @Test
    public void testRegisterForClass2() {
    	this.admin.createClass("Test1", 2017, "Instructor", 2);
    	this.student.registerForClass("Student1", "Test1", 2017);
    	this.student.registerForClass("Student2", "Test1", 2017);
    	assertTrue(this.student.isRegisteredFor("Student1", "Test1", 2017));
    	assertTrue(this.student.isRegisteredFor("Student2", "Test1", 2017));
    }
    
    // enroll students with spaces in their name and with no spaces and make sure they're there
    @Test
    public void testRegisterForClass3() {
    	this.admin.createClass("Test1", 2017, "Instructor", 2);
    	this.student.registerForClass("First Last", "Test1", 2017);
    	assertTrue(this.student.isRegisteredFor("First Last", "Test1", 2017));
    }
    
    @Test
    public void testRegisterForClass4() {
    	this.admin.createClass("Test1", 2017, "Instructor", 2);
    	this.student.registerForClass("First Last", "Test1", 2017);
    	assertFalse(this.student.isRegisteredFor("FirstLast", "Test1", 2017));
    }
    
    @Test
    public void testRegisterForClass5() {
    	this.admin.createClass("Test1", 2017, "Instructor", 2);
    	this.student.registerForClass("FirstLast", "Test1", 2017);
    	assertFalse(this.student.isRegisteredFor("First Last", "Test1", 2017));
    }
    
    @Test
    public void testRegisterForClass6() {
    	this.admin.createClass("Test1", 2017, "Instructor", 2);
    	this.student.registerForClass("Student1", "Test1", 2017);
    	this.student.registerForClass("Student2", "Test1", 2017);
    	this.student.dropClass("Student2", "Test1", 2017);
    	this.student.registerForClass("Student3", "Test1", 2017);
    	assertTrue(this.student.isRegisteredFor("Student1", "Test1", 2017));
    	assertFalse(this.student.isRegisteredFor("Student2", "Test1", 2017));
    	assertTrue(this.student.isRegisteredFor("Student3", "Test1", 2017));
    }
    
    
    // test inability to register for a class that does not exist
    @Test
    public void testRegisterForNonexistentClass() {
    	this.student.registerForClass("Student1", "Test1", 2017);
    	assertFalse(this.student.isRegisteredFor("Student1", "Test1", 2017));
    }
    
    @Test
    public void testRegisterForNonexistentClass1() {
    	this.admin.createClass("Test1", 2017, "Instructor", 15);
    	this.student.registerForClass("Student1", "Test", 2017);
    	assertFalse(this.student.isRegisteredFor("Student1", "Test", 2017));
    	assertFalse(this.student.isRegisteredFor("Student1", "Test1", 2017));
    }
    
    @Test
    public void testRegisterForNonexistentClass2() {
    	this.admin.createClass("Test", 2017, "Instructor", 15);
    	this.student.registerForClass("Student1", "Test1", 2017);
    	assertFalse(this.student.isRegisteredFor("Student1", "Test", 2017));
    	assertFalse(this.student.isRegisteredFor("Student1", "Test1", 2017));
    }
    
    // test inability to register for a class that met has its enrollment capacity
    @Test
    public void testRegisterForFullClass() {
    	this.admin.createClass("Test1", 2017, "Instructor", 1);
    	this.student.registerForClass("Student1", "Test1", 2017);
    	this.student.registerForClass("Student2", "Test1", 2017);
    	assertTrue(this.student.isRegisteredFor("Student1", "Test1", 2017));
    	assertFalse(this.student.isRegisteredFor("Student2", "Test1", 2017));
    }
    
    @Test
    public void testRegisterForFullClass1() {
    	this.admin.createClass("Test1", 2017, "Instructor", 1);
    	this.student.registerForClass("Student1", "Test1", 2017);
    	this.student.registerForClass("Student2", "Test1", 2017);
    	assertTrue(this.student.isRegisteredFor("Student1", "Test1", 2017));
    	assertFalse(this.student.isRegisteredFor("Student2", "Test1", 2017));
    }
    
    @Test
    public void testRegisterForFullClass2() {
    	this.admin.createClass("Test1", 2017, "Instructor", 2);
    	this.student.registerForClass("Student1", "Test1", 2017);
    	this.student.registerForClass("Student2", "Test1", 2017);
    	this.student.registerForClass("Student3", "Test1", 2017);
    	assertTrue(this.student.isRegisteredFor("Student1", "Test1", 2017));
    	assertTrue(this.student.isRegisteredFor("Student2", "Test1", 2017));
    	assertFalse(this.student.isRegisteredFor("Student3", "Test1", 2017));
    }
    
    // drop class provided the student is registered and the class has not ended
    // test ability to drop class when all criteria are met
    @Test
    public void testDropClass() {
    	this.admin.createClass("Test1", 2017, "Instructor", 15);
    	this.student.registerForClass("Student1", "Test1", 2017);
    	assertTrue(this.student.isRegisteredFor("Student1", "Test1", 2017));
    	this.student.dropClass("Student1", "Test1", 2017);
    	assertFalse(this.student.isRegisteredFor("Student1", "Test1", 2017));
    }
    
    // test inability for student to drop class if they are not registered
    @Test
    public void testDropClassWhenNotRegistered() {
    	this.admin.createClass("Test1", 2017, "Instructor", 15);
    	this.student.dropClass("Student1", "Test1", 2017);
    	assertFalse(this.student.isRegisteredFor("Student1", "Test1", 2017));
    }
    
    // dropping student twice results in not enrolled
    @Test
    public void testDropClassTwice() {
    	this.admin.createClass("Test1", 2017, "Instructor", 15);
    	this.student.registerForClass("Student1", "Test1", 2017);
    	this.student.dropClass("Student1", "Test1", 2017);
    	this.student.dropClass("Student1", "Test1", 2017);
    	assertFalse(this.student.isRegisteredFor("Student1", "Test1", 2017));
    }

    // how to test that the class has not passed when we cannot create a course in the past?
    
    
    // submit student's homework solution provided homework exists, 
    // student is registered and the class is taught in the current year
    @Test
    public void testSubmitHomework() {
    	this.admin.createClass("Test1", 2017, "Instructor", 15);
    	this.student.registerForClass("Student1", "Test1", 2017);
    	assertTrue(this.student.isRegisteredFor("Student1", "Test1", 2017));
    	this.instructor.addHomework("Instructor", "Test1", 2017, "Homework1");
    	this.student.submitHomework("Student1", "Homework1", "Submission", "Test1", 2017);
    	assertTrue(this.student.hasSubmitted("Student1", "Homework1", "Test1", 2017));
    }
    
    // test submitting hw for class in the future
    @Test
    public void testSubmitHomeworkFuture() {
    	this.admin.createClass("Test1", 2018, "Instructor", 15);
    	this.student.registerForClass("Student1", "Test1", 2018);
    	this.instructor.addHomework("Instructor", "Test1", 2018, "Homework1");
    	this.student.submitHomework("Student1", "Homework1", "Submission", "Test1", 2018);
    	assertFalse(this.student.hasSubmitted("Student1", "Homework1", "Test1", 2018));
    }
    
    @Test
    public void testSubmitHomeworkFuture1() {
    	this.admin.createClass("Test1", 2017, "Instructor", 15);
    	this.admin.createClass("Test1", 2018, "Instructor", 15);
    	this.student.registerForClass("Student1", "Test1", 2017);
    	this.student.registerForClass("Student1", "Test1", 2018);
    	this.instructor.addHomework("Instructor", "Test1", 2017, "Homework1");
    	this.instructor.addHomework("Instructor", "Test1", 2018, "Homework1");
    	this.student.submitHomework("Student1", "Homework1", "Submission", "Test1", 2018);
    	assertFalse(this.student.hasSubmitted("Student1", "Homework1", "Test1", 2018));
    	assertFalse(this.student.hasSubmitted("Student1", "Homework1", "Test1", 2017));
    }
    
    // test to see if homework is submitted only for current year if class is offered multiple yesrs
    @Test
    public void testSubmitHomeworkCurrent() {
    	this.admin.createClass("Test1", 2017, "Instructor", 15);
    	this.admin.createClass("Test1", 2018, "Instructor", 15);
    	this.student.registerForClass("Student1", "Test1", 2017);
    	this.student.registerForClass("Student1", "Test1", 2018);
    	this.instructor.addHomework("Instructor", "Test1", 2017, "Homework1");
    	this.instructor.addHomework("Instructor", "Test1", 2018, "Homework1");
    	this.student.submitHomework("Student1", "Homework1", "Submission", "Test1", 2017);
    	assertTrue(this.student.hasSubmitted("Student1", "Homework1", "Test1", 2017));
    	assertFalse(this.student.hasSubmitted("Student1", "Homework1", "Test1", 2018));
    }
}
