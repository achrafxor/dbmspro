package com.company.Services;

import com.company.Entities.Db;
import com.company.Entities.Row;
import com.company.Entities.Table;
import com.company.Exceptions.IdValueDuplicationException;
import com.company.Exceptions.TableReferenceNameNotExistException;
import com.company.Views.DisplayerDb;
import org.json.JSONException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class DbService {
    DisplayerDb displayer=new DisplayerDb();
    Db database=new Db("ssss");
    TableService tableService=new TableService();
    public void buildDataBaseTable(String tableName) throws TableReferenceNameNotExistException, JSONException {
        Table table=tableService.createTable(tableName,this.database);
        List<Table> tables =database.getTables();
        tables.add(table);
        database.setTables(tables);

    }
    //TODO
   public void insertIntoTable(String tableName,List<Object> values) throws IdValueDuplicationException, ParseException, JSONException {
       System.out.println("inert into table invoked");
       for (Table table:database.getTables()
            ) {
           if(table.getTableName()==tableName){

               tableService=new TableService();
               List<Row> rows= table.getRows();

               table.setRows(tableService.insertRow(values,table));
               tableService.saveTabel(tableName,table);
           }
       }
   }
   public void showDataBase(){
       System.out.println(this.database);
   }

}
