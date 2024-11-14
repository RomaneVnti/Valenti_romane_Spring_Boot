package com.safetyNet.safetyNetSystem.dao;

import com.safetyNet.safetyNetSystem.model.Firestation;

import java.util.List;
import java.util.Optional;

public interface FirestationDao {

    List<Firestation> findAll();

    Optional<Firestation> findByAddress(String address);

    List<Firestation> findByStationNumber(String stationNumber);

    void addFirestation(Firestation firestation);

    boolean deleteFirestation(String address);

    boolean updateFirestation(String address, Firestation updatedFirestation);
}
