package com.safetyNet.safetyNetSystem.service;

import com.safetyNet.safetyNetSystem.model.DataContainer;
import com.safetyNet.safetyNetSystem.util.DataLoaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DataLoaderService {
    private final DataLoaderUtil dataLoaderUtil;
    private DataContainer dataContainer;

    // Création d'un logger pour la classe
    private static final Logger logger = LoggerFactory.getLogger(DataLoaderService.class);

    public DataLoaderService(DataLoaderUtil dataLoaderUtil) {
        this.dataLoaderUtil = dataLoaderUtil;
        logger.info("Initialisation de DataLoaderService...");

        this.dataContainer = dataLoaderUtil.loadData();  // Charger les données une seule fois

        if (dataContainer == null) {
            dataContainer = new DataContainer();  // Créer un DataContainer vide si les données sont vides
            logger.warn("Les données sont vides, un DataContainer vide a été créé.");
        } else {
            logger.info("Les données ont été chargées avec succès.");
        }
    }

    public DataContainer getDataContainer() {
        logger.debug("Récupération du DataContainer.");
        return dataContainer;  // Renvoie l'instance partagée de DataContainer
    }

    public void saveData() {
        try {
            dataLoaderUtil.saveData(dataContainer);  // Sauvegarde des données après modifications
            logger.info("Les données ont été sauvegardées avec succès.");
        } catch (Exception e) {
            logger.error("Erreur lors de la sauvegarde des données : {}", e.getMessage());
        }
    }
}
