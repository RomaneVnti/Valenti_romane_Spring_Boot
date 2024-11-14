package com.safetyNet.safetyNetSystem.service;

import com.safetyNet.safetyNetSystem.model.DataContainer;
import com.safetyNet.safetyNetSystem.util.DataLoaderUtil;
import org.springframework.stereotype.Service;

@Service
public class DataLoaderService {
    private final DataLoaderUtil dataLoaderUtil;
    private DataContainer dataContainer;

    public DataLoaderService() {
        this.dataLoaderUtil = new DataLoaderUtil();
        this.dataContainer = dataLoaderUtil.loadData(); // Charger les données une seule fois lors de l'initialisation

        // Si les données sont vides, initialiser un DataContainer vide
        if (dataContainer == null) {
            dataContainer = new DataContainer();
        }
    }

    public DataContainer getDataContainer() {
        return dataContainer;
    }

    public void saveData() {
        dataLoaderUtil.saveData(dataContainer); // Sauvegarder les données après modifications
    }
}
