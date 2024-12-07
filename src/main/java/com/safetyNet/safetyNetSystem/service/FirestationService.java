package com.safetyNet.safetyNetSystem.service;

import com.safetyNet.safetyNetSystem.dao.FirestationDAO;
import com.safetyNet.safetyNetSystem.dto.FirestationResponse;
import com.safetyNet.safetyNetSystem.dto.FirestationResponseNoCount;
import com.safetyNet.safetyNetSystem.dto.MedicalInfo;
import com.safetyNet.safetyNetSystem.dto.PersonInfo;
import com.safetyNet.safetyNetSystem.model.Firestation;
import com.safetyNet.safetyNetSystem.model.Person;
import com.safetyNet.safetyNetSystem.model.MedicalRecord;
import com.safetyNet.safetyNetSystem.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FirestationService {

    private static final Logger logger = LoggerFactory.getLogger(FirestationService.class);

    private final FirestationDAO firestationDAO;

    @Lazy
    private final PersonService personService;

    @Lazy
    private final MedicalRecordService medicalRecordService;

    @Autowired
    public FirestationService(FirestationDAO firestationDAO, @Lazy MedicalRecordService medicalRecordService, @Lazy PersonService personService) {
        this.firestationDAO = firestationDAO;
        this.medicalRecordService = medicalRecordService;
        this.personService = personService;
    }

    public List<Firestation> getAllFirestations() {
        logger.info("Fetching all firestations");
        List<Firestation> firestations = firestationDAO.getAllFirestations();
        logger.info("Found {} firestations", firestations.size());
        return firestations;
    }

    public void addFirestation(Firestation firestation) {
        logger.info("Adding a new firestation at address: {}", firestation.getAddress());
        firestationDAO.addFirestation(firestation);
        logger.info("Firestation added successfully");
    }

    public Optional<Firestation> updateFirestation(String address, Firestation updatedFirestation) {
        logger.info("Updating firestation at address: {}", address);
        Optional<Firestation> updated = firestationDAO.updateFirestation(address, updatedFirestation);
        if (updated.isPresent()) {
            logger.info("Firestation updated successfully at address: {}", address);
        } else {
            logger.warn("Firestation at address: {} not found for update", address);
        }
        return updated;
    }

    public boolean deleteFirestation(String address) {
        logger.info("Deleting firestation at address: {}", address);
        boolean isDeleted = firestationDAO.deleteFirestation(address);
        if (isDeleted) {
            logger.info("Firestation deleted successfully at address: {}", address);
        } else {
            logger.warn("Firestation at address: {} not found for deletion", address);
        }
        return isDeleted;
    }

    public FirestationResponse getPersonsCoveredByStation(String stationNumber) {
        logger.info("Fetching persons covered by firestation with station number: {}", stationNumber);

        List<Person> allPersons = personService.getAllPersons();
        List<Firestation> firestations = firestationDAO.getAllFirestations();

        String addressForStation = firestations.stream()
                .filter(firestation -> firestation.getStation().equals(stationNumber))
                .map(Firestation::getAddress)
                .findFirst()
                .orElse(null);

        if (addressForStation == null) {
            logger.warn("No firestation found for station number: {}", stationNumber);
            return new FirestationResponse(new ArrayList<>(), 0, 0);
        }

        List<Person> personsCoveredByStation = allPersons.stream()
                .filter(person -> person.getAddress().equals(addressForStation))
                .toList();

        List<PersonInfo> personInfoList = new ArrayList<>();
        int numberOfAdults = 0;
        int numberOfChildren = 0;

        for (Person person : personsCoveredByStation) {
            Optional<MedicalRecord> medicalRecordOptional = medicalRecordService.getMedicalRecordByPerson(person);
            if (medicalRecordOptional.isPresent()) {
                MedicalRecord medicalRecord = medicalRecordOptional.get();
                int age = DateUtil.calculateAge(medicalRecord.getBirthdate());

                PersonInfo personInfo = new PersonInfo(
                        person.getFirstName(),
                        person.getLastName(),
                        person.getAddress(),
                        person.getPhone()
                );
                personInfoList.add(personInfo);

                if (age < 18) {
                    numberOfChildren++;
                } else {
                    numberOfAdults++;
                }
            }
        }

        logger.info("Found {} persons covered by firestation with station number: {}", personInfoList.size(), stationNumber);
        return new FirestationResponse(personInfoList, numberOfAdults, numberOfChildren);
    }

    public List<String> getPhoneNumbersByStation(String stationNumber) {
        logger.info("Fetching phone numbers for persons covered by firestation with station number: {}", stationNumber);

        List<Person> allPersons = personService.getAllPersons();
        List<Firestation> firestations = firestationDAO.getAllFirestations();

        List<String> addressesForStation = firestations.stream()
                .filter(firestation -> firestation.getStation().equals(stationNumber))
                .map(Firestation::getAddress)
                .toList();

        List<String> phoneNumbers = allPersons.stream()
                .filter(person -> addressesForStation.contains(person.getAddress()))
                .map(Person::getPhone)
                .distinct()
                .toList();

        logger.info("Found {} phone numbers for firestation with station number: {}", phoneNumbers.size(), stationNumber);
        return phoneNumbers;
    }

    public FirestationResponseNoCount getFirestationInfoByAddress(String address) {
        logger.info("Fetching firestation info for address: {}", address);

        List<Firestation> firestations = firestationDAO.getAllFirestations();
        Firestation firestation = firestations.stream()
                .filter(f -> f.getAddress().equals(address))
                .findFirst()
                .orElse(null);

        if (firestation == null) {
            logger.warn("No firestation found for address: {}", address);
            return new FirestationResponseNoCount(new ArrayList<>(), null);
        }

        String stationNumber = firestation.getStation();
        List<PersonInfo> personsInfo = personService.getPersonsWithMedicalInfoByAddress(address, true);

        logger.info("Found firestation and persons info for address: {}", address);
        return new FirestationResponseNoCount(personsInfo, stationNumber);
    }

    public List<FirestationResponseNoCount> getFloodedStations(List<String> stations) {
        logger.info("Fetching flooded stations for station numbers: {}", stations);

        List<Firestation> firestations = firestationDAO.getAllFirestations();
        List<FirestationResponseNoCount> floodedStations = new ArrayList<>();

        if (stations.isEmpty()) {
            logger.warn("No stations provided for flooding");
            return floodedStations;
        }

        for (Firestation firestation : firestations) {
            String stationNumber = firestation.getStation();
            if (stations.contains(stationNumber)) {
                String stationAddress = firestation.getAddress();

                List<Person> allPersons = personService.getAllPersons();
                List<Person> personsCoveredByStation = allPersons.stream()
                        .filter(person -> person.getAddress().equals(stationAddress))
                        .toList();

                List<PersonInfo> personInfoList = new ArrayList<>();

                for (Person person : personsCoveredByStation) {
                    Optional<MedicalRecord> medicalRecordOptional = medicalRecordService.getMedicalRecordByPerson(person);
                    if (medicalRecordOptional.isPresent()) {
                        MedicalRecord medicalRecord = medicalRecordOptional.get();
                        int age = DateUtil.calculateAge(medicalRecord.getBirthdate());

                        List<String> medications = medicalRecord.getMedications();
                        List<String> allergies = medicalRecord.getAllergies();

                        PersonInfo personInfo = new PersonInfo(
                                person.getFirstName(),
                                person.getLastName(),
                                person.getAddress(),
                                person.getPhone()
                        );
                        personInfo.setMedicalInfo(new MedicalInfo(medications, allergies));

                        personInfoList.add(personInfo);
                    }
                }

                if (!personInfoList.isEmpty()) {
                    FirestationResponseNoCount firestationResponse = new FirestationResponseNoCount(personInfoList, stationNumber);
                    floodedStations.add(firestationResponse);
                }
            }
        }

        logger.info("Found {} flooded stations", floodedStations.size());
        return floodedStations;
    }
}
