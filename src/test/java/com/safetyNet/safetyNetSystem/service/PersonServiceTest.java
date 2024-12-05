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

    @Test
    void testGetPersonInfoByLastName() {
        // Arrange
        Person person1 = new Person("John", "Doe", "123 Main St", "City", "12345", "123-456-7890", "john.doe@example.com");
        Person person2 = new Person("Jane", "Doe", "456 Elm St", "City", "67890", "987-654-3210", "jane.doe@example.com");

        MedicalRecord medicalRecord1 = new MedicalRecord("John", "Doe", "1980-03-15", List.of("med1"), List.of("allergy1"));
        MedicalRecord medicalRecord2 = new MedicalRecord("Jane", "Doe", "1990-05-20", List.of("med2"), List.of("allergy2"));

        when(personDAO.getAllPersons()).thenReturn(List.of(person1, person2));
        when(medicalRecordService.getMedicalRecordByPerson(person1)).thenReturn(Optional.of(medicalRecord1));
        when(medicalRecordService.getMedicalRecordByPerson(person2)).thenReturn(Optional.of(medicalRecord2));

        // Act
        List<PersonInfo> result = personService.getPersonInfoByLastName("Doe");

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getFirstName()).isEqualTo("John");
        assertThat(result.get(1).getFirstName()).isEqualTo("Jane");
        assertThat(result.get(0).getMedicalInfo().getMedications()).contains("med1");
        assertThat(result.get(1).getMedicalInfo().getMedications()).contains("med2");
        verify(personDAO, times(1)).getAllPersons();
        verify(medicalRecordService, times(2)).getMedicalRecordByPerson(any(Person.class));
    }

    @Test
    void testGetEmailsByCity() {
        // Arrange
        Person person1 = new Person("John", "Doe", "123 Main St", "City", "12345", "123-456-7890", "john.doe@example.com");
        Person person2 = new Person("Jane", "Doe", "456 Elm St", "City", "67890", "987-654-3210", "jane.doe@example.com");
        Person person3 = new Person("Alice", "Smith", "789 Oak St", "OtherCity", "11223", "555-555-5555", "alice.smith@example.com");

        when(personDAO.getAllPersons()).thenReturn(List.of(person1, person2, person3));

        // Act
        List<String> result = personService.getEmailsByCity("City");

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result).contains("john.doe@example.com", "jane.doe@example.com");
        verify(personDAO, times(1)).getAllPersons();
    }

    @Test
    void testGetChildAlertByAddress() {
        // Arrange
        Person person1 = new Person("John", "Doe", "123 Main St", "City", "12345", "123-456-7890", "john.doe@example.com");
        Person person2 = new Person("Jane", "Doe", "123 Main St", "City", "67890", "987-654-3210", "john.doe@example.com");
        Person person3 = new Person("Jimmy", "Doe", "456 Elm St", "City", "11223", "555-555-5555", "jimmy.doe@example.com");
        when(personDAO.getAllPersons()).thenReturn(List.of(person1, person2, person3));
        when(medicalRecordService.getMedicalRecordByPerson(person1)).thenReturn(Optional.of(new MedicalRecord("John", "Doe", "2010-03-15", List.of(), List.of())));
        when(medicalRecordService.getMedicalRecordByPerson(person2)).thenReturn(Optional.of(new MedicalRecord("Jane", "Doe", "2015-06-15", List.of(), List.of())));

        // Act
        ChildrenAlertResponse result = personService.getChildAlertByAddress("123 Main St");

        // Assert
        assertThat(result.getChildren()).hasSize(2); // Assume both John and Jane are children
        assertThat(result.getAdults()).isEmpty();
        verify(personDAO, times(1)).getAllPersons();
    }



}
