package com.safetyNet.safetyNetSystem.service;

import com.safetyNet.safetyNetSystem.model.DataContainer;
import com.safetyNet.safetyNetSystem.model.Firestation;
import com.safetyNet.safetyNetSystem.util.DataLoaderUtil;
import com.safetyNet.safetyNetSystem.dto.FirestationResponse;
import com.safetyNet.safetyNetSystem.dto.PersonInfo;
import com.safetyNet.safetyNetSystem.model.Person;
import com.safetyNet.safetyNetSystem.model.MedicalRecord;
import com.safetyNet.safetyNetSystem.util.DateUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FirestationService {

    private final DataContainer dataContainer;
    private final DataLoaderUtil dataLoaderUtil;
    private final MedicalRecordService medicalRecordService;

    public FirestationService(DataLoaderService dataLoaderService, DataLoaderUtil dataLoaderUtil, MedicalRecordService medicalRecordService) {
        this.dataContainer = dataLoaderService.loadData();
        this.dataLoaderUtil = dataLoaderUtil;
        this.medicalRecordService = medicalRecordService;
    }

    public void addFirestation(Firestation firestation) {
        dataContainer.getFirestations().add(firestation);
        dataLoaderUtil.saveData(dataContainer);
    }

    public Optional<Firestation> updateFirestation(String address, String station) {
        Optional<Firestation> existingFirestation = dataContainer.getFirestations().stream()
                .filter(f -> f.getAddress().equals(address))
                .findFirst();

        existingFirestation.ifPresent(firestationToUpdate -> {
            firestationToUpdate.setStation(station);
            dataLoaderUtil.saveData(dataContainer);
        });

        return existingFirestation;
    }

    public boolean deleteFirestation(String address) {
        boolean removed = dataContainer.getFirestations().removeIf(f -> f.getAddress().equals(address));
        if (removed) {
            dataLoaderUtil.saveData(dataContainer);
        }
        return removed;
    }

    public FirestationResponse getPersonsCoveredByStation(String stationNumber) {
        List<String> addresses = dataContainer.getFirestations().stream()
                .filter(f -> f.getStation().equals(stationNumber))
                .map(Firestation::getAddress)
                .toList();

        List<PersonInfo> personInfoList = new ArrayList<>();
        int numberOfAdults = 0;
        int numberOfChildren = 0;

        for (Person person : dataContainer.getPersons()) {
            if (addresses.contains(person.getAddress())) {
                Optional<MedicalRecord> medicalRecordOptional = medicalRecordService.getMedicalRecordByPerson(person);
                if (medicalRecordOptional.isPresent()) {
                    MedicalRecord medicalRecord = medicalRecordOptional.get();
                    int age = DateUtil.calculateAge(medicalRecord.getBirthdate());
                    if (age > 18) {
                        numberOfAdults++;
                    } else {
                        numberOfChildren++;
                    }

                    personInfoList.add(new PersonInfo(person.getFirstName(), person.getLastName(), person.getAddress(), person.getPhone()));
                }
            }
        }
        return new FirestationResponse(personInfoList, numberOfAdults, numberOfChildren);
    }
}
