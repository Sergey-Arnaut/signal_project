package com.data_management;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a single patient and holds all of their recorded vitals.
 */
public class Patient {
    private final int patientId;
    private final List<PatientRecord> records = new ArrayList<>();

    /**
     * Constructs a new Patient with the given ID.
     *
     * @param patientId the unique identifier of this patient
     */
    public Patient(int patientId) {
        this.patientId = patientId;
    }

    /**
     * Adds a new measurement record for this patient.
     *
     * @param measurementValue the numeric value of the measurement
     * @param recordType       the type or label of the measurement
     * @param timestamp        the time at which the measurement was taken (ms since epoch)
     */
    public void addRecord(double measurementValue, String recordType, long timestamp) {
        // PatientRecord’s constructor is (patientId, measurementValue, recordType, timestamp)
        records.add(new PatientRecord(patientId, measurementValue, recordType, timestamp));
    }

    /**
     * Returns an unmodifiable view of all records for this patient.
     *
     * @return all measurement records in insertion order
     */
    public List<PatientRecord> getAllRecords() {
        return Collections.unmodifiableList(records);
    }

    /**
     * Returns all records for this patient whose timestamps are
     * in the inclusive range [startTime, endTime].
     *
     * @param startTime inclusive lower bound of timestamp
     * @param endTime   inclusive upper bound of timestamp
     * @return list of PatientRecord objects in the given time window, in insertion order
     */
    public List<PatientRecord> getRecords(long startTime, long endTime) {
        return records.stream()
                .filter(r -> r.getTimestamp() >= startTime && r.getTimestamp() <= endTime)
                .collect(Collectors.toList());
    }

    /**
     * @return the unique patient ID
     */
    public int getPatientId() {
        return patientId;
    }

}
