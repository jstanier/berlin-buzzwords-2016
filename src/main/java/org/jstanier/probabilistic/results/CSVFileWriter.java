package org.jstanier.probabilistic.results;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVFileWriter {

    private static final Object[] FILE_HEADER = { "label", "value" };

    private String outputLocation;
    private List<Result> results = new ArrayList<>();

    public CSVFileWriter(String outputLocation) {
        this.outputLocation = outputLocation;
    }

    public void addResult(String label, long value) {
        results.add(new Result(label, value));
    }

    public void writeOutput() {
        FileWriter fileWriter = null;
        CSVPrinter csvPrinter = null;
        CSVFormat csvFormat = CSVFormat.DEFAULT.withRecordSeparator('\n');
        try {
            fileWriter = new FileWriter(outputLocation);
            csvPrinter = new CSVPrinter(fileWriter, csvFormat);
            for (Result result: results) {
                csvPrinter.printRecord(Arrays.asList(result.getLabel(), result.getValue()));
            }
        } catch (IOException e) {
            System.err.println("Error writing CSV to file at " + outputLocation);
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
                csvPrinter.close();
            } catch (IOException e) {
                System.err.println("Error closing CSV writer");
            }
        }
    }
}
