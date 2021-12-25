package com.labor.labor6fp.Model;
import java.util.ArrayList;
import java.util.List;

public class Student extends com.labor.labor6fp.Model.Person {
    private int totalCredits;
    private List<Course> enrolledCourses;
    private int id;

    public Student(int id, String firstName, String lastName) {
        super(firstName, lastName);
        this.enrolledCourses = new ArrayList<>();
        this.id = id;
    }

    public int getTotalCredits() {
        return totalCredits;
    }

    public void setTotalCredits(int totalCredits) {
        this.totalCredits = totalCredits;
    }

    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void setEnrolledCourses(List<Course> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Student " +
                "first name = " + firstName +
                " last name = " + lastName +
                "studentId=" + id +
                ", totalCredits=" + totalCredits +
                ", enrolledCourses=" + enrolledCourses;
    }

    /**
     * Method to check if two objects of type Student have the same id
     * @param other the other object that is compared
     * @return true if the objects match, else false
     */
    public boolean equals(Student other) {
        return this.id == other.getId();
    }
}
