package com.safetyNet.safetyNetSystem.service;

import com.safetyNet.safetyNetSystem.dao.PersonDAO;
import com.safetyNet.safetyNetSystem.dto.ChildrenAlertResponse;
import com.safetyNet.safetyNetSystem.dto.MedicalInfo;
import com.safetyNet.safetyNetSystem.dto.PersonInfo;
import com.safetyNet.safetyNetSystem.model.MedicalRecord;
import com.safetyNet.safetyNetSystem.model.Person;
import com.safetyNet.safetyNetSystem.util.DateUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class PersonServiceTest {

    @Mock
    private PersonDAO personDAO;

    @Mock
    private MedicalRecordService medicalRecordService;

    @InjectMocks
    private PersonService personService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllPersons() {
        // Arrange
        List<Person> mockPersons = List.of(
                new Person("John", "Doe", "123 Main St", "City", "12345", "123-456-7890", "john.doe@example.com"),
                new Person("Jane", "Doe", "456 Elm St", "City", "67890", "987-654-3210", "john.doe@example.com")
        );
        when(personDAO.getAllPersons()).thenReturn(mockPersons);

        // Act
        List<Person> result = personService.getAllPersons();

        // Assert
        assertThat(result).hasSize(2);
        verify(personDAO, times(1)).getAllPersons();
    }

    @Test
    void testAddPerson() {
        // Arrange
        Person newPerson = new Person("John", "Smith", "123 Main St", "City", "12345", "123-456-7890", "john.doe@example.com");

        // Act
        personService.addPerson(newPerson);

        // Assert
        verify(personDAO, times(1)).addPerson(newPerson);
    }

    @Test
    void testUpdatePerson() {
        // Arrange
        Person updatedPerson = new Person("John", "Doe", "789 Oak St", "City", "12345", "123-456-7890", "john.doe@example.com");
        when(personDAO.updatePerson("John", "Doe", updatedPerson)).thenReturn(Optional.of(updatedPerson));

        // Act
        Optional<Person> result = personService.updatePerson("John", "Doe", updatedPerson);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getAddress()).isEqualTo("789 Oak St");
        verify(personDAO, times(1)).updatePerson("John", "Doe", updatedPerson);
    }

    @Test
    void testDeletePerson() {
        // Arrange
        when(personDAO.deletePerson("John", "Doe")).thenReturn(true);

        // Act
        boolean result = personService.deletePerson("John", "Doe");

        // Assert
        assertThat(result).isTrue();
        verify(personDAO, times(1)).deletePerson("John", "Doe");
    }

    @Test
    void testGetPersonByFirstNameAndLastName() {
        // Arrange
        Person person = new Person("John", "Doe", "123 Main St", "City", "12345", "123-456-7890", "john.doe@example.com");
        when(personDAO.getAllPersons()).thenReturn(List.of(person));

        // Act
        Optional<Person> result = personService.getPersonByFirstNameAndLastName("John", "Doe");

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getFirstName()).isEqualTo("John");
        verify(personDAO, times(1)).getAllPersons();
    }

    @Test
    void testGetPersonsWithMedicalInfoByAddress() {
        // Arrange
        Person person = new Person("John", "Doe", "123 Main St", "City", "12345", "123-456-7890", "john.doe@example.com");
        MedicalRecord medicalRecord = new MedicalRecord("John", "Doe", "1980-03-15", List.of("med1"), List.of("allergy1"));
        when(personDAO.getAllPersons()).thenReturn(List.of(person));
        when(medicalRecordService.getMedicalRecordByPerson(person)).thenReturn(Optional.of(medicalRecord));

        // Act
        List<PersonInfo> result = personService.getPersonsWithMedicalInfoByAddress("123 Main St", true);

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getMedicalInfo().getMedications()).contains("med1");
        verify(personDAO, times(1)).getAllPersons();
        verify(medicalRecordService, times(1)).getMedicalRecordByPerson(person);
    }
}
