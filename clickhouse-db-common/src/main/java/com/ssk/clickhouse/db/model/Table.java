package com.ssk.clickhouse.db.model;

import java.util.List;
import java.util.Objects;

public class Table {

    private String database;
    private String name;
    private List<Column> column;

    public Table(String dbName, String name) {
        this.database = dbName;
        this.name = name;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Column> getColumn() {
        return column;
    }

    public void setColumn(List<Column> column) {
        this.column = column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Table table = (Table) o;
        return Objects.equals(database, table.database) && Objects.equals(name, table.name) && Objects.equals(column, table.column);
    }

    @Override
    public int hashCode() {
        return Objects.hash(database, name, column);
    }

    @Override
    public String toString() {
        return "Table{" +
                "database='" + database + '\'' +
                ", name='" + name + '\'' +
                ", column=" + column +
                '}';
    }
}
