package com.safetyNet.safetyNetSystem.service;

import com.safetyNet.safetyNetSystem.dao.PersonDAO;
import com.safetyNet.safetyNetSystem.dto.*;
import com.safetyNet.safetyNetSystem.model.Person;
import com.safetyNet.safetyNetSystem.util.DateUtil;
import com.safetyNet.safetyNetSystem.model.MedicalRecord;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

@Service
public class PersonService {

    private final PersonDAO personDAO;

    @Lazy  // Ajout de @Lazy sur MedicalRecordService pour éviter la dépendance circulaire immédiate
    private final MedicalRecordService medicalRecordService;

    @Autowired
    public PersonService(PersonDAO personDAO, @Lazy MedicalRecordService medicalRecordService) {
        this.personDAO = personDAO;
        this.medicalRecordService = medicalRecordService;
    }

    // Utiliser le DAO pour récupérer toutes les personnes
    public List<Person> getAllPersons() {
        return personDAO.getAllPersons();
    }

    // Utiliser le DAO pour ajouter une personne
    public void addPerson(Person person) {
        personDAO.addPerson(person);
    }

    // Utiliser le DAO pour mettre à jour une personne
    public Optional<Person> updatePerson(String firstName, String lastName, Person updatedPerson) {
        return personDAO.updatePerson(firstName, lastName, updatedPerson);
    }

    // Utiliser le DAO pour supprimer une personne
    public boolean deletePerson(String firstName, String lastName) {
        return personDAO.deletePerson(firstName, lastName);
    }

    public ChildrenAlertResponse getChildAlertByAddress(String address) {
        List<Person> personsAtAddress = personDAO.getAllPersons().stream()
                .filter(person -> person.getAddress().equals(address))
                .toList();

        List<ChildInfo> children = new ArrayList<>();
        List<PersonInfo> adults = new ArrayList<>();

        for (Person person : personsAtAddress) {
            Optional<MedicalRecord> medicalRecordOptional = medicalRecordService.getMedicalRecordByPerson(person);
            if (medicalRecordOptional.isPresent()) {
                MedicalRecord medicalRecord = medicalRecordOptional.get();
                int age = DateUtil.calculateAge(medicalRecord.getBirthdate());

                if (age < 18) {
                    addChildInfo(children, person, age);
                } else {
                    addAdultInfo(adults, person);
                }
            }
        }

        return new ChildrenAlertResponse(children, adults);
    }

    private void addChildInfo(List<ChildInfo> children, Person person, int age) {
        ChildInfo childInfo = new ChildInfo(
                person.getFirstName(),
                person.getLastName(),
                age
        );
        if (!children.contains(childInfo)) {
            children.add(childInfo);
        }
    }

    private void addAdultInfo(List<PersonInfo> adults, Person person) {
        PersonInfo personInfo = new PersonInfo(
                person.getFirstName(),
                person.getLastName(),
                person.getAddress(),
                person.getPhone()
        );
        adults.add(personInfo);
    }

    public Optional<Person> getPersonByFirstNameAndLastName(String firstName, String lastName) {
        return personDAO.getAllPersons().stream()
                .filter(person -> person.getFirstName().equals(firstName) && person.getLastName().equals(lastName))
                .findFirst();
    }

    public List<PersonInfo> getPersonsWithMedicalInfoByAddress(String address, boolean includeMedicalInfo) {
        List<Person> personsAtAddress = personDAO.getAllPersons().stream()
                .filter(person -> person.getAddress().equals(address))
                .toList();

        return personsAtAddress.stream()
                .map(person -> createPersonInfoWithMedicalInfo(person, includeMedicalInfo))
                .collect(Collectors.toList());
    }

    private PersonInfo createPersonInfoWithMedicalInfo(Person person, boolean includeMedicalInfo) {
        PersonInfo personInfo = new PersonInfo(
                person.getFirstName(),
                person.getLastName(),
                person.getAddress(),
                person.getPhone()
        );

        if (includeMedicalInfo) {
            Optional<MedicalRecord> medicalRecordOptional = medicalRecordService.getMedicalRecordByPerson(person);
            medicalRecordOptional.ifPresent(medicalRecord -> {
                MedicalInfo medicalInfo = new MedicalInfo(
                        medicalRecord.getMedications(),
                        medicalRecord.getAllergies()
                );
                personInfo.setMedicalInfo(medicalInfo);
            });
        }

        return personInfo;
    }

    public List<PersonInfo> getPersonInfoByLastName(String lastName) {
        List<Person> filteredPersons = personDAO.getAllPersons().stream()
                .filter(person -> person.getLastName().equalsIgnoreCase(lastName))
                .toList();

        List<PersonInfo> personInfoList = new ArrayList<>();
        for (Person person : filteredPersons) {
            Optional<MedicalRecord> medicalRecordOptional = medicalRecordService.getMedicalRecordByPerson(person);
            medicalRecordOptional.ifPresent(medicalRecord -> {
                PersonInfo personInfo = createPersonInfoWithMedicalInfo(person, true);
                personInfoList.add(personInfo);
            });
        }
        return personInfoList;
    }

    public List<String> getEmailsByCity(String city) {
        return personDAO.getAllPersons().stream()
                .filter(person -> person.getCity().equalsIgnoreCase(city))
                .map(Person::getEmail)
                .collect(Collectors.toList());
    }
}
