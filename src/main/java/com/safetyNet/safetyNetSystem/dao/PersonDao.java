package com.safetyNet.safetyNetSystem.dao;

import com.safetyNet.safetyNetSystem.model.Person;
import java.util.List;
import java.util.Optional;

public interface PersonDao {
    List<Person> findAll();
    void addPerson(Person person);
    Optional<Person> updatePerson(String firstName, String lastName, Person updatedPerson);
    boolean deletePerson(String firstName, String lastName);

    // Signature de méthode sans implémentation
    List<Person> findByAddress(String address);
}
