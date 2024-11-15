package com.safetyNet.safetyNetSystem.service;

import com.safetyNet.safetyNetSystem.model.DataContainer;
import com.safetyNet.safetyNetSystem.util.DataLoaderUtil;
import org.springframework.stereotype.Service;

@Service
public class DataLoaderService {
    private final DataLoaderUtil dataLoaderUtil;
    private DataContainer dataContainer;

    public DataLoaderService(DataLoaderUtil dataLoaderUtil) {
        this.dataLoaderUtil = dataLoaderUtil;
        this.dataContainer = dataLoaderUtil.loadData();  // Charger les données une seule fois

        if (dataContainer == null) {
            dataContainer = new DataContainer();  // Créer un DataContainer vide si les données sont vides
        }
    }

    public DataContainer getDataContainer() {
        return dataContainer;  // Renvoie l'instance partagée de DataContainer
    }

    public void saveData() {
        dataLoaderUtil.saveData(dataContainer);  // Sauvegarde des données après modifications
    }
}
