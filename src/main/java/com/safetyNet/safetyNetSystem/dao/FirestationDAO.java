package com.safetyNet.safetyNetSystem.dao;

import com.safetyNet.safetyNetSystem.model.Firestation;
import com.safetyNet.safetyNetSystem.util.DataLoaderUtil;
import org.springframework.stereotype.Repository;
import com.safetyNet.safetyNetSystem.model.DataContainer;
import java.util.List;
import java.util.Optional;

@Repository
public class FirestationDAO {

    private final DataLoaderUtil dataLoaderUtil;
    private final DataContainer dataContainer;

    public FirestationDAO(DataLoaderUtil dataLoaderUtil) {
        this.dataLoaderUtil = dataLoaderUtil;
        this.dataContainer = dataLoaderUtil.loadData();  // Assurez-vous que la méthode loadData charge bien le DataContainer.
    }

    // Récupérer toutes les casernes
    public List<Firestation> getAllFirestations() {
        return dataContainer.getFirestations();
    }

    // Ajouter une caserne
    public void addFirestation(Firestation firestation) {
        dataContainer.getFirestations().add(firestation);
        dataLoaderUtil.saveData(dataContainer);
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
            dataLoaderUtil.saveData(dataContainer);
            return Optional.of(firestation);
        }

        return Optional.empty();
    }

    // Supprimer une caserne par son adresse
    public boolean deleteFirestation(String address) {
        List<Firestation> firestations = dataContainer.getFirestations();
        boolean removed = firestations.removeIf(f -> f.getAddress().equals(address));

        if (removed) {
            dataLoaderUtil.saveData(dataContainer);
        }
        return removed;
    }
}
