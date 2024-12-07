package com.safetyNet.safetyNetSystem.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetyNet.safetyNetSystem.model.DataContainer;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * Utilitaire pour charger et sauvegarder les données depuis et vers un fichier JSON.
 */
@Component
public class DataLoaderUtil {

    private static final Logger logger = LoggerFactory.getLogger(DataLoaderUtil.class);
    private final String dataFilePath = "src/main/resources/data.json";

    /**
     * Charge les données depuis le fichier JSON et les retourne sous forme de DataContainer.
     *
     * @return un DataContainer contenant les données chargées depuis le fichier
     */
    public DataContainer loadData() {
        ObjectMapper objectMapper = new ObjectMapper();
        DataContainer dataContainer = null;

        try {
            File jsonFile = new File(dataFilePath);
            dataContainer = objectMapper.readValue(jsonFile, DataContainer.class);
            logger.info("Données chargées avec succès depuis le fichier.");
        } catch (IOException e) {
            logger.error("Erreur lors du chargement des données depuis le fichier : {}", e.getMessage());
        }

        return dataContainer;
    }

    /**
     * Sauvegarde les données dans le fichier JSON en mettant à jour uniquement les données modifiées.
     *
     * @param dataContainer les données à sauvegarder
     */
    public void saveData(DataContainer dataContainer) {
        ObjectMapper objectMapper = new ObjectMapper();

        // Charger les données existantes depuis le fichier
        DataContainer existingDataContainer = loadDataFromFile();

        // Mettre à jour uniquement les données modifiées
        existingDataContainer.setPersons(dataContainer.getPersons());
        existingDataContainer.setFirestations(dataContainer.getFirestations());
        existingDataContainer.setMedicalrecords(dataContainer.getMedicalrecords());

        try {
            objectMapper.writeValue(new File(dataFilePath), existingDataContainer);
            logger.info("Données sauvegardées avec succès dans le fichier.");
        } catch (IOException e) {
            logger.error("Erreur lors de la sauvegarde des données dans le fichier : {}", e.getMessage());
        }
    }

    /**
     * Charge les données depuis le fichier JSON. Si le fichier est vide ou inexistant, retourne un DataContainer vide.
     *
     * @return un DataContainer contenant les données chargées ou un DataContainer vide si aucune donnée n'est présente
     */
    private DataContainer loadDataFromFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(new File(dataFilePath), DataContainer.class);
        } catch (IOException e) {
            logger.warn("Le fichier de données est vide ou inexistant, retour d'un DataContainer vide.");
            return new DataContainer();
        }
    }
}
