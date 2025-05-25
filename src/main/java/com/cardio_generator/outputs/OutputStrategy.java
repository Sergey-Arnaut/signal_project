package com.cardio_generator.outputs;

/**
 * Strategy interface for handling output of simulated patient data.
 * <p>
 * Implementations define how generated data is delivered, e.g., printed to
 * console, written to files, or streamed over network protocols.
 */
public interface OutputStrategy {

    /**
     * Outputs a data record for a specific patient.
     *
     * @param patientId the unique identifier of the patient
     * @param timestamp the measurement timestamp in milliseconds since epoch
     * @param label     a short descriptor of the data type (e.g., "ECG", "Alert")
     * @param data      the measurement value or alert detail as a string
     */
    void output(int patientId, long timestamp, String label, String data);
}
