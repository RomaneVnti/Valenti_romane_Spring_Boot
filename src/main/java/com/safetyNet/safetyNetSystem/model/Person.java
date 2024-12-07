package com.safetyNet.safetyNetSystem.model;

/**
 * Représente une personne avec ses informations personnelles, telles que le prénom, le nom,
 * l'adresse, la ville, le code postal, le téléphone et l'email.
 */
public class Person {

    private String firstName;  // Prénom de la personne
    private String lastName;   // Nom de la personne
    private String address;    // Adresse de la personne
    private String city;       // Ville de la personne
    private String zip;        // Code postal de la personne
    private String phone;      // Numéro de téléphone de la personne
    private String email;      // Email de la personne

    /**
     * Constructeur par défaut pour la classe Person.
     * Nécessaire pour certaines instanciations comme celles avec des frameworks de sérialisation.
     */
    public Person() {
    }

    /**
     * Constructeur pour initialiser une personne avec toutes les informations nécessaires.
     *
     * @param firstName Le prénom de la personne.
     * @param lastName Le nom de la personne.
     * @param address L'adresse de la personne.
     * @param city La ville de la personne.
     * @param zip Le code postal de la personne.
     * @param phone Le numéro de téléphone de la personne.
     * @param email L'email de la personne.
     */
    public Person(String firstName, String lastName, String address, String city, String zip, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.zip = zip;
        this.phone = phone;
        this.email = email;
    }

    /**
     * Récupère le prénom de la personne.
     *
     * @return Le prénom de la personne.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Définit le prénom de la personne.
     *
     * @param firstName Le prénom à définir.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Récupère le nom de la personne.
     *
     * @return Le nom de la personne.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Définit le nom de la personne.
     *
     * @param lastName Le nom à définir.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Récupère l'adresse de la personne.
     *
     * @return L'adresse de la personne.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Définit l'adresse de la personne.
     *
     * @param address L'adresse à définir.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Récupère la ville de la personne.
     *
     * @return La ville de la personne.
     */
    public String getCity() {
        return city;
    }

    /**
     * Définit la ville de la personne.
     *
     * @param city La ville à définir.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Récupère le code postal de la personne.
     *
     * @return Le code postal de la personne.
     */
    public String getZip() {
        return zip;
    }

    /**
     * Définit le code postal de la personne.
     *
     * @param zip Le code postal à définir.
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * Récupère le numéro de téléphone de la personne.
     *
     * @return Le numéro de téléphone de la personne.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Définit le numéro de téléphone de la personne.
     *
     * @param phone Le numéro de téléphone à définir.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Récupère l'email de la personne.
     *
     * @return L'email de la personne.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Définit l'email de la personne.
     *
     * @param email L'email à définir.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Méthode toString() pour obtenir une représentation sous forme de chaîne de caractères
     * de l'objet Person.
     *
     * @return Une chaîne de caractères représentant l'objet Person.
     */
    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", zip='" + zip + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
