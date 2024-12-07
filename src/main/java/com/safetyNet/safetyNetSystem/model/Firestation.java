package com.safetyNet.safetyNetSystem.model;

/**
 * Classe représentant une caserne de pompiers.
 * Elle contient les informations relatives à l'adresse de la caserne et à son numéro de station.
 */
public class Firestation {

    private String address;  // Adresse de la caserne de pompiers
    private String station;  // Numéro de la station de pompiers

    /**
     * Constructeur par défaut pour la classe Firestation.
     * Nécessaire pour certaines instanciations, comme celles avec des frameworks de sérialisation.
     */
    public Firestation() {
    }

    /**
     * Constructeur pour initialiser une caserne de pompiers avec une adresse et un numéro de station.
     *
     * @param address L'adresse de la caserne de pompiers.
     * @param station Le numéro de la station de pompiers.
     */
    public Firestation(String address, String station) {
        this.address = address;
        this.station = station;
    }

    /**
     * Récupère l'adresse de la caserne de pompiers.
     *
     * @return L'adresse de la caserne de pompiers.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Définit l'adresse de la caserne de pompiers.
     *
     * @param address L'adresse à définir.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Récupère le numéro de la station de pompiers.
     *
     * @return Le numéro de la station de pompiers.
     */
    public String getStation() {
        return station;
    }

    /**
     * Définit le numéro de la station de pompiers.
     *
     * @param station Le numéro de station à définir.
     */
    public void setStation(String station) {
        this.station = station;
    }

    /**
     * Méthode toString() pour obtenir une représentation sous forme de chaîne de caractères
     * de la caserne de pompiers.
     *
     * @return Une chaîne de caractères représentant l'objet Firestation.
     */
    @Override
    public String toString() {
        return "Firestation{" +
                "address='" + address + '\'' +
                ", station='" + station + '\'' +
                '}';
    }
}
