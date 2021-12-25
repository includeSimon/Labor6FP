package com.labor.labor6fp.Model;
import java.util.ArrayList;
import java.util.List;

public class Teacher extends com.labor.labor6fp.Model.Person {
    public List<Course> courses;
    private int id;

    public Teacher(int id, String firstName, String lastName) {
        super(firstName, lastName);
        this.courses = new ArrayList<>();
        this.id=id;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Teacher " +
                "first name = " + firstName +
                " last name = " + lastName +
                "courses=" + courses +
                ", id=" + id +
                '}';
    }

    /**
     * Method to check if two objects of type Teacher have the same id
     * @param other the other Object that is compared
     * @return true objects are identical, false otherwise
     */
    public boolean equals(Teacher other) {
        return this.id ==  other.getId();
    }

}
