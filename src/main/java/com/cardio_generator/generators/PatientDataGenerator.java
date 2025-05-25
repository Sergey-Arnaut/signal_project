package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * Interface for generating simulated patient data.
 * <p>
 * Implementations produce measurements for a given patient and deliver them
 * via the specified output strategy.
 */
public interface PatientDataGenerator {

    /**
     * Generates and outputs simulated data for the specified patient.
     *
     * @param patientId the unique identifier of the patient
     * @param outputStrategy the strategy used to output generated data
     */
    void generate(int patientId, OutputStrategy outputStrategy);
}
