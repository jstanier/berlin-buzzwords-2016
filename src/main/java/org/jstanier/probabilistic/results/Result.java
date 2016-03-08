package org.jstanier.probabilistic.results;

public class Result {

    private String label;
    private long value;

    public Result(String label, long value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public long getValue() {
        return value;
    }
}
