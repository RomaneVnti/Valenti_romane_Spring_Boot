package com.safetyNet.safetyNetSystem.service;

import com.safetyNet.safetyNetSystem.model.DataContainer;
import com.safetyNet.safetyNetSystem.util.DataLoaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service permettant de charger et de sauvegarder les données dans un conteneur de données.
 * Il utilise un utilitaire de chargement et de sauvegarde des données pour gérer l'accès à celles-ci.
 */
@Service
public class DataLoaderService {

    private final DataLoaderUtil dataLoaderUtil;  // Utilitaire pour charger et sauvegarder les données
    private DataContainer dataContainer;  // Conteneur de données

    // Création d'un logger pour la classe
    private static final Logger logger = LoggerFactory.getLogger(DataLoaderService.class);

    /**
     * Constructeur de DataLoaderService qui initialise le service avec l'utilitaire de chargement des données.
     * Il charge les données une seule fois à l'initialisation.
     *
     * @param dataLoaderUtil L'utilitaire utilisé pour charger et sauvegarder les données.
     */
    public DataLoaderService(DataLoaderUtil dataLoaderUtil) {
        this.dataLoaderUtil = dataLoaderUtil;
        logger.info("Initialisation de DataLoaderService...");

        // Charger les données au démarrage
        this.dataContainer = dataLoaderUtil.loadData();

        // Si les données sont nulles, un DataContainer vide est créé
        if (dataContainer == null) {
            dataContainer = new DataContainer();  // Créer un DataContainer vide si les données sont vides
            logger.warn("Les données sont vides, un DataContainer vide a été créé.");
        } else {
            logger.info("Les données ont été chargées avec succès.");
        }
    }

    /**
     * Récupère le DataContainer contenant les données chargées.
     *
     * @return L'instance de DataContainer avec les données chargées.
     */
    public DataContainer getDataContainer() {
        logger.debug("Récupération du DataContainer.");
        return dataContainer;  // Renvoie l'instance partagée de DataContainer
    }

    /**
     * Sauvegarde les données dans le DataContainer.
     * Utilise l'utilitaire de chargement des données pour effectuer la sauvegarde.
     * En cas d'erreur, un message d'erreur est enregistré.
     */
    public void saveData() {
        try {
            dataLoaderUtil.saveData(dataContainer);  // Sauvegarde des données après modifications
            logger.info("Les données ont été sauvegardées avec succès.");
        } catch (Exception e) {
            // En cas d'erreur, le message d'erreur est loggé
            logger.error("Erreur lors de la sauvegarde des données : {}", e.getMessage());
        }
    }
}
