package com.company.Services;

import com.company.Entities.Attribute;
import com.company.Entities.Db;
import com.company.Entities.Row;
import com.company.Entities.Table;
import com.company.Exceptions.IdValueDuplicationException;
import com.company.Exceptions.TableReferenceNameNotExistException;
import com.company.Views.DisplayerTable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class TableService implements InterfaceTable {
    Db db=new Db("db");
    DisplayerTable d=new DisplayerTable();
    public Table table;

    @Override

    public Table createTable(String tableName,Db db) throws TableReferenceNameNotExistException {
        this.table=new Table(tableName);
        createTableAttributes(db);
        List<Table> tables=new ArrayList<>();
        tables=db.getTables();
        tables.add(table);
        db.setTables(tables);
        return table;
    }
        //create the table attributes
        public void createTableAttributes(Db db) throws TableReferenceNameNotExistException {
            List<String> listOfTableNames=new ArrayList<>();
            String tableRefernceName;
            List<Attribute> tableAttributes=new ArrayList<>();
            List<String> names,types,foreignKeys;
            String primaryKey="";
            names=new ArrayList<>();
            types=new ArrayList<>();
            names=d.createTableAttributesNameView();
            types=d.createTableAttributesTypeView(names);
            primaryKey=d.definePrimaryKeyView(names);
            foreignKeys=d.defineForeignKeysView(names);
            System.out.println(foreignKeys);
            if(db.getTables().size()!=0){
                listOfTableNames= db.getTables().stream().map(x->x.getTableName()).collect(Collectors.toList());
                System.out.println("list of table names "+listOfTableNames);
                System.out.println("size is not zeron");
            }else{
                System.out.println("size of db is 0");
            }

            for(int i=0;i<names.size();i++){

                if(names.get(i).equals(primaryKey)&&foreignKeys.contains(names.get(i))){
                    //if the attribuTe is a PK and FK
                    tableRefernceName=d.defineTableReferenceName(names.get(i));

                   if(!listOfTableNames.contains(equals(tableRefernceName)) || listOfTableNames.size()==0){
                       throw new TableReferenceNameNotExistException();
                   }else{
                       tableAttributes.add(new Attribute(names.get(i),types.get(i),true,true,tableRefernceName));
                   }

                } else if(names.get(i).equals(primaryKey)){
                    //if the attribuTe is a PK ONLY
                    tableAttributes.add(new Attribute(names.get(i),types.get(i),true,false));
                } else if(foreignKeys.contains(names.get(i))){
                    //if the attribuTe is a fK ONLY
                    tableRefernceName=d.defineTableReferenceName(names.get(i));
                    System.out.println("table reference name is " +tableRefernceName);

                    System.out.println("test table names list " +listOfTableNames);
                    if(!listOfTableNames.contains(tableRefernceName) || listOfTableNames.size()==0){
                        System.out.println(!listOfTableNames.contains(tableRefernceName));
                        System.out.println(listOfTableNames.size()==0);
                        System.out.println(tableRefernceName);
                        System.out.println("list of table names is" +listOfTableNames);
                        throw new TableReferenceNameNotExistException();
                    }else{
                        tableAttributes.add(new Attribute(names.get(i),types.get(i),false,true,tableRefernceName));
                    }

                } else
                    tableAttributes.add(new Attribute(names.get(i),types.get(i),false,false));


            }
                this.table.setTableAttributes(tableAttributes);

        }
    public List<Row> insertRow(List<Object> values, Table table) throws ParseException, ClassCastException, IdValueDuplicationException, ParseException {
        System.out.println("insert row invoked");
        Map<String,Object> temporarlyRowValues=new HashMap<>();
        Map<String,Object> rowValues=new HashMap();
        List<Object> keys=new ArrayList<>();
        System.out.println("table attributes "+table.getTableAttributes());
        if(values.size()!=table.getTableAttributes().size()){
            System.out.println("error throw exception values length not match");
        }else{
            for(int i=0;i<table.getTableAttributes().size();i++){
                if(table.getTableAttributes().get(i).getType().equals("INT")){
                    if(values.get(i) instanceof Integer){
                        temporarlyRowValues.put(table.getTableAttributes().get(i).getName(),values.get(i));
                    }else{

                    }
                }else if(table.getTableAttributes().get(i).getType().equals("DATE")){
                    SimpleDateFormat format=new SimpleDateFormat("MM-dd-yyyy");
                    Date d=format.parse((String) values.get(i));
                    temporarlyRowValues.put(table.getTableAttributes().get(i).getName(),values.get(i));

                }else{
                    String s= String.valueOf(values.get(i)) ;
                    temporarlyRowValues.put(table.getTableAttributes().get(i).getName(),values.get(i));

                }
            }
        }
        System.out.println(table.getRows());
        keys=  table.getRows().stream().flatMap(x->x.getRow().entrySet().stream().filter(e->e.getKey().equals(primaryKeyName(table)))).map(key->key.getValue()).collect(Collectors.toList());
        System.out.println("row values ="+temporarlyRowValues.get(primaryKeyName(table)));
        System.out.println("keys are"+ keys);
        System.out.println("key is "+primaryKeyName(table));
        if(!(keys.contains((temporarlyRowValues.get(primaryKeyName(table)))))){

            rowValues=temporarlyRowValues;
            List<Row> rows=table.getRows();
            rows.add(new Row(rowValues));
            table.setRows(rows);
            System.out.println("not duplicated id");
        }else{
            throw new IdValueDuplicationException("id value must be unique");
        }


        keys=  table.getRows().stream().flatMap(x->x.getRow().entrySet().stream().filter(e->e.getKey().equals(primaryKeyName(table)))).map(key->key.getValue()).collect(Collectors.toList());
        System.out.println("keys are " +keys);
        return table.getRows();
    }
    public String primaryKeyName(Table table){
        String name="";
        for(int i=0;i< table.getTableAttributes().size();i++){
            if(table.getTableAttributes().get(i).getPrimaryKey()){
                name=table.getTableAttributes().get(i).getName();
            }
        }
        return name;
    }
    public void saveTabel(String tableName,Table table) throws JSONException {
        String foreignKeyReference;
        JSONObject tableDetails = new JSONObject();
        JSONArray jsonArray=new JSONArray();
        Set<String> keys= table.getRows().get(0).getRow().keySet();
        List<String> keys2=new ArrayList<>(keys);
        for (Row row:table.getRows()
             ) {
            for(int i=0;i<table.getTableAttributes().size();i++){
                tableDetails.put(keys2.get(i),row.getRow().get(keys2.get(i)));
            }
        }
        System.out.println(tableDetails);
        try{
            FileWriter filewriter=new FileWriter("C:\\Users\\asaoud\\Desktop\\javaworkspace\\"+tableName+".json");
            filewriter.write(tableDetails.toString());
            filewriter.flush();
            filewriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
