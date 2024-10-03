package com.safetyNet.safetyNetSystem.service;

import com.safetyNet.safetyNetSystem.model.DataContainer;
import com.safetyNet.safetyNetSystem.model.Firestation;
import com.safetyNet.safetyNetSystem.util.DataLoaderUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FirestationService {

    private final DataContainer dataContainer;
    private final DataLoaderUtil dataLoaderUtil;

    public FirestationService(DataLoaderService dataLoaderService, DataLoaderUtil dataLoaderUtil) {
        this.dataContainer = dataLoaderService.loadData();
        this.dataLoaderUtil = dataLoaderUtil;
    }

    // Ajouter une caserne/adresse
    public void addFirestation(Firestation firestation) {
        dataContainer.getFirestations().add(firestation);
        dataLoaderUtil.saveData(dataContainer);
    }

    // Mettre à jour le numéro de caserne d'une adresse
    public Optional<Firestation> updateFirestation(String address, String station) {
        List<Firestation> firestations = dataContainer.getFirestations();

        Optional<Firestation> existingFirestation = firestations.stream()
                .filter(f -> f.getAddress().equals(address))
                .findFirst();

        if (existingFirestation.isPresent()) {
            Firestation firestationToUpdate = existingFirestation.get();
            firestationToUpdate.setStation(station);
            dataLoaderUtil.saveData(dataContainer);
            return Optional.of(firestationToUpdate);
        }
        return Optional.empty();
    }

    // Supprimer une caserne/adresse
    public boolean deleteFirestation(String address) {
        List<Firestation> firestations = dataContainer.getFirestations();
        boolean removed = firestations.removeIf(f -> f.getAddress().equals(address));

        if (removed) {
            dataLoaderUtil.saveData(dataContainer);
        }

        return removed;
    }
}
