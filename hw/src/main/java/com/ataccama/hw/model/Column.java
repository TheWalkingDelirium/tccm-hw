package com.ataccama.hw.model;


public class Column {
    private final String tableCatalog;
    private final String schemaCatalog;
    private final String tableName;
    private final String columnName;
    private final String columnDataType;
    private final String columnDataTypeName;
    private final String columnSize;
    private final String columnComments;
    private final String columnDefaultValue;
    private final String columnIndex;
    private final String isAutoincrement;
    private final String isGenerated;

    public Column(final String tableCatalog,
                  final String schemaCatalog,
                  final String tableName,
                  final String columnName,
                  final String columnDataType,
                  final String columnDataTypeName,
                  final String columnSize,
                  final String columnComments,
                  final String columnDefaultValue,
                  final String columnIndex,
                  final String isAutoincrement,
                  final String isGenerated)
    {
        this.tableCatalog = tableCatalog;
        this.schemaCatalog = schemaCatalog;
        this.tableName = tableName;
        this.columnName = columnName;
        this.columnDataType = columnDataType;
        this.columnDataTypeName = columnDataTypeName;
        this.columnSize = columnSize;
        this.columnComments = columnComments;
        this.columnDefaultValue = columnDefaultValue;
        this.columnIndex = columnIndex;
        this.isAutoincrement = isAutoincrement;
        this.isGenerated = isGenerated;
    }

    public String getTableCatalog() {
        return tableCatalog;
    }

    public String getSchemaCatalog() {
        return schemaCatalog;
    }

    public String getTableName() {
        return tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getColumnDataType() {
        return columnDataType;
    }

    public String getColumnDataTypeName() {
        return columnDataTypeName;
    }

    public String getColumnSize() {
        return columnSize;
    }

    public String getColumnComments() {
        return columnComments;
    }

    public String getColumnDefaultValue() {
        return columnDefaultValue;
    }

    public String getColumnIndex() {
        return columnIndex;
    }

    public String getIsAutoincrement() {
        return isAutoincrement;
    }

    public String getIsGenerated() {
        return isGenerated;
    }
}
