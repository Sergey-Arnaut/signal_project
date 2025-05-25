package com.alerts;

import com.alerts.Alert;
import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;

/**
 * The {@code AlertGenerator}  class monitors patient data and generates alerts
 * when predifeind conditiona are met
 *
 * <p>It relies on a {@link DataStorage} instance to access and evaluate data.
 *
 * the problem wsa that line comments for java doc were  big and we wrapped them
 */
public class AlertGenerator {
    private DataStorage dataStorage;

    /**
     * Constructs an {@code AlertGenerator} with a specified {@code DataStorage}.
     * The {@code DataStorage} is used to retrieve patient data that this class
     * will monitor and evaluate.
     *
     * @param dataStorage the storage system that provides access to patient data
     *
     *  we changed the line structure so the line is not seaprated
     *
     */
    public AlertGenerator(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    /**
     * If a condition is met, calls {@link #triggerAlert(Alert)}.
     *
     * we simplified all text so it is short annd clear now
     *
     * @param patient the patient data to evaluate for alert conditions
     */
    public void evaluateData(Patient patient) {
        // Example 1: Critical blood pressure thresholds
        for (PatientRecord rec : patient.getAllRecords()) {
            String type = rec.getRecordType();
            double value = rec.getMeasurementValue();
            long ts = rec.getTimestamp();

            if ("SystolicPressure".equals(type) && (value > 180 || value < 90)) {
                triggerAlert(new Alert(
                        String.valueOf(patient.getPatientId()),             // convert int → String
                        "Critical systolic pressure: " + value,
                        ts));
            }

            // Example 2: Low oxygen saturation
            if ("Saturation".equals(type) && value < 92.0) {
                triggerAlert(new Alert(
                        String.valueOf(patient.getPatientId()),             // convert int → String
                        "Low blood oxygen saturation: " + value + "%",
                        ts));
            }

            // Example 3: Hypotensive hypoxemia (combined condition)
            if ("SystolicPressure".equals(type) && value < 90) {
                patient.getRecords(ts - 60_000, ts).stream()
                        .filter(r -> "Saturation".equals(r.getRecordType()) && r.getMeasurementValue() < 92.0)
                        .findFirst()
                        .ifPresent(r2 -> triggerAlert(new Alert(
                                String.valueOf(patient.getPatientId()),         // convert int → String
                                "Hypotensive hypoxemia (BP=" + value + ", Sat=" + r2.getMeasurementValue() + "%)",
                                ts)));
            }
        }
    }

    /**
     * Triggers an alert for the monitoring system. This method can be extended to
     * notify medical staff, log the alert, or perform other actions. The method
     * currently assumes that the alert information is fully formed when passed as
     * an argument.
     *
     * @param alert the alert object containing details about the alert condition
     */
    private void triggerAlert(Alert alert) {
        // Implementation might involve logging the alert or notifying staff
        System.out.println("ALERT: " + alert);
    }
}
