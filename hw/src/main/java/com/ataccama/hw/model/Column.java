package com.ataccama.hw.model;


public class Column {
    private final String tableCat;
    private final String tableSchem;
    private final String tableName;
    private final String columnName;
    private final String dataType;
    private final String typeName;
    private final String columnSize;
    private final String bufferLength;
    private final String decimalDigits;
    private final String numPrecRadix;
    private final String nullable;
    private final String remarks;
    private final String columnDef;
    private final String sqlDataType;
    private final String sqlDatetimeSub;
    private final String charOctetLength;
    private final String ordinalPosition;
    private final String isNullable;
    private final String scopeCatalog;
    private final String scopeSchema;
    private final String scopeTable;
    private final String sourceDataType;
    private final String isAutoincrement;
    private final String isGeneratedcolumn;

    public Column(
            String tableCat,
            String tableSchem,
            String tableName,
            String columnName,
            String dataType,
            String typeName,
            String columnSize,
            String bufferLength,
            String decimalDigits,
            String numPrecRadix,
            String nullable,
            String remarks,
            String columnDef,
            String sqlDataType,
            String sqlDatetimeSub,
            String charOctetLength,
            String ordinalPosition,
            String isNullable,
            String scopeCatalog,
            String scopeSchema,
            String scopeTable,
            String sourceDataType,
            String isAutoincrement,
            String isGeneratedcolumn) {
        this.tableCat = tableCat;
        this.tableSchem = tableSchem;
        this.tableName = tableName;
        this.columnName = columnName;
        this.dataType = dataType;
        this.typeName = typeName;
        this.columnSize = columnSize;
        this.bufferLength = bufferLength;
        this.decimalDigits = decimalDigits;
        this.numPrecRadix = numPrecRadix;
        this.nullable = nullable;
        this.remarks = remarks;
        this.columnDef = columnDef;
        this.sqlDataType = sqlDataType;
        this.sqlDatetimeSub = sqlDatetimeSub;
        this.charOctetLength = charOctetLength;
        this.ordinalPosition = ordinalPosition;
        this.isNullable = isNullable;
        this.scopeCatalog = scopeCatalog;
        this.scopeSchema = scopeSchema;
        this.scopeTable = scopeTable;
        this.sourceDataType = sourceDataType;
        this.isAutoincrement = isAutoincrement;
        this.isGeneratedcolumn = isGeneratedcolumn;
    }

    public String getTableCat() {
        return tableCat;
    }

    public String getTableSchem() {
        return tableSchem;
    }

    public String getTableName() {
        return tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getDataType() {
        return dataType;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getColumnSize() {
        return columnSize;
    }

    public String getBufferLength() {
        return bufferLength;
    }

    public String getDecimalDigits() {
        return decimalDigits;
    }

    public String getNumPrecRadix() {
        return numPrecRadix;
    }

    public String getNullable() {
        return nullable;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getColumnDef() {
        return columnDef;
    }

    public String getSqlDataType() {
        return sqlDataType;
    }

    public String getSqlDatetimeSub() {
        return sqlDatetimeSub;
    }

    public String getCharOctetLength() {
        return charOctetLength;
    }

    public String getOrdinalPosition() {
        return ordinalPosition;
    }

    public String getIsNullable() {
        return isNullable;
    }

    public String getScopeCatalog() {
        return scopeCatalog;
    }

    public String getScopeSchema() {
        return scopeSchema;
    }

    public String getScopeTable() {
        return scopeTable;
    }

    public String getSourceDataType() {
        return sourceDataType;
    }

    public String getIsAutoincrement() {
        return isAutoincrement;
    }

    public String getIsGeneratedcolumn() {
        return isGeneratedcolumn;
    }
}
