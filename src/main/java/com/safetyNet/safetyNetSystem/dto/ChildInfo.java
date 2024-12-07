package com.safetyNet.safetyNetSystem.dto;

/**
 * DTO (Data Transfer Object) représentant les informations d'un enfant.
 * Contient les attributs relatifs à un enfant, à savoir son prénom, son nom de famille et son âge.
 */
public class ChildInfo {
    private String firstName;
    private String lastName;
    private int age; // L'âge de l'enfant

    /**
     * Constructeur pour initialiser un objet ChildInfo avec un prénom, un nom de famille et un âge.
     *
     * @param firstName Le prénom de l'enfant.
     * @param lastName Le nom de famille de l'enfant.
     * @param age L'âge de l'enfant.
     */
    public ChildInfo(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age; // Initialiser l'âge
    }

    /**
     * Récupère le prénom de l'enfant.
     *
     * @return Le prénom de l'enfant.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Modifie le prénom de l'enfant.
     *
     * @param firstName Le nouveau prénom de l'enfant.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Récupère le nom de famille de l'enfant.
     *
     * @return Le nom de famille de l'enfant.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Modifie le nom de famille de l'enfant.
     *
     * @param lastName Le nouveau nom de famille de l'enfant.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Récupère l'âge de l'enfant.
     *
     * @return L'âge de l'enfant.
     */
    public int getAge() {
        return age;
    }

    /**
     * Modifie l'âge de l'enfant.
     *
     * @param age Le nouvel âge de l'enfant.
     */
    public void setAge(int age) {
        this.age = age;
    }
}
