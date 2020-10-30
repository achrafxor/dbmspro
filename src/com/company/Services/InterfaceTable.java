package com.company.Services;

import com.company.Entities.Db;
import com.company.Entities.Table;
import com.company.Exceptions.TableReferenceNameNotExistException;

import java.util.List;

public interface InterfaceTable {
     public Table createTable(String tableName, Db db) throws TableReferenceNameNotExistException;

}
