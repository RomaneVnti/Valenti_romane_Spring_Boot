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

/**
 * Service pour la gestion des personnes.
 * Cette classe fournit des méthodes pour interagir avec les données des personnes,
 * y compris l'ajout, la mise à jour, la suppression et la récupération des informations
 * relatives aux personnes et à leurs antécédents médicaux.
 */
@Service
public class PersonService {

    private final PersonDAO personDAO;

    @Lazy
    private final MedicalRecordService medicalRecordService;

    /**
     * Constructeur du service des personnes.
     * @param personDAO Le DAO pour gérer les personnes.
     * @param medicalRecordService Le service pour gérer les dossiers médicaux.
     */
    @Autowired
    public PersonService(PersonDAO personDAO, @Lazy MedicalRecordService medicalRecordService) {
        this.personDAO = personDAO;
        this.medicalRecordService = medicalRecordService;
    }

    /**
     * Récupère toutes les personnes.
     * @return La liste de toutes les personnes.
     */
    public List<Person> getAllPersons() {
        return personDAO.getAllPersons();
    }

    /**
     * Ajoute une nouvelle personne.
     * @param person La personne à ajouter.
     */
    public void addPerson(Person person) {
        personDAO.addPerson(person);
    }

    /**
     * Met à jour les informations d'une personne.
     * @param firstName Le prénom de la personne.
     * @param lastName Le nom de famille de la personne.
     * @param updatedPerson Les nouvelles informations de la personne.
     * @return L'objet Person mis à jour, s'il existe.
     */
    public Optional<Person> updatePerson(String firstName, String lastName, Person updatedPerson) {
        return personDAO.updatePerson(firstName, lastName, updatedPerson);
    }

    /**
     * Supprime une personne.
     * @param firstName Le prénom de la personne.
     * @param lastName Le nom de famille de la personne.
     * @return True si la personne a été supprimée, sinon false.
     */
    public boolean deletePerson(String firstName, String lastName) {
        return personDAO.deletePerson(firstName, lastName);
    }

    /**
     * Récupère les enfants et les adultes à une adresse donnée.
     * @param address L'adresse à vérifier.
     * @return Un objet ChildrenAlertResponse contenant les enfants et les adultes.
     */
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
                    if (!children.contains(childInfo)) {
                        children.add(childInfo);
                    }
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

    /**
     * Récupère une personne par son prénom et nom de famille.
     * @param firstName Le prénom de la personne.
     * @param lastName Le nom de famille de la personne.
     * @return Une personne si elle existe, sinon un Optional vide.
     */
    public Optional<Person> getPersonByFirstNameAndLastName(String firstName, String lastName) {
        return personDAO.getAllPersons().stream()
                .filter(person -> person.getFirstName().equals(firstName) && person.getLastName().equals(lastName))
                .findFirst();
    }

    /**
     * Récupère les informations des personnes à une adresse donnée avec leurs informations médicales.
     * @param address L'adresse à vérifier.
     * @param includeMedicalInfo Indicateur pour inclure ou non les informations médicales.
     * @return Une liste d'objets PersonInfo contenant les informations des personnes.
     */
    public List<PersonInfo> getPersonsWithMedicalInfoByAddress(String address, boolean includeMedicalInfo) {
        List<Person> personsAtAddress = personDAO.getAllPersons().stream()
                .filter(person -> person.getAddress().equals(address))
                .toList();

        return personsAtAddress.stream()
                .map(person -> {
                    PersonInfo personInfo = new PersonInfo(
                            person.getFirstName(),
                            person.getLastName(),
                            person.getAddress(),
                            person.getPhone()
                    );

                    if (includeMedicalInfo) {
                        Optional<MedicalRecord> medicalRecordOptional = medicalRecordService.getMedicalRecordByPerson(person);
                        if (medicalRecordOptional.isPresent()) {
                            MedicalRecord medicalRecord = medicalRecordOptional.get();
                            MedicalInfo medicalInfo = new MedicalInfo(
                                    medicalRecord.getMedications(),
                                    medicalRecord.getAllergies()
                            );
                            personInfo.setMedicalInfo(medicalInfo);
                        }
                    }
                    return personInfo;
                })
                .collect(Collectors.toList());
    }

    /**
     * Récupère les informations des personnes par leur nom de famille.
     * @param lastName Le nom de famille à rechercher.
     * @return Une liste d'objets PersonInfo contenant les informations des personnes correspondantes.
     */
    public List<PersonInfo> getPersonInfoByLastName(String lastName) {
        List<Person> filteredPersons = personDAO.getAllPersons().stream()
                .filter(person -> person.getLastName().equalsIgnoreCase(lastName))
                .toList();

        List<PersonInfo> personInfoList = new ArrayList<>();

        for (Person person : filteredPersons) {
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
                        person.getEmail()
                );

                personInfo.setMedicalInfo(new MedicalInfo(medications, allergies));
                personInfoList.add(personInfo);
            }
        }

        return personInfoList;
    }

    /**
     * Récupère les emails des habitants d'une ville donnée.
     * @param city La ville à vérifier.
     * @return Une liste des emails des habitants de la ville.
     */
    public List<String> getEmailsByCity(String city) {
        List<Person> allPersons = personDAO.getAllPersons();

        return allPersons.stream()
                .filter(person -> person.getCity().equalsIgnoreCase(city))
                .map(Person::getEmail)
                .collect(Collectors.toList());
    }
}
