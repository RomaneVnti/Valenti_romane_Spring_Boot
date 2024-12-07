package com.safetyNet.safetyNetSystem.service;

import com.safetyNet.safetyNetSystem.model.DataContainer;
import com.safetyNet.safetyNetSystem.util.DataLoaderUtil;
import com.safetyNet.safetyNetSystem.service.DataLoaderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test unitaires pour la classe {@link DataLoaderService}.
 * Ces tests vérifient le bon fonctionnement des méthodes du service {@link DataLoaderService}.
 */
class DataLoaderServiceTest {

    private DataLoaderUtil dataLoaderUtil;
    private DataLoaderService dataLoaderService;
    private DataContainer mockDataContainer;

    /**
     * Initialisation des objets avant chaque test.
     * Création des mocks et configuration du comportement des méthodes.
     */
    @BeforeEach
    void setUp() {
        // Création du mock pour DataLoaderUtil
        dataLoaderUtil = mock(DataLoaderUtil.class);
        mockDataContainer = mock(DataContainer.class);

        // Simulation de la méthode loadData() pour renvoyer un DataContainer simulé
        when(dataLoaderUtil.loadData()).thenReturn(mockDataContainer);

        // Instanciation de DataLoaderService avec le mock
        dataLoaderService = new DataLoaderService(dataLoaderUtil);
    }

    /**
     * Test de la méthode {@link DataLoaderService#getDataContainer()} lorsqu'un DataContainer valide est chargé.
     * Vérifie que la méthode retourne bien le DataContainer chargé.
     */
    @Test
    void testGetDataContainer_whenDataIsLoaded() {
        // Act: Appeler la méthode pour obtenir le DataContainer
        DataContainer result = dataLoaderService.getDataContainer();

        // Assert: Vérifier que le DataContainer retourné est celui chargé
        assertNotNull(result, "Le DataContainer ne doit pas être nul");
        assertSame(mockDataContainer, result, "Le DataContainer retourné doit être le même que celui simulé");
    }

    /**
     * Test de la méthode {@link DataLoaderService#getDataContainer()} lorsque les données chargées sont nulles.
     * Vérifie que la méthode crée un DataContainer même si les données chargées sont nulles.
     */
    @Test
    void testGetDataContainer_whenDataIsNull() {
        // Arrange: Simuler que les données chargées sont nulles
        when(dataLoaderUtil.loadData()).thenReturn(null);

        // Act: Appeler la méthode pour obtenir le DataContainer
        dataLoaderService = new DataLoaderService(dataLoaderUtil);  // Re-créer le service avec les nouvelles données
        DataContainer result = dataLoaderService.getDataContainer();

        // Assert: Vérifier que le DataContainer est bien créé même si les données sont nulles
        assertNotNull(result, "Le DataContainer ne doit pas être nul, même si les données sont nulles");
    }

    /**
     * Test de la méthode {@link DataLoaderService#saveData()}.
     * Vérifie que la méthode appelle bien la méthode {@link DataLoaderUtil#saveData(DataContainer)}.
     */
    @Test
    void testSaveData() {
        // Act: Appeler la méthode pour sauvegarder les données
        dataLoaderService.saveData();

        // Assert: Vérifier que la méthode saveData de DataLoaderUtil a été appelée
        verify(dataLoaderUtil, times(1)).saveData(mockDataContainer);
    }
}
