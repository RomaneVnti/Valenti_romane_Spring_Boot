package com.safetyNet.safetyNetSystem.service;

import com.safetyNet.safetyNetSystem.dao.PersonDAO;
import com.safetyNet.safetyNetSystem.model.Person;
import com.safetyNet.safetyNetSystem.dto.ChildrenAlertResponse;
import com.safetyNet.safetyNetSystem.dto.PersonInfo;
import com.safetyNet.safetyNetSystem.util.DateUtil;
import com.safetyNet.safetyNetSystem.dto.ChildInfo;
import com.safetyNet.safetyNetSystem.model.MedicalRecord;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

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
                    ChildInfo childInfo = new ChildInfo(
                            person.getFirstName(),
                            person.getLastName(),
                            age
                    );
                    children.add(childInfo);
                } else {
                    PersonInfo personInfo = new PersonInfo(
                            person.getFirstName(),
                            person.getLastName(),
                            person.getAddress(),
                            person.getPhone()
                    );
                    adults.add(personInfo);
                }
            }
        }

        return new ChildrenAlertResponse(children, adults);
    }

    public Optional<Person> getPersonByFirstNameAndLastName(String firstName, String lastName) {
        return personDAO.getAllPersons().stream()
                .filter(person -> person.getFirstName().equals(firstName) && person.getLastName().equals(lastName))
                .findFirst();
    }
}
