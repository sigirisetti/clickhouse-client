package com.ssk.clickhouse.db.model;

import java.util.List;
import java.util.Objects;

public class Database {

    private String name;
    private List<Table> tables;

    public Database(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Database database = (Database) o;
        return Objects.equals(name, database.name) && Objects.equals(tables, database.tables);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, tables);
    }

    @Override
    public String toString() {
        return "Database{" +
                "name='" + name + '\'' +
                ", tables=" + tables +
                '}';
    }
}
