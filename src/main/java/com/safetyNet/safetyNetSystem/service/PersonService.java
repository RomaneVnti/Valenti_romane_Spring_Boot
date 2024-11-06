package com.safetyNet.safetyNetSystem.service;

import com.safetyNet.safetyNetSystem.dao.PersonDao;
import com.safetyNet.safetyNetSystem.model.Person;
import com.safetyNet.safetyNetSystem.model.DataContainer;
import com.safetyNet.safetyNetSystem.util.DataLoaderUtil;
import com.safetyNet.safetyNetSystem.dto.ChildrenAlertResponse;
import com.safetyNet.safetyNetSystem.dto.PersonInfo;
import com.safetyNet.safetyNetSystem.model.MedicalRecord;
import com.safetyNet.safetyNetSystem.util.DateUtil;
import com.safetyNet.safetyNetSystem.dto.ChildInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

@Service
public class PersonService {

    private final PersonDao personDao;
    private final DataLoaderUtil dataLoaderUtil;
    private final MedicalRecordService medicalRecordService;

    public PersonService(PersonDao personDao, DataLoaderUtil dataLoaderUtil, MedicalRecordService medicalRecordService) {
        this.personDao = personDao;
        this.dataLoaderUtil = dataLoaderUtil;
        this.medicalRecordService = medicalRecordService;
    }

    public List<Person> getAllPersons() {
        return personDao.findAll();
    }

    public void addPerson(Person person) {
        personDao.addPerson(person);
        DataContainer dataContainer = new DataContainer();
        dataContainer.setPersons(personDao.findAll()); // Utilise setPersons pour initialiser les données
        dataLoaderUtil.saveData(dataContainer);
    }

    public Optional<Person> updatePerson(String firstName, String lastName, Person updatedPerson) {
        Optional<Person> existingPerson = personDao.updatePerson(firstName, lastName, updatedPerson);

        if (existingPerson.isPresent()) {
            DataContainer dataContainer = new DataContainer();
            dataContainer.setPersons(personDao.findAll()); // Utilise setPersons pour initialiser les données
            dataLoaderUtil.saveData(dataContainer);
        }

        return existingPerson;
    }

    public boolean deletePerson(String firstName, String lastName) {
        boolean removed = personDao.deletePerson(firstName, lastName);

        if (removed) {
            DataContainer dataContainer = new DataContainer();
            dataContainer.setPersons(personDao.findAll()); // Utilise setPersons pour initialiser les données
            dataLoaderUtil.saveData(dataContainer);
        }

        return removed;
    }

    public ChildrenAlertResponse getChildAlertByAddress(String address) {
        List<Person> personsAtAddress = personDao.findByAddress(address);
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
}
