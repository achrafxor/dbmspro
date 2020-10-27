package com.company.Services;

import com.company.Entities.Db;
import com.company.Entities.Row;
import com.company.Entities.Table;
import com.company.Exceptions.IdValueDuplicationException;
import com.company.Exceptions.TableReferenceNameNotExistException;
import com.company.Views.DisplayerDb;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class DbService {
    DisplayerDb displayer=new DisplayerDb();
    Db database=new Db("ssss");
    TableService tableService=new TableService();
    public void buildDataBaseTable(String tableName) throws TableReferenceNameNotExistException {
        Table table=tableService.createTable(tableName);
        List<Table> tables =database.getTables();
        tables.add(table);
        database.setTables(tables);

    }
   public void insertIntoTable(String tableName,List<Object> values) throws IdValueDuplicationException, ParseException {
       for (Table table:database.getTables()
            ) {
           if(table.getTableName()==tableName){
               tableService=new TableService();
               tableService.insertRow(values);
               List<Row> rows= table.getRows();
               rows.add(tableService.table.getRows().get(0));
               table.setRows(rows);
           }
       }
   }

}
