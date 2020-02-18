package com.ataccama.hw.model;


public class Column {
    public final static String TABLE_CAT = "TABLE_CAT";
    public final static String TABLE_SCHEM = "TABLE_SCHEM";
    public final static String TABLE_NAME = "TABLE_NAME";
    public final static String COLUMN_NAME = "COLUMN_NAME";
    public final static String DATA_TYPE = "DATA_TYPE";
    public final static String TYPE_NAME = "TYPE_NAME";
    public final static String COLUMN_SIZE = "COLUMN_SIZE";
    public final static String BUFFER_LENGTH = "BUFFER_LENGTH";
    public final static String DECIMAL_DIGITS = "DECIMAL_DIGITS";
    public final static String NUM_PREC_RADIX = "NUM_PREC_RADIX";
    public final static String NULLABLE = "NULLABLE";
    public final static String REMARKS = "REMARKS";
    public final static String COLUMN_DEF = "COLUMN_DEF";
    public final static String SQL_DATA_TYPE = "SQL_DATA_TYPE";
    public final static String SQL_DATETIME_SUB = "SQL_DATETIME_SUB";
    public final static String CHAR_OCTET_LENGTH = "CHAR_OCTET_LENGTH";
    public final static String ORDINAL_POSITION = "ORDINAL_POSITION";
    public final static String IS_NULLABLE = "IS_NULLABLE";
    public final static String SCOPE_CATALOG = "SCOPE_CATALOG";
    public final static String SCOPE_SCHEMA = "SCOPE_SCHEMA";
    public final static String SCOPE_TABLE = "SCOPE_TABLE";
    public final static String SOURCE_DATA_TYPE = "SOURCE_DATA_TYPE";
    public final static String IS_AUTOINCREMENT = "IS_AUTOINCREMENT";
    public final static String IS_GENERATEDCOLUMN = "IS_GENERATEDCOLUMN";

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
