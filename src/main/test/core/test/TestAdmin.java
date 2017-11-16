package core.test;

import core.api.IAdmin;
import core.api.IStudent;
import core.api.impl.Admin;
import core.api.impl.Student;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestAdmin {

    private IAdmin admin;
    private IStudent student;

    @Before
    public void setup() {
        this.admin = new Admin();
        this.student = new Student();
    }
    
    @Test
    public void testClass() {
        this.admin.createClass("Test1", 2017, "Instructor", 15);
        assertEquals(this.admin.getClassCapacity("Test1", 2017), 15);
    }
    
    @Test
    public void testClass1() {
        this.admin.createClass("Test1", 2017, "Instructor", 15);
        assertEquals(this.admin.getClassInstructor("Test1", 2017), "Instructor");
    }
    
    // className/year pair must be unique
    // testing with same className, year, and instructor, but different capacity
    @Test
    public void testUniqueClass() {
        this.admin.createClass("Test1", 2017, "Instructor", 15);
        this.admin.createClass("Test1", 2017, "Instructor", 16);
        assertEquals(this.admin.getClassCapacity("Test1", 2017), 15);
    }
    
    @Test
    public void testUniqueClass0() {
        this.admin.createClass("Test1", 2017, "Instructor", 15);
        this.admin.createClass("Test1", 2017, "Instructor1", 16);
        assertEquals(this.admin.getClassInstructor("Test1", 2017), "Instructor");
    }
    
    // className/year pair must be unique
    // testing with same className, year, but different instructor and capacity
    @Test
    public void testUniqueClass1() {
        this.admin.createClass("Test1", 2017, "Instructor", 15);
        this.admin.createClass("Test1", 2017, "Instructor1", 16);
        assertEquals(this.admin.getClassCapacity("Test1", 2017), 15);
    }
    
    @Test
    public void testUniqueClass11() {
        this.admin.createClass("Test1", 2017, "Instructor", 15);
        this.admin.createClass("Test1", 2017, "Instructor1", 16);
        assertEquals(this.admin.getClassInstructor("Test1", 2017), "Instructor");
    }
    
    // no instructor can be assigned to more than two courses in a year
    // test ability to assign instructor for two courses in a year
    @Test
    public void testInstructorTeach2() {
        this.admin.createClass("Test1", 2017, "Instructor", 15);
        this.admin.createClass("Test2", 2017, "Instructor", 15);
        assertTrue(this.admin.classExists("Test2", 2017));
    }
    
    // no instructor can be assigned to more than two courses in a year
    // test that third course course assigned to an instructor in a year is not created
    @Test
    public void testInstructorTeach3() {
        this.admin.createClass("Test1", 2017, "Instructor", 15);
        assertTrue(this.admin.classExists("Test1", 2017));
        this.admin.createClass("Test2", 2017, "Instructor", 15);
        assertTrue(this.admin.classExists("Test2", 2017));
        this.admin.createClass("Test3", 2017, "Instructor", 15);
        assertFalse(this.admin.classExists("Test3", 2017));
    }
    
    // Calendar year in which the course is to be taught, cannot be in the past
    // test ability to add course for current year
    @Test
    public void testMakeClass() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        assertTrue(this.admin.classExists("Test", 2017));
    }

    // Calendar year in which the course is to be taught, cannot be in the past
    // test ability to add course for next year
    @Test
    public void testMakeClass1() {
        this.admin.createClass("Test", 2018, "Instructor", 15);
        assertTrue(this.admin.classExists("Test", 2018));
    }
    
    // Calendar year in which the course is to be taught, cannot be in the past
    // test inability to add course for past year
    @Test
    public void testMakeClass2() {
        this.admin.createClass("Test", 2016, "Instructor", 15);
        assertFalse(this.admin.classExists("Test", 2016));
    }
    
    // Maximum capacity of this class > 0
    // test inability to add course with -1 capacity
    @Test
    public void testNegativeCapcity() {
        this.admin.createClass("Test1", 2017, "Instructor", -1);
        assertFalse(this.admin.classExists("Test1", 2017));
    }
    
    // Maximum capacity of this class > 0
    // test inability to add course with 0 capacity
    @Test
    public void testZeroCapcity() {
        this.admin.createClass("Test1", 2017, "Instructor", 0);
        assertFalse(this.admin.classExists("Test1", 2017));
    } 
    
    // Maximum capacity of this class > 0
    // test ability to add course with 1 capacity
    @Test
    public void testPositiveCapcity() {
        this.admin.createClass("Test1", 2017, "Instructor", 1);
        assertEquals(this.admin.getClassCapacity("Test1", 2017), 1);
    } 
    
    // New capacity of this class, must be at least equal to the number of students enrolled
    // test inability to change capacity to 0
    @Test
    public void testCapcityChangeZero() {
        this.admin.createClass("Test1", 2017, "Instructor", 15);
        this.admin.changeCapacity("Test1", 2017, 0);
        assertEquals(this.admin.getClassCapacity("Test1", 2017), 15);
    } 
    
    // New capacity of this class, must be at least equal to the number of students enrolled
    // test inability to change capacity to -1
    @Test
    public void testCapcityChangeNegative() {
        this.admin.createClass("Test1", 2017, "Instructor", 15);
        this.admin.changeCapacity("Test1", 2017, -1);
        assertEquals(this.admin.getClassCapacity("Test1", 2017), 15);
    } 
    
    // New capacity of this class, must be at least equal to the number of students enrolled
    // test inability to change to capacity 1 less than number of students enrolled
    @Test
    public void testCapcityChangeLessEnrolled() {
        this.admin.createClass("Test1", 2017, "Instructor", 15);
        this.student.registerForClass("Student1", "Test1", 2017);
        this.student.registerForClass("Student2", "Test1", 2017);
        this.admin.changeCapacity("Test1", 2017, 1);
        assertEquals(this.admin.getClassCapacity("Test1", 2017), 15);
    } 
    
    @Test
    public void testCapcityChangeLessEnrolled1() {
        this.admin.createClass("Test1", 2017, "Instructor", 15);
        this.student.registerForClass("Student1", "Test1", 2017);
        this.student.registerForClass("Student2", "Test1", 2017);
        this.student.registerForClass("Student3", "Test1", 2017);
        this.student.dropClass("Student2", "Test1", 2017);
        this.admin.changeCapacity("Test1", 2017, 1);
        assertEquals(this.admin.getClassCapacity("Test1", 2017), 15);
    } 
    
    // New capacity of this class, must be at least equal to the number of students enrolled
    // test ability to change capacity to number of students enrolled
    @Test
    public void testCapcityChangeEqualEnrolled() {
    	this.admin.createClass("Test1", 2017, "Instructor", 15);
    	this.student.registerForClass("Student1", "Test1", 2017);
    	this.student.registerForClass("Student2", "Test1", 2017);
    	this.admin.changeCapacity("Test1", 2017, 2);
    	assertEquals(this.admin.getClassCapacity("Test1", 2017), 2);
    } 
    
    @Test
    public void testCapcityChangeEqualEnrolled0() {
    	this.admin.createClass("Test1", 2017, "Instructor", 15);
    	this.student.registerForClass("Student1", "Test1", 2017);
    	this.student.registerForClass("Student2", "Test1", 2017);
    	this.student.registerForClass("Student3", "Test1", 2017);
    	this.student.dropClass("Student3", "Test1", 2017);
    	this.admin.changeCapacity("Test1", 2017, 2);
    	assertEquals(this.admin.getClassCapacity("Test1", 2017), 2);
    } 
    
    @Test
    public void testCapcityChangeEqualEnrolled1() {
    	this.admin.createClass("Test1", 2017, "Instructor", 2);
    	this.student.registerForClass("Student1", "Test1", 2017);
    	this.student.registerForClass("Student2", "Test1", 2017);
    	this.admin.changeCapacity("Test1", 2017, 2);
    	assertEquals(this.admin.getClassCapacity("Test1", 2017), 2);
    } 
    
    @Test
    public void testCapcityChangeEqualEnrolled2() {
    	this.admin.createClass("Test1", 2017, "Instructor", 2);
    	this.student.registerForClass("Student1", "Test1", 2017);
    	this.student.registerForClass("Student2", "Test1", 2017);
    	this.student.registerForClass("Student3", "Test1", 2017);
    	this.student.dropClass("Student3", "Test1", 2017);
    	this.admin.changeCapacity("Test1", 2017, 2);
    	assertEquals(this.admin.getClassCapacity("Test1", 2017), 2);
    } 
    
    // New capacity of this class, must be at least equal to the number of students enrolled
    // test ability to change capacity to number of students enrolled + 1
    @Test
    public void testCapcityChangeMoreEnrolled() {
    	this.admin.createClass("Test1", 2017, "Instructor", 15);
    	this.student.registerForClass("Student1", "Test1", 2017);
    	this.student.registerForClass("Student2", "Test1", 2017);
    	this.admin.changeCapacity("Test1", 2017, 3);
    	assertEquals(this.admin.getClassCapacity("Test1", 2017), 3);
    } 
    
    @Test
    public void testCapcityChangeMoreEnrolled1() {
    	this.admin.createClass("Test1", 2017, "Instructor", 2);
    	this.student.registerForClass("Student1", "Test1", 2017);
    	this.student.registerForClass("Student2", "Test1", 2017);
    	this.admin.changeCapacity("Test1", 2017, 3);
    	assertEquals(this.admin.getClassCapacity("Test1", 2017), 3);
    } 
    
    // New capacity of this class, must be at least equal to the number of students enrolled
    // test ability to change capacity to the same number as before
    @Test
    public void testCapcityNoChange() {
    	this.admin.createClass("Test1", 2017, "Instructor", 15);
        this.admin.changeCapacity("Test1", 2017, 15);
        assertEquals(this.admin.getClassCapacity("Test1", 2017), 15);
    } 
    
    // New capacity of this class, must be at least equal to the number of students enrolled
    // test ability to change capacity to 1 greater than capacity before
    @Test
    public void testCapcityChangeMore() {
    	this.admin.createClass("Test1", 2017, "Instructor", 15);
        this.admin.changeCapacity("Test1", 2017, 16);
        assertEquals(this.admin.getClassCapacity("Test1", 2017), 16);
    }
    
    @Test
    public void testCapcityChangeMore1() {
    	this.admin.createClass("Test1", 2017, "Instructor", 2);
    	this.student.registerForClass("Student1", "Test1", 2017);
    	this.student.registerForClass("Student2", "Test1", 2017);
        this.admin.changeCapacity("Test1", 2017, 3);
        assertEquals(this.admin.getClassCapacity("Test1", 2017), 3);
    }
    
}
