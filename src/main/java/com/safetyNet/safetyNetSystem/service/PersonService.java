package com.safetyNet.safetyNetSystem.service;

import com.safetyNet.safetyNetSystem.model.DataContainer;
import com.safetyNet.safetyNetSystem.model.Person;
import com.safetyNet.safetyNetSystem.util.DataLoaderUtil;
import org.springframework.stereotype.Service;
import com.safetyNet.safetyNetSystem.dto.ChildrenAlertResponse;
import com.safetyNet.safetyNetSystem.dto.PersonInfo;
import com.safetyNet.safetyNetSystem.model.MedicalRecord;
import com.safetyNet.safetyNetSystem.util.DateUtil;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

@Service
public class PersonService {

    private final DataContainer dataContainer;
    private final DataLoaderUtil dataLoaderUtil; // Ajouter l'instance de DataLoaderUtil

    private final MedicalRecordService medicalRecordService;

    public PersonService(DataLoaderService dataLoaderService, DataLoaderUtil dataLoaderUtil, MedicalRecordService medicalRecordService) {
        this.dataContainer = dataLoaderService.loadData(); // Charger les données au démarrage
        this.dataLoaderUtil = dataLoaderUtil; // Initialiser l'utilitaire de chargement et de sauvegarde des données
        this.medicalRecordService = medicalRecordService;
    }

    public List<Person> getAllPersons() {
        return dataContainer.getPersons();
    }

    public void addPerson(Person person) {
        dataContainer.getPersons().add(person);
        dataLoaderUtil.saveData(dataContainer); // Sauvegarder les données après ajout
    }

    public Optional<Person> updatePerson(String firstName, String lastName, Person updatedPerson) {
        List<Person> persons = dataContainer.getPersons();

        Optional<Person> existingPerson = persons.stream()
                .filter(p -> p.getFirstName().equals(firstName) && p.getLastName().equals(lastName))
                .findFirst();

        if (existingPerson.isPresent()) {
            Person person = existingPerson.get();

            person.setFirstName(updatedPerson.getFirstName());
            person.setLastName(updatedPerson.getLastName());
            person.setAddress(updatedPerson.getAddress());
            person.setCity(updatedPerson.getCity());  // Mise à jour de la ville
            person.setZip(updatedPerson.getZip());    // Mise à jour du code postal
            person.setPhone(updatedPerson.getPhone());
            person.setEmail(updatedPerson.getEmail());

            dataLoaderUtil.saveData(dataContainer); // Sauvegarder les données après mise à jour
            return Optional.of(person);
        }

        return Optional.empty();
    }


    public boolean deletePerson(String firstName, String lastName) {
        List<Person> persons = dataContainer.getPersons();
        boolean removed = persons.removeIf(p -> p.getFirstName().equals(firstName) && p.getLastName().equals(lastName));

        if (removed) {
            dataLoaderUtil.saveData(dataContainer); // Sauvegarder les données après suppression
        }

        return removed;
    }


    public ChildrenAlertResponse getChildAlertByAddress(String address) {
        List<Person> personsAtAddress = dataContainer.getPersons().stream()
                .filter(person -> person.getAddress().equals(address))
                .toList();

        List<PersonInfo> children = new ArrayList<>();
        List<PersonInfo> adults = new ArrayList<>();

        for (Person person : personsAtAddress) {
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

                if (age < 18) {
                    children.add(personInfo);
                } else {
                    adults.add(personInfo);
                }
            }
        }

        return new ChildrenAlertResponse(children, adults);
    }
}
