package com.company.Services;

import com.company.Entities.Table;
import com.company.Exceptions.TableReferenceNameNotExistException;

import java.util.List;

public interface InterfaceTable {
    public void insertIntoTable(List<Object> values);
    public Table createTable(String tableName) throws TableReferenceNameNotExistException;

}
