package com.safetyNet.safetyNetSystem.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetyNet.safetyNetSystem.model.DataContainer;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class DataLoaderUtil {
    private final String dataFilePath = "src/main/resources/data.json";

    public DataContainer loadData() {
        ObjectMapper objectMapper = new ObjectMapper();
        DataContainer dataContainer = null;

        try {
            File jsonFile = new File(dataFilePath);
            dataContainer = objectMapper.readValue(jsonFile, DataContainer.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataContainer;
    }

    public void saveData(DataContainer dataContainer) {
        ObjectMapper objectMapper = new ObjectMapper();

        // Étape 1 : Charger les données existantes depuis le fichier
        DataContainer existingDataContainer = loadDataFromFile();

        // Étape 2 : Fusionner les nouvelles données avec celles existantes
        existingDataContainer.getPersons().addAll(dataContainer.getPersons()); // Ajouter les personnes
        existingDataContainer.getFirestations().addAll(dataContainer.getFirestations()); // Ajouter les casernes

        // Étape 3 : Réécrire le fichier avec les nouvelles données
        try {
            objectMapper.writeValue(new File(dataFilePath), existingDataContainer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private DataContainer loadDataFromFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Si le fichier existe et contient des données, charge-les
            return objectMapper.readValue(new File(dataFilePath), DataContainer.class);
        } catch (IOException e) {
            // Si le fichier n'existe pas ou est vide, retourne un DataContainer vide
            return new DataContainer();
        }
    }

}
