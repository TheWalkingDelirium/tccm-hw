package com.ataccama.hw.model;

import java.util.Objects;

public class Table {
    public final static String TABLE_CAT = "TABLE_CAT";
    public final static String TABLE_SCHEM = "TABLE_SCHEM";
    public final static String TABLE_NAME = "TABLE_NAME";
    public final static String TABLE_TYPE = "TABLE_TYPE";
    public final static String REMARKS = "REMARKS";
    public final static String TYPE_CAT = "TYPE_CAT";
    public final static String TYPE_SCHEM = "TYPE_SCHEM";
    public final static String TYPE_NAME = "TYPE_NAME";
    public final static String SELF_REFERENCING_COL_NAME = "SELF_REFERENCING_COL_NAME";
    public final static String REF_GENERATION = "REF_GENERATION";

    private final String catalog;
    private final String schema;
    private final String name;
    private final String type;
    private final String commentary;
    private final String typeCatalog;
    private final String typeSchema;
    private final String typeName;
    private final String selfRefColName;
    private final String refGeneration;

    public Table(final String catalog,
                 final String schema,
                 final String name,
                 final String type,
                 final String commentary,
                 final String typeCatalog,
                 final String typeSchema,
                 final String typeName,
                 final String selfRefColName,
                 final String refGeneration)
    {
        this.catalog = catalog;
        this.schema = schema;
        this.name = name;
        this.type = type;
        this.commentary = commentary;
        this.typeCatalog = typeCatalog;
        this.typeSchema = typeSchema;
        this.typeName = typeName;
        this.selfRefColName = selfRefColName;
        this.refGeneration = refGeneration;
    }

    public String getCatalog() {
        return catalog;
    }

    public String getSchema() {
        return schema;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getCommentary() {
        return commentary;
    }

    public String getTypeCatalog() {
        return typeCatalog;
    }

    public String getTypeSchema() {
        return typeSchema;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getSelfRefColName() {
        return selfRefColName;
    }

    public String getRefGeneration() {
        return refGeneration;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Table table = (Table) o;
        return Objects.equals(catalog, table.catalog) &&
                Objects.equals(schema, table.schema) &&
                Objects.equals(name, table.name) &&
                Objects.equals(type, table.type) &&
                Objects.equals(commentary, table.commentary) &&
                Objects.equals(typeCatalog, table.typeCatalog) &&
                Objects.equals(typeSchema, table.typeSchema) &&
                Objects.equals(typeName, table.typeName) &&
                Objects.equals(selfRefColName, table.selfRefColName) &&
                Objects.equals(refGeneration, table.refGeneration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(catalog, schema, name, type, commentary, typeCatalog, typeSchema, typeName, selfRefColName, refGeneration);
    }
}
