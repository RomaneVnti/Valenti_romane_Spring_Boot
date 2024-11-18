package com.safetyNet.safetyNetSystem.service;

import com.safetyNet.safetyNetSystem.dao.FirestationDAO;
import com.safetyNet.safetyNetSystem.dto.FirestationResponse;
import com.safetyNet.safetyNetSystem.dto.PersonInfo;
import com.safetyNet.safetyNetSystem.model.Firestation;
import com.safetyNet.safetyNetSystem.model.Person;
import com.safetyNet.safetyNetSystem.model.MedicalRecord;
import com.safetyNet.safetyNetSystem.util.DateUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class FirestationService {

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

    // Utiliser le DAO pour récupérer toutes les casernes
    public List<Firestation> getAllFirestations() {
        return firestationDAO.getAllFirestations();
    }

    // Utiliser le DAO pour ajouter une caserne
    public void addFirestation(Firestation firestation) {
        firestationDAO.addFirestation(firestation);
    }

    // Utiliser le DAO pour mettre à jour une caserne
    public Optional<Firestation> updateFirestation(String address, Firestation updatedFirestation) {
        return firestationDAO.updateFirestation(address, updatedFirestation);
    }

    // Utiliser le DAO pour supprimer une caserne
    public boolean deleteFirestation(String address) {
        return firestationDAO.deleteFirestation(address);
    }

    // Obtenir les personnes couvertes par une caserne
    public FirestationResponse getPersonsCoveredByStation(String stationNumber) {
        // Obtenir toutes les personnes et les casernes
        List<Person> allPersons = personService.getAllPersons();
        List<Firestation> firestations = firestationDAO.getAllFirestations();

        // Trouver l'adresse de la caserne correspondant au stationNumber
        String addressForStation = firestations.stream()
                .filter(firestation -> firestation.getStation().equals(stationNumber))
                .map(Firestation::getAddress)
                .findFirst()
                .orElse(null);

        // Si aucune adresse n'est trouvée pour la caserne, renvoyer une réponse vide
        if (addressForStation == null) {
            return new FirestationResponse(new ArrayList<>(), 0, 0);
        }

        // Filtrer les personnes selon l'adresse de la caserne
        List<Person> personsCoveredByStation = allPersons.stream()
                .filter(person -> person.getAddress().equals(addressForStation))
                .toList();

        // Créer des objets PersonInfo et compter les adultes et enfants
        List<PersonInfo> personInfoList = new ArrayList<>();
        int numberOfAdults = 0;
        int numberOfChildren = 0;

        for (Person person : personsCoveredByStation) {
            // Utiliser la méthode modifiée qui prend un objet Person
            Optional<MedicalRecord> medicalRecordOptional = medicalRecordService.getMedicalRecordByPerson(person);
            if (medicalRecordOptional.isPresent()) {
                MedicalRecord medicalRecord = medicalRecordOptional.get();
                int age = DateUtil.calculateAge(medicalRecord.getBirthdate());

                // Créer un objet PersonInfo avec les données de la personne
                PersonInfo personInfo = new PersonInfo(
                        person.getFirstName(),
                        person.getLastName(),
                        person.getAddress(),
                        person.getPhone()
                );
                personInfoList.add(personInfo);

                // Compter le nombre d'adultes et d'enfants en fonction de l'âge
                if (age < 18) {
                    numberOfChildren++;
                } else {
                    numberOfAdults++;
                }
            }
        }

        // Retourner la réponse avec les informations des personnes et les comptes d'adultes et d'enfants
        return new FirestationResponse(personInfoList, numberOfAdults, numberOfChildren);
    }

    // Obtenir les numéros de téléphone des personnes couvertes par une caserne
    public List<String> getPhoneNumbersByStation(String stationNumber) {
        // Obtenir toutes les personnes et les casernes
        List<Person> allPersons = personService.getAllPersons();
        List<Firestation> firestations = firestationDAO.getAllFirestations();

        // Trouver les adresses correspondant au stationNumber
        List<String> addressesForStation = firestations.stream()
                .filter(firestation -> firestation.getStation().equals(stationNumber))
                .map(Firestation::getAddress)
                .toList();

        // Filtrer les personnes par adresse et collecter leurs numéros de téléphone
        return allPersons.stream()
                .filter(person -> addressesForStation.contains(person.getAddress()))
                .map(Person::getPhone)
                .distinct() // Pour éviter les doublons
                .toList();
    }

}
