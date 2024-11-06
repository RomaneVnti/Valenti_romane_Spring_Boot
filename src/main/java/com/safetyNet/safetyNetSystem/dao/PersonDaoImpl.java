package com.safetyNet.safetyNetSystem.dao;

import com.safetyNet.safetyNetSystem.dao.PersonDao;
import com.safetyNet.safetyNetSystem.model.Person;
import com.safetyNet.safetyNetSystem.util.DataLoaderUtil;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PersonDaoImpl implements PersonDao {

    private final List<Person> persons;

    // Inject DataLoaderUtil pour charger les données au démarrage
    public PersonDaoImpl(DataLoaderUtil dataLoaderUtil) {
        this.persons = dataLoaderUtil.loadData().getPersons();  // Charge les données du JSON
    }

    @Override
    public List<Person> findAll() {
        return persons;
    }

    @Override
    public void addPerson(Person person) {
        persons.add(person);
    }

    @Override
    public Optional<Person> updatePerson(String firstName, String lastName, Person updatedPerson) {
        return persons.stream()
                .filter(p -> p.getFirstName().equals(firstName) && p.getLastName().equals(lastName))
                .findFirst()
                .map(existingPerson -> {
                    existingPerson.setFirstName(updatedPerson.getFirstName());
                    existingPerson.setLastName(updatedPerson.getLastName());
                    existingPerson.setAddress(updatedPerson.getAddress());
                    existingPerson.setCity(updatedPerson.getCity());
                    existingPerson.setZip(updatedPerson.getZip());
                    existingPerson.setPhone(updatedPerson.getPhone());
                    existingPerson.setEmail(updatedPerson.getEmail());
                    return existingPerson;
                });
    }

    @Override
    public boolean deletePerson(String firstName, String lastName) {
        return persons.removeIf(p -> p.getFirstName().equals(firstName) && p.getLastName().equals(lastName));
    }

    @Override
    public List<Person> findByAddress(String address) {
        return persons.stream()
                .filter(person -> person.getAddress().equals(address))
                .collect(Collectors.toList());
    }
}
