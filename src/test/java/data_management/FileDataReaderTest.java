package com.data_management;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

class FileDataReaderTest {

    @TempDir
    Path tempDir;

    private DataStorage storage;
    private FileDataReader reader;

    @BeforeEach
    void setUp() {
        storage = new DataStorage();
        reader = new FileDataReader(tempDir.toString());
    }

    @Test
    void testReadData_parsesAndStoresAllRecords() throws IOException {
        // 1) Set up two files, "Foo.txt" and "Bar.txt"
        Path foo = tempDir.resolve("Foo.txt");
        Path bar = tempDir.resolve("Bar.txt");

        String line1 = "Patient ID: 1, Timestamp: 1000, Label: Foo, Data: 3.14";
        String line2 = "Patient ID: 2, Timestamp: 2000, Label: Bar, Data: 42";
        Files.write(foo, List.of(line1), StandardOpenOption.CREATE);
        Files.write(bar, List.of(line2), StandardOpenOption.CREATE);

        // 2) Read them
        reader.readData(storage);

        // 3) Assert storage now has exactly those two records:
        List<PatientRecord> recs1 = storage.getRecords(1, 1000, 1000);
        assertEquals(1, recs1.size(), "Should find one record for patient 1");
        assertEquals(3.14, recs1.get(0).getMeasurementValue(), 1e-9);

        List<PatientRecord> recs2 = storage.getRecords(2, 2000, 2000);
        assertEquals(1, recs2.size(), "Should find one record for patient 2");
        assertEquals(42.0, recs2.get(0).getMeasurementValue(), 1e-9);
    }
}
