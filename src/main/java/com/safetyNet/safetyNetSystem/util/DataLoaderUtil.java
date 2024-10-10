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
        try {
            objectMapper.writeValue(new File(dataFilePath), dataContainer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
