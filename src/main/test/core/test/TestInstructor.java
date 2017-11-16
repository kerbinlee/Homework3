package core.test;

import core.api.IAdmin;
import core.api.IInstructor;
import core.api.IStudent;
import core.api.impl.Admin;
import core.api.impl.Instructor;
import core.api.impl.Student;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestInstructor {

    private IInstructor instructor;
    private IAdmin admin;
    private IStudent student;

    @Before
    public void setup() {
        this.instructor = new Instructor();
        this.admin = new Admin();
        this.student = new Student();
    }

    // provided this instructor has been assigned to this class
    // test ability to add homework to a course with corresponding assigned instructor
    @Test
    public void testAddHomework() {
    	this.admin.createClass("Test1", 2017, "Instructor", 15);
    	this.instructor.addHomework("Instructor", "Test1", 2017, "Homework1");
    	assertTrue(this.instructor.homeworkExists("Test1", 2017, "Homework1"));
    }
    
    // provided this instructor has been assigned to this class
    // test inability to add homework to a course without corresponding assigned instructor
    @Test
    public void testAddHomework1() {
    	this.admin.createClass("Test1", 2017, "Instructor1", 15);
    	this.instructor.addHomework("Instructor", "Test1", 2017, "Homework1");
    	assertFalse(this.instructor.homeworkExists("Test1", 2017, "Homework1"));
    }
    
    @Test
    public void testAddHomework2() {
    	this.admin.createClass("Test1", 2017, "Instructor", 15);
    	this.instructor.addHomework("Instructor1", "Test1", 2017, "Homework1");
    	assertFalse(this.instructor.homeworkExists("Test1", 2017, "Homework1"));
    }
    
    // repeat previous 3 tests but with spaces in instructor name
    @Test
    public void testAddHomework3() {
    	this.admin.createClass("Test1", 2017, "First Last", 15);
    	this.instructor.addHomework("First Last", "Test1", 2017, "Homework1");
    	assertTrue(this.instructor.homeworkExists("Test1", 2017, "Homework1"));
    }
    
    @Test
    public void testAddHomework4() {
    	this.admin.createClass("Test1", 2017, "First Last", 15);
    	this.instructor.addHomework("FirstLast", "Test1", 2017, "Homework1");
    	assertFalse(this.instructor.homeworkExists("Test1", 2017, "Homework1"));
    }
    
    @Test
    public void testAddHomework5() {
    	this.admin.createClass("Test1", 2017, "FirstLast", 15);
    	this.instructor.addHomework("First Last", "Test1", 2017, "Homework1");
    	assertFalse(this.instructor.homeworkExists("Test1", 2017, "Homework1"));
    }
    
    // add homework to nonexistent class/year/both
    @Test
    public void testAddHomework6() {
    	this.admin.createClass("Test1", 2017, "First Last", 15);
    	this.instructor.addHomework("First Last", "Test", 2017, "Homework1");
    	assertFalse(this.instructor.homeworkExists("Test", 2017, "Homework1"));
    }
    
    @Test
    public void testAddHomework7() {
    	this.admin.createClass("Test", 2017, "First Last", 15);
    	this.instructor.addHomework("First Last", "Test1", 2017, "Homework1");
    	assertFalse(this.instructor.homeworkExists("Test1", 2017, "Homework1"));
    }
    
    @Test
    public void testAddHomework8() {
    	this.admin.createClass("Test", 2018, "First Last", 15);
    	this.instructor.addHomework("First Last", "Test", 2017, "Homework1");
    	assertFalse(this.instructor.homeworkExists("Test", 2017, "Homework1"));
    }
    
    @Test
    public void testAddHomework9() {
    	this.admin.createClass("Test1", 2018, "First Last", 15);
    	this.instructor.addHomework("First Last", "Test", 2017, "Homework1");
    	assertFalse(this.instructor.homeworkExists("Test", 2017, "Homework1"));
    }
    
    @Test
    public void testAddHomework10() {
    	this.admin.createClass("Test", 2018, "First Last", 15);
    	this.instructor.addHomework("First Last", "Test1", 2017, "Homework1");
    	assertFalse(this.instructor.homeworkExists("Test1", 2017, "Homework1"));
    }
    
    @Test
    public void testAddHomework11() {
    	this.instructor.addHomework("First Last", "Test1", 2017, "Homework1");
    	assertFalse(this.instructor.homeworkExists("Test1", 2017, "Homework1"));
    }
    
    // Assign grade provided this instructor has been assigned to this class,
    // the homework has been assigned and the student has submitted this homework
    // test ability to assign grade when all conditions are met
    @Test
    public void testAssignGrade() {
    	this.admin.createClass("Test1", 2017, "Instructor", 15);
    	this.instructor.addHomework("Instructor", "Test1", 2017, "Homework1");
    	this.student.submitHomework("Student1", "Homework1", "Solution", "Test1", 2017);
    	this.instructor.assignGrade("Instructor", "Test1", 2017, "Homework1", "Student1", 87);
    	assertEquals(this.instructor.getGrade("Test1", 2017, "Homework1", "Student1"), new Integer(87));
    }
    
    // test inability to assign grade when instructor has not been assigned to the class
    @Test
    public void testAssignGrade1() {
    	this.admin.createClass("Test1", 2017, "Instructor1", 15);
    	this.instructor.addHomework("Instructor", "Test1", 2017, "Homework1");
    	this.student.submitHomework("Student1", "Homework1", "Solution", "Test1", 2017);
    	this.instructor.assignGrade("Instructor", "Test1", 2017, "Homework1", "Student1", 87);
    	assertNull(this.instructor.getGrade("Test1", 2017, "Homework1", "Student1"));
    }
    
    @Test
    public void testAssignGrade2() {
    	this.admin.createClass("Test1", 2017, "Instructor", 15);
    	this.instructor.addHomework("Instructor1", "Test1", 2017, "Homework1");
    	this.student.submitHomework("Student1", "Homework1", "Solution", "Test1", 2017);
    	this.instructor.assignGrade("Instructor1", "Test1", 2017, "Homework1", "Student1", 87);
    	assertNull(this.instructor.getGrade("Test1", 2017, "Homework1", "Student1"));
    }
    
    @Test
    public void testAssignGrade21() {
    	this.admin.createClass("Test1", 2017, "Instructor", 15);
    	this.instructor.addHomework("Instructor1", "Test1", 2017, "Homework1");
    	this.student.submitHomework("Student1", "Homework1", "Solution", "Test1", 2017);
    	this.instructor.assignGrade("Instructor", "Test1", 2017, "Homework1", "Student1", 87);
    	assertNull(this.instructor.getGrade("Test1", 2017, "Homework1", "Student1"));
    }
    
    @Test
    public void testAssignGrade22() {
    	this.admin.createClass("Test1", 2017, "Instructor", 15);
    	this.instructor.addHomework("Instructor", "Test1", 2017, "Homework1");
    	this.student.submitHomework("Student1", "Homework1", "Solution", "Test1", 2017);
    	this.instructor.assignGrade("Instructor1", "Test1", 2017, "Homework1", "Student1", 87);
    	assertNull(this.instructor.getGrade("Test1", 2017, "Homework1", "Student1"));
    }
    
    // test inability to assign grade when homework has not been assigned
    @Test
    public void testAssignGrade3() {
    	this.admin.createClass("Test1", 2017, "Instructor", 15);
    	this.instructor.addHomework("Instructor", "Test1", 2017, "Homework");
    	this.student.submitHomework("Student1", "Homework1", "Solution", "Test1", 2017);
    	this.instructor.assignGrade("Instructor", "Test1", 2017, "Homework1", "Student1", 87);
    	assertNull(this.instructor.getGrade("Test1", 2017, "Homework1", "Student1"));
    }
    
    @Test
    public void testAssignGrade4() {
    	this.admin.createClass("Test1", 2017, "Instructor", 15);
    	this.instructor.addHomework("Instructor", "Test1", 2017, "Homework1");
    	this.student.submitHomework("Student1", "Homework", "Solution", "Test1", 2017);
    	this.instructor.assignGrade("Instructor", "Test1", 2017, "Homework", "Student1", 87);
    	assertNull(this.instructor.getGrade("Test1", 2017, "Homework", "Student1"));
    }
    
    @Test
    public void testAssignGrade5() {
    	this.admin.createClass("Test1", 2017, "Instructor", 15);
    	this.student.submitHomework("Student1", "Homework1", "Solution", "Test1", 2017);
    	this.instructor.assignGrade("Instructor", "Test1", 2017, "Homework1", "Student1", 87);
    	assertNull(this.instructor.getGrade("Test1", 2017, "Homework1", "Student1"));
    }
}
