package com.safetyNet.safetyNetSystem.dto;

public class ChildInfo {
    private String firstName;
    private String lastName;
    private int age; // Ajouter l'âge

    public ChildInfo(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age; // Initialiser l'âge
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
