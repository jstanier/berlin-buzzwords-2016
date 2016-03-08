package org.jstanier.probabilistic.benchmarks;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Sets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.jstanier.probabilistic.results.CSVFileWriter;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class BloomFilterAndSetBenchmark {

    private static final List<Integer> sampleSizes = Arrays.asList(10, 100, 1000, 10000, 100000, 1000000, 10000000);

    private BloomFilter<String> bloomFilter;
    private Set<String> set;
    private CSVFileWriter csvFileWriter;

    public void profileAllSizes() throws IOException {
        csvFileWriter = new CSVFileWriter("bloomfilter_vs_set_insertions.csv");
        for (Integer size : sampleSizes) {
            profileOneSize(size);
        }
        csvFileWriter.writeOutput();
    }

    public void profileOneSize(Integer size) {
        initialiseDataStructures(size);
        insertIntoBloomFilter(size);
        insertIntoSet(size);
    }

    private void initialiseDataStructures(Integer entries) {
        bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charset.forName("UTF-8")), entries);
        set = Sets.newHashSet();
    }

    private void insertIntoSet(Integer entries) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        for (int i = 0; i < entries; i++) {
            set.add(UUID.randomUUID().toString());
        }
        long elapsed = stopwatch.stop().elapsed(TimeUnit.MILLISECONDS);
        System.out.println("Insertion with " + entries + " entries Set: " + elapsed + "ms");
        csvFileWriter.addResult("Set (" + entries + ")", elapsed);
    }

    private void insertIntoBloomFilter(Integer entries) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        for (int i = 0; i < entries; i++) {
            bloomFilter.put(UUID.randomUUID().toString());
        }
        long elapsed = stopwatch.stop().elapsed(TimeUnit.MILLISECONDS);
        System.out.println("Insertion with " + entries + " entries Bloom: " + elapsed + "ms");
        csvFileWriter.addResult("Bloom filter (" + entries + ")", elapsed);
    }
}
