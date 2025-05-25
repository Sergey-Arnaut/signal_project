package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Strategy for writing simulated patient data to files.
 * <p>
 * Each distinct data label is written to its own text file within
 * a specified base directory. Records are appended in a human-readable format.
 */
public class FileOutputStrategy implements OutputStrategy {

    /** Base directory where output files are stored. */
    private String baseDirectory; // lower camelCase from BaseDirectory to basediretory

    /** Mapping from data label to its corresponding file path. */
    public final ConcurrentHashMap<String, String> file_map = new ConcurrentHashMap<>();

    /**
     * Creates a new FileOutputStrategy for the given directory.
     *
     * @param baseDirectory the root directory for output files
     */
    public FileOutputStrategy(String baseDirectory) {
        this.baseDirectory = baseDirectory; // as written before to lower camelCase
    }

    /**
     * Outputs a data record for a specific patient to a file. Creates
     * the base directory if it does not exist, and appends the record
     * to a file named &lt;label&gt;.txt within it.
     *
     * @param patientId the unique identifier of the patient
     * @param timestamp the time of the measurement (milliseconds since epoch)
     * @param label     the label used to group records into files
     * @param data      the measurement value or detail to record
     */
    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        try {
            // Create the directory
            Files.createDirectories(Paths.get(baseDirectory)); // baseDirectory
        } catch (IOException e) {
            System.err.println("Error creating base directory: " + e.getMessage());
            return;
        }
        // Set the FilePath variable and cahnged it to filePath because it is variable
        String filePath = file_map.computeIfAbsent(label, k ->
                Paths.get(baseDirectory, label + ".txt").toString()
        );

        // Write the data to the file
        try (PrintWriter out = new PrintWriter(
                Files.newBufferedWriter(
                        Paths.get(filePath),
                        StandardOpenOption.CREATE,
                        StandardOpenOption.APPEND))) {
            out.printf("Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n",
                    patientId, timestamp, label, data);
        } catch (Exception e) {
            System.err.println("Error writing to file " + filePath + ": " + e.getMessage()); // filePath to lower camelCase
        }
    }
}
