package com.ataccama.hw.model;

public class ColumnStat {
    private final String tableName;
    private final String columnName;
    private final String min;
    private final String max;
    private final String avg;
    private final String median;

    public ColumnStat(final String tableName,
                      final String columnName,
                      final String min,
                      final String max,
                      final String avg,
                      final String median)
    {
        this.tableName = tableName;
        this.columnName = columnName;
        this.min = min;
        this.max = max;
        this.avg = avg;
        this.median = median;
    }

    public String getTableName() {
        return tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getMin() {
        return min;
    }

    public String getMax() {
        return max;
    }

    public String getAvg() {
        return avg;
    }

    public String getMedian() {
        return median;
    }
}
