// src/test/java/com/data_management/PatientTest.java
package com.data_management;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * Unit tests for Patient.getRecords(...) behavior.
 */
class PatientTest {

    private static final double EPS = 1e-6;

    @Test
    void testGetRecordsFiltering() {
        Patient p = new Patient(42);

        // Add three records at timestamps 100, 200, 300
        p.addRecord(1.0, "Foo", 100L);
        p.addRecord(2.0, "Foo", 200L);
        p.addRecord(3.0, "Foo", 300L);

        // Query [100, 200] => should get the first two
        List<PatientRecord> subset = p.getRecords(100L, 200L);
        assertEquals(2, subset.size(), "Should retrieve two records in [100,200]");
        assertEquals(1.0, subset.get(0).getMeasurementValue(), EPS,
                "First record's measurementValue should be 1.0");
        assertEquals(2.0, subset.get(1).getMeasurementValue(), EPS,
                "Second record's measurementValue should be 2.0");

        // Query [150, 250] => should get exactly the middle one
        List<PatientRecord> mid = p.getRecords(150L, 250L);
        assertEquals(1, mid.size(), "Should retrieve one record in [150,250]");
        assertEquals(2.0, mid.get(0).getMeasurementValue(), EPS,
                "That record's measurementValue should be 2.0");

        // Query a range that has none
        List<PatientRecord> none = p.getRecords(400L, 500L);
        assertEquals(0, none.size(), "Should retrieve no records in [400,500]");
    }
}
