package com.ataccama.hw.model;

public class TableStat {
    private final int numberOfRecords;
    private final int numberOfAttrubutes;

    public TableStat(final int numberOfRecords, final int numberOfAttrubutes) {
        this.numberOfRecords = numberOfRecords;
        this.numberOfAttrubutes = numberOfAttrubutes;
    }

    public int getNumberOfRecords() {
        return numberOfRecords;
    }

    public int getNumberOfAttrubutes() {
        return numberOfAttrubutes;
    }
}
