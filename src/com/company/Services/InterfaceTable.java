package com.company.Services;

import com.company.Entities.Table;

import java.util.List;

public interface InterfaceTable {
    public void insertIntoTable(List<Object> values);
    public Table createTable(String tableName);

}
