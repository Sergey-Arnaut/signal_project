package com.data_management;

import java.io.IOException;
import java.nio.file.*;
import java.util.stream.Stream;

/**
 * Reads all “<label>.txt” files from a base directory, parses each
 * line of the form:
 *
 *   Patient ID: <id>, Timestamp: <ts>, Label: <label>, Data: <value>
 *
 * and adds it into the supplied DataStorage.
 */
public class FileDataReader implements DataReader {
    private final Path baseDir;
    private DataStorage storage;  // holds the DataStorage instance during a read

    /**
     * @param baseDirectory the directory where the simulator wrote its .txt files
     */
    public FileDataReader(String baseDirectory) {
        this.baseDir = Paths.get(baseDirectory); //
    }

    /**
     * Walks through every .txt file in baseDir, parses each line
     * and calls storage.addPatientData(...)
     */
    @Override
    public void readData(DataStorage storage) {
        // capture the storage reference so parseAndStore can use it
        this.storage = storage;

        try (Stream<Path> files = Files.list(baseDir)) {
            files
                    .filter(p -> p.toString().endsWith(".txt"))
                    .forEach(this::processFile);
        } catch (IOException e) {
            throw new RuntimeException("Failed to list files in " + baseDir, e);
        }
    }

    private void processFile(Path file) {
        try (Stream<String> lines = Files.lines(file)) {
            lines.forEach(this::parseAndStore);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read " + file, e);
        }
    }

    private void parseAndStore(String line) {
        // Expected format:
        //   Patient ID: 1, Timestamp: 12345, Label: Foo, Data: 3.14
        String[] parts = line.split(",\\s*");
        int patientId    = Integer.parseInt(parts[0].split(":\\s*")[1]);
        long timestamp   = Long.parseLong(parts[1].split(":\\s*")[1]);
        String label     = parts[2].split(":\\s*")[1];
        String dataStr   = parts[3].split(":\\s*")[1].replace("%", "");

        double value;
        // parse numeric (handles both integer and decimal values)
        if (dataStr.matches("\\d+\\.\\d+")) {
            value = Double.parseDouble(dataStr);
        } else {
            value = Double.parseDouble(dataStr);
        }

        // now that `this.storage` is set, we can store the parsed record
        this.storage.addPatientData(patientId, value, label, timestamp);
    }
}
