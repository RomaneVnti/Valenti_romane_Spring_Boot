package com.safetyNet.safetyNetSystem.dao;

import com.safetyNet.safetyNetSystem.model.Person;
import com.safetyNet.safetyNetSystem.util.DataLoaderUtil;
import org.springframework.stereotype.Repository;
import com.safetyNet.safetyNetSystem.model.DataContainer;
import java.util.List;
import java.util.Optional;

@Repository
public class PersonDAO {

    private final DataLoaderUtil dataLoaderUtil;
    private final DataContainer dataContainer;

    public PersonDAO(DataLoaderUtil dataLoaderUtil) {
        this.dataLoaderUtil = dataLoaderUtil;
        this.dataContainer = dataLoaderUtil.loadData();  // Assurez-vous que la méthode loadData charge bien le DataContainer.
    }

    // Récupérer toutes les personnes
    public List<Person> getAllPersons() {
        return dataContainer.getPersons();
    }

    // Ajouter une personne
    public void addPerson(Person person) {
        dataContainer.getPersons().add(person);
        dataLoaderUtil.saveData(dataContainer);
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
            dataLoaderUtil.saveData(dataContainer);
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
        }); // Fermeture de la méthode removeIf ici

        if (removed) {
            dataLoaderUtil.saveData(dataContainer);
        }
        return removed;
    }
}
