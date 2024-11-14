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

@Service
public class FirestationService {

    private final FirestationDAO firestationDAO;
    private final PersonService personService; // Injection de PersonService


    private final MedicalRecordService medicalRecordService;

    public FirestationService(FirestationDAO firestationDAO, MedicalRecordService medicalRecordService, PersonService personService) {
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
        // Appeler PersonService pour obtenir toutes les personnes
        List<Person> allPersons = personService.getAllPersons();  // Appel de PersonService

        // Récupérer toutes les casernes (on suppose que tu as un moyen de récupérer la caserne avec son numéro)
        List<Firestation> firestations = firestationDAO.getAllFirestations();

        // Trouver l'adresse correspondant au numéro de la caserne
        String addressForStation = firestations.stream()
                .filter(firestation -> firestation.getStation().equals(stationNumber))
                .map(Firestation::getAddress)
                .findFirst()
                .orElse(null); // Si la station n'existe pas, retourner null

        if (addressForStation == null) {
            // Si la station n'a pas d'adresse, retourner une réponse vide ou gérer l'erreur
            return new FirestationResponse(new ArrayList<>(), 0, 0); // Retourne une réponse vide avec 0 adultes et enfants
        }

        // Filtrer les personnes par adresse de la caserne
        List<Person> personsCoveredByStation = allPersons.stream()
                .filter(person -> person.getAddress().equals(addressForStation)) // Comparer l'adresse de la personne
                .toList();

        // Convertir les objets Person en PersonInfo
        List<PersonInfo> personInfoList = new ArrayList<>();
        int numberOfAdults = 0;
        int numberOfChildren = 0;

        for (Person person : personsCoveredByStation) {
            Optional<MedicalRecord> medicalRecordOptional = medicalRecordService.getMedicalRecordByPerson(person);
            if (medicalRecordOptional.isPresent()) {
                MedicalRecord medicalRecord = medicalRecordOptional.get();
                int age = DateUtil.calculateAge(medicalRecord.getBirthdate());

                // Créer un objet PersonInfo et l'ajouter à la liste
                PersonInfo personInfo = new PersonInfo(
                        person.getFirstName(),
                        person.getLastName(),
                        person.getAddress(),
                        person.getPhone()
                );
                personInfoList.add(personInfo);

                // Compter le nombre d'adultes et d'enfants
                if (age < 18) {
                    numberOfChildren++;
                } else {
                    numberOfAdults++;
                }
            }
        }

        // Créer la réponse FirestationResponse avec la liste des personnes, le nombre d'adultes et d'enfants
        return new FirestationResponse(personInfoList, numberOfAdults, numberOfChildren);
    }


}
