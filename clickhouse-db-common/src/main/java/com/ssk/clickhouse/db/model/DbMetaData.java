package com.ssk.clickhouse.db.model;

import java.util.List;
import java.util.Objects;

public class DbMetaData {

    private List<Database> databases;

    public List<Database> getDatabases() {
        return databases;
    }

    public void setDatabases(List<Database> databases) {
        this.databases = databases;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DbMetaData that = (DbMetaData) o;
        return Objects.equals(databases, that.databases);
    }

    @Override
    public int hashCode() {
        return Objects.hash(databases);
    }

    @Override
    public String toString() {
        return "DbMetaData{" +
                "databases=" + databases +
                '}';
    }
}
