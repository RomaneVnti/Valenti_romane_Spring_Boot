package com.safetyNet.safetyNetSystem.service;

import com.safetyNet.safetyNetSystem.model.DataContainer;
import com.safetyNet.safetyNetSystem.util.DataLoaderUtil;
import org.springframework.stereotype.Service;

@Service
public class DataLoaderService {
    private final DataLoaderUtil dataLoaderUtil;

    public DataLoaderService() {
        this.dataLoaderUtil = new DataLoaderUtil();
    }

    public DataContainer loadData() {
        return dataLoaderUtil.loadData();
    }
}
