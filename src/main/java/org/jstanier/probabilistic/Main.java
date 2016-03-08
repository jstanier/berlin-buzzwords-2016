package org.jstanier.probabilistic;

import org.jstanier.probabilistic.benchmarks.BloomFilterAndSetBenchmark;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        new BloomFilterAndSetBenchmark().profileAllSizes();

        while (true); // Run forever to allow us to do profiling.
    }
}
