package com.safetyNet.safetyNetSystem.dao;

import com.safetyNet.safetyNetSystem.model.Person;
import com.safetyNet.safetyNetSystem.service.DataLoaderService;
import com.safetyNet.safetyNetSystem.util.DataLoaderUtil;
import org.springframework.stereotype.Repository;
import com.safetyNet.safetyNetSystem.model.DataContainer;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PersonDAO {

    private final DataLoaderUtil dataLoaderUtil;
    private final DataLoaderService dataLoaderService;
    private DataContainer dataContainer;

    // Constructeur pour initialiser DataLoaderUtil et DataLoaderService
    public PersonDAO(DataLoaderUtil dataLoaderUtil, DataLoaderService dataLoaderService) {
        this.dataLoaderUtil = dataLoaderUtil;
        this.dataLoaderService = dataLoaderService;
        // Charger les données via DataLoaderService
        this.dataContainer = dataLoaderService.getDataContainer();  // Utilisation de DataLoaderService pour obtenir le DataContainer
    }

    // Récupérer toutes les personnes
    public List<Person> getAllPersons() {
        return dataContainer.getPersons();
    }

    // Ajouter une personne
    public void addPerson(Person person) {
        dataContainer.getPersons().add(person);
        dataLoaderService.saveData();  // Sauvegarder les données après ajout
    }

    // Mettre à jour une personne
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
            person.setCity(updatedPerson.getCity());
            person.setZip(updatedPerson.getZip());
            person.setPhone(updatedPerson.getPhone());
            person.setEmail(updatedPerson.getEmail());
            dataLoaderService.saveData();  // Sauvegarder les données après mise à jour
            return Optional.of(person);
        }

        return Optional.empty();
    }

    // Supprimer une personne
    public boolean deletePerson(String firstName, String lastName) {
        List<Person> persons = dataContainer.getPersons();
        boolean removed = persons.removeIf(p -> {
            boolean match = p.getFirstName().equals(firstName) && p.getLastName().equals(lastName);
            if (match) {
                System.out.println("Suppression de: " + p.getFirstName() + " " + p.getLastName());
            }
            return match;
        });

        if (removed) {
            dataLoaderService.saveData();  // Sauvegarder les données après suppression
        }
        return removed;
    }

    public List<String> getPhoneNumbersByAddresses(List<String> addresses) {
        return dataContainer.getPersons().stream()
                .filter(person -> addresses.contains(person.getAddress()))
                .map(Person::getPhone)
                .distinct() // Élimine les doublons
                .collect(Collectors.toList());
    }

}
