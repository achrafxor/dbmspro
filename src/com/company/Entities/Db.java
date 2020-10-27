package com.company.Entities;

import java.util.ArrayList;
import java.util.List;

public class Db {
    String databaseName;
    List<Table> tables=new ArrayList<>();
    public Db(String databaseName){
        this.databaseName=databaseName;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

    @Override
    public String toString() {
        return "Db{" +
                "databaseName='" + databaseName + '\'' +
                ", tables=" + tables +
                '}';
    }
}
