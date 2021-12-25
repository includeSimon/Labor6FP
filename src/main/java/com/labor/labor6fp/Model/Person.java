package com.labor.labor6fp.Model;

//Person class should be abstract because it is inherited by class 'Student' and 'Teacher'
public abstract class  Person {
    protected String firstName;
    protected String lastName;

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Person " +
                "firstName='" + firstName +
                " lastName='" + lastName;
    }
}