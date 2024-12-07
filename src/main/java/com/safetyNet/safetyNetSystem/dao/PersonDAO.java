package com.safetyNet.safetyNetSystem.dao;

import com.safetyNet.safetyNetSystem.model.Person;
import com.safetyNet.safetyNetSystem.service.DataLoaderService;
import org.springframework.stereotype.Repository;
import com.safetyNet.safetyNetSystem.model.DataContainer;
import java.util.List;
import java.util.Optional;

/**
 * DAO (Data Access Object) pour gérer les personnes.
 * Ce DAO permet de récupérer, ajouter, mettre à jour et supprimer des personnes dans la base de données.
 * Utilise le DataLoaderService pour charger et sauvegarder les données.
 */
@Repository
public class PersonDAO {

    private final DataLoaderService dataLoaderService;
    private final DataContainer dataContainer;

    /**
     * Constructeur pour initialiser DataLoaderUtil et DataLoaderService.
     * Le DataContainer est utilisé pour accéder à la liste des personnes.
     *
     * @param dataLoaderService Service de gestion du chargement et de la sauvegarde des données.
     */
    public PersonDAO(DataLoaderService dataLoaderService) {
        this.dataLoaderService = dataLoaderService;
        // Charger les données via DataLoaderService
        this.dataContainer = dataLoaderService.getDataContainer();  // Utilisation de DataLoaderService pour obtenir le DataContainer
    }

    /**
     * Récupère toutes les personnes.
     *
     * @return Une liste de toutes les personnes.
     */
    public List<Person> getAllPersons() {
        return dataContainer.getPersons();
    }

    /**
     * Ajoute une nouvelle personne.
     *
     * @param person L'objet Person à ajouter.
     */
    public void addPerson(Person person) {
        dataContainer.getPersons().add(person);
        dataLoaderService.saveData();  // Sauvegarder les données après ajout
    }

    /**
     * Met à jour une personne existante en fonction du prénom et du nom.
     * Si la personne est trouvée, ses informations sont mises à jour.
     *
     * @param firstName Le prénom de la personne à mettre à jour.
     * @param lastName Le nom de famille de la personne à mettre à jour.
     * @param updatedPerson L'objet Person contenant les nouvelles informations.
     * @return Un objet Optional contenant la personne mise à jour si trouvée, sinon un Optional vide.
     */
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

    /**
     * Supprime une personne par son prénom et son nom.
     * Si la personne est trouvée et supprimée, les données sont sauvegardées.
     *
     * @param firstName Le prénom de la personne à supprimer.
     * @param lastName Le nom de famille de la personne à supprimer.
     * @return true si la personne a été supprimée, false sinon.
     */
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

}
