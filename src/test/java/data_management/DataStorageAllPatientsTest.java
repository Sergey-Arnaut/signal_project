package com.data_management;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * Unit tests for DataStorage.getAllPatients() behavior.
 */
class DataStorageAllPatientsTest {

    @Test
    void testGetAllPatients() {
        DataStorage storage = new DataStorage();
        // Add data for patient 1 and 2
        storage.addPatientData(1, 10.0, "HeartRate", 1000L);
        storage.addPatientData(2, 20.0, "HeartRate", 2000L);

        List<Patient> all = storage.getAllPatients();
        // Should return exactly two Patient objects, IDs 1 and 2
        assertEquals(2, all.size(), "Should have two patients stored");

        // Verify their IDs
        assertEquals(1, all.get(0).getPatientId(), "First patient ID should be 1");
        assertEquals(2, all.get(1).getPatientId(), "Second patient ID should be 2");
    }
}
