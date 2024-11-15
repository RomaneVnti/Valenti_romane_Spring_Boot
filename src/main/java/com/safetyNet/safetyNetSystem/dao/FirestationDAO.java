package com.safetyNet.safetyNetSystem.dao;

import com.safetyNet.safetyNetSystem.model.Firestation;
import com.safetyNet.safetyNetSystem.service.DataLoaderService;
import com.safetyNet.safetyNetSystem.util.DataLoaderUtil;
import org.springframework.stereotype.Repository;
import com.safetyNet.safetyNetSystem.model.DataContainer;
import java.util.List;
import java.util.Optional;

@Repository
public class FirestationDAO {

    private final DataLoaderUtil dataLoaderUtil;
    private final DataLoaderService dataLoaderService;

    private DataContainer dataContainer;

    // Constructeur pour initialiser DataLoaderUtil et DataLoaderService
    public FirestationDAO(DataLoaderUtil dataLoaderUtil, DataLoaderService dataLoaderService) {
        this.dataLoaderUtil = dataLoaderUtil;
        this.dataLoaderService = dataLoaderService;
        // Charger les données via le DataLoaderService
        this.dataContainer = dataLoaderService.getDataContainer(); // Utilisation de DataLoaderService pour obtenir le DataContainer
    }

    // Récupérer toutes les casernes
    public List<Firestation> getAllFirestations() {
        return dataContainer.getFirestations();
    }

    // Ajouter une caserne
    public void addFirestation(Firestation firestation) {
        dataContainer.getFirestations().add(firestation);
        dataLoaderService.saveData();  // Sauvegarder les données après modification
    }

    // Mettre à jour une caserne par son adresse
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

    // Supprimer une caserne par son adresse
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
