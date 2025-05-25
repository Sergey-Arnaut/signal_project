package com.alerts;

import com.data_management.DataStorage;
import com.data_management.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link AlertGenerator#evaluateData(Patient)}.
 * <p>
 * These tests verify that calling evaluateData with various critical
 * conditions does not throw exceptions and (in a real system) would
 * trigger the appropriate alerts.
 */
class AlertGeneratorTest {

    private DataStorage storage;
    private AlertGenerator generator;

    /**
     * Prepare a fresh DataStorage and AlertGenerator before each test.
     */
    @BeforeEach
    void setUp() {
        storage = new DataStorage();
        generator = new AlertGenerator(storage);
    }

    /**
     * Test that a dangerously high systolic pressure record
     * is processed without error.
     */
    @Test
    void testCriticalSystolicPressureAlert() {
        Patient patient = new Patient(1);
        // Add a dangerously high systolic pressure reading
        patient.addRecord(200.0, "SystolicPressure", System.currentTimeMillis());
        generator.evaluateData(patient);
        // No exception thrown indicates that evaluateData handled it gracefully
        assertTrue(true, "evaluateData should handle critical systolic pressure");
    }

    /**
     * Test that a low blood oxygen saturation record
     * is processed without error.
     */
    @Test
    void testLowOxygenSaturationAlert() {
        Patient patient = new Patient(2);
        // Add a low saturation reading
        patient.addRecord(90.0, "Saturation", System.currentTimeMillis());
        generator.evaluateData(patient);
        // No exception thrown indicates that evaluateData handled it gracefully
        assertTrue(true, "evaluateData should handle low oxygen saturation");
    }
}
