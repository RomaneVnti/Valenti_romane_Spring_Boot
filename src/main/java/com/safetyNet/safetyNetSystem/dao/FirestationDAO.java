package com.safetyNet.safetyNetSystem.dao;

import com.safetyNet.safetyNetSystem.model.Firestation;
import com.safetyNet.safetyNetSystem.service.DataLoaderService;
import org.springframework.stereotype.Repository;
import com.safetyNet.safetyNetSystem.model.DataContainer;
import java.util.List;
import java.util.Optional;

/**
 * DAO (Data Access Object) pour gérer les casernes de pompiers.
 * Ce DAO permet de récupérer, ajouter, mettre à jour et supprimer des casernes dans la base de données.
 * Utilise le DataLoaderService pour charger et sauvegarder les données.
 */
@Repository
public class FirestationDAO {

    private final DataLoaderService dataLoaderService;

    private final DataContainer dataContainer;

    /**
     * Constructeur pour initialiser DataLoaderUtil et DataLoaderService.
     * Charge les données via le DataLoaderService pour initialiser le DataContainer.
     *
     * @param dataLoaderService Service de gestion du chargement et de la sauvegarde des données.
     */
    public FirestationDAO(DataLoaderService dataLoaderService) {
        this.dataLoaderService = dataLoaderService;
        // Charger les données via le DataLoaderService
        this.dataContainer = dataLoaderService.getDataContainer(); // Utilisation de DataLoaderService pour obtenir le DataContainer
    }

    /**
     * Récupère toutes les casernes de pompiers.
     *
     * @return Une liste de toutes les casernes.
     */
    public List<Firestation> getAllFirestations() {
        return dataContainer.getFirestations();
    }

    /**
     * Ajoute une nouvelle caserne de pompiers.
     *
     * @param firestation La caserne à ajouter.
     */
    public void addFirestation(Firestation firestation) {
        dataContainer.getFirestations().add(firestation);
        dataLoaderService.saveData();  // Sauvegarder les données après modification
    }

    /**
     * Met à jour une caserne de pompiers par son adresse.
     *
     * @param address L'adresse de la caserne à mettre à jour.
     * @param updatedFirestation L'objet Firestation avec les nouvelles informations.
     * @return Un objet Optional contenant la caserne mise à jour si trouvée, sinon un Optional vide.
     */
    public Optional<Firestation> updateFirestation(String address, Firestation updatedFirestation) {
        List<Firestation> firestations = dataContainer.getFirestations();
        Optional<Firestation> existingFirestation = firestations.stream()
                .filter(f -> f.getAddress().equals(address))
                .findFirst();

        if (existingFirestation.isPresent()) {
            Firestation firestation = existingFirestation.get();
            firestation.setStation(updatedFirestation.getStation());
            dataLoaderService.saveData();  // Sauvegarder les données après modification
            return Optional.of(firestation);
        }

        return Optional.empty();
    }

    /**
     * Supprime une caserne de pompiers par son adresse.
     *
     * @param address L'adresse de la caserne à supprimer.
     * @return true si la caserne a été supprimée, false sinon.
     */
    public boolean deleteFirestation(String address) {
        List<Firestation> firestations = dataContainer.getFirestations();

        // Supprimer immédiatement la station de pompiers correspondante
        boolean removed = firestations.removeIf(f -> f.getAddress().equals(address));

        if (removed) {
            // Sauvegarder immédiatement les données dans le fichier JSON
            dataLoaderService.saveData();
        }

        return removed;
    }

}
