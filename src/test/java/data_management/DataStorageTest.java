package com.data_management;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * Verifies DataStorage.addPatientData(...) and getRecords(...)
 */
class DataStorageTest {

    @Test
    void testAddAndGetRecords() {
        DataStorage storage = new DataStorage();

        // insert two records for patient #1
        storage.addPatientData(1, 100.0, "WBC", 1000L);
        storage.addPatientData(1, 200.0, "WBC", 1001L);

        // fetch them back
        List<PatientRecord> recs = storage.getRecords(1, 1000L, 1001L);
        assertEquals(2, recs.size(), "Should retrieve two records");
        assertEquals(100.0, recs.get(0).getMeasurementValue(), "First record value");
        assertEquals(200.0, recs.get(1).getMeasurementValue(), "Second record value");
    }
}
