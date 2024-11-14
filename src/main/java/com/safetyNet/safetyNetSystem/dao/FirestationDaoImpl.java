package com.safetyNet.safetyNetSystem.dao;

import com.safetyNet.safetyNetSystem.model.Firestation;
import com.safetyNet.safetyNetSystem.util.DataLoaderUtil;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class FirestationDaoImpl implements FirestationDao {

    private final List<Firestation> firestations;

    // Inject DataLoaderUtil pour charger les données au démarrage
    public FirestationDaoImpl(DataLoaderUtil dataLoaderUtil) {
        this.firestations = dataLoaderUtil.loadData().getFirestations();  // Charge les données du JSON
    }

    @Override
    public List<Firestation> findAll() {
        return firestations;
    }

    @Override
    public Optional<Firestation> findByAddress(String address) {
        return firestations.stream()
                .filter(firestation -> firestation.getAddress().equals(address))
                .findFirst();
    }

    @Override
    public List<Firestation> findByStationNumber(String stationNumber) {
        return firestations.stream()
                .filter(firestation -> firestation.getStation().equals(stationNumber))
                .collect(Collectors.toList());
    }

    @Override
    public void addFirestation(Firestation firestation) {
        firestations.add(firestation);
    }

    @Override
    public boolean deleteFirestation(String address) {
        return firestations.removeIf(firestation -> firestation.getAddress().equals(address));
    }

    @Override
    public boolean updateFirestation(String address, Firestation updatedFirestation) {
        return firestations.stream()
                .filter(firestation -> firestation.getAddress().equals(address))
                .findFirst()
                .map(firestation -> {
                    firestation.setStation(updatedFirestation.getStation());
                    return true;
                })
                .orElse(false);
    }
}
