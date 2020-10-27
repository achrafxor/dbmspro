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
    public void insertIntoTable(List<Object> values) {

    }

    @Override

    public Table createTable(String tableName) throws TableReferenceNameNotExistException {
        this.table=new Table(tableName);
        createTableAttributes();
        return table;
    }

        public void createTableAttributes() throws TableReferenceNameNotExistException {
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
            for(int i=0;i<names.size();i++){
                if(names.get(i).equals(primaryKey)&&foreignKeys.contains(names.get(i))){

                    tableRefernceName=d.defineTableReferenceName(names.get(i));
                   List<String> listOfTableNames= db.getTables().stream().map(x->x.getTableName()).collect(Collectors.toList());
                   if(!listOfTableNames.contains(equals(tableRefernceName)) || listOfTableNames.size()==0){
                       throw new TableReferenceNameNotExistException();
                   }else{
                       tableAttributes.add(new Attribute(names.get(i),types.get(i),true,true,tableRefernceName));
                   }

                } else if(names.get(i).equals(primaryKey))
                    tableAttributes.add(new Attribute(names.get(i),types.get(i),true,false));
                else if(foreignKeys.contains(names.get(i))){
                    tableRefernceName=d.defineTableReferenceName(names.get(i));
                    System.out.println("table reference name is " +tableRefernceName);
                    List<String> listOfTableNames= db.getTables().stream().map(x->x.getTableName()).collect(Collectors.toList());
                    if(!listOfTableNames.contains(equals(tableRefernceName)) || listOfTableNames.size()==0){
                        throw new TableReferenceNameNotExistException();
                    }else{
                        tableAttributes.add(new Attribute(names.get(i),types.get(i),false,true,tableRefernceName));
                    }

                } else
                    tableAttributes.add(new Attribute(names.get(i),types.get(i),false,false));


            }
            this.table.setTableAttributes(tableAttributes);
        }
    public void insertRow(List<Object> values) throws ParseException, ClassCastException, IdValueDuplicationException, ParseException {
        System.out.println("insert row invoked");
        Map<String,Object> temporarlyRowValues=new HashMap<>();
        Map<String,Object> rowValues=new HashMap();
        List<Object> keys=new ArrayList<>();
        if(values.size()!=this.table.getTableAttributes().size()){
            System.out.println("error throw exception values length not match");
        }else{
            for(int i=0;i<this.table.getTableAttributes().size();i++){
                if(this.table.getTableAttributes().get(i).getType().equals("INT")){
                    if(values.get(i) instanceof Integer){
                        temporarlyRowValues.put(this.table.getTableAttributes().get(i).getName(),values.get(i));
                    }else{

                    }
                }else if(this.table.getTableAttributes().get(i).getType().equals("DATE")){
                    SimpleDateFormat format=new SimpleDateFormat("MM-dd-yyyy");
                    Date d=format.parse((String) values.get(i));
                    temporarlyRowValues.put(this.table.getTableAttributes().get(i).getName(),values.get(i));

                }else{
                    String s= String.valueOf(values.get(i)) ;
                    temporarlyRowValues.put(this.table.getTableAttributes().get(i).getName(),values.get(i));

                }
            }
        }
        keys=  this.table.getRows().stream().flatMap(x->x.getRow().entrySet().stream().filter(e->e.getKey().equals(primaryKeyName()))).map(key->key.getValue()).collect(Collectors.toList());

        if(!(keys.contains((temporarlyRowValues.get(primaryKeyName()))))){

            rowValues=temporarlyRowValues;
            List<Row> rows=this.table.getRows();
            rows.add(new Row(rowValues));
            this.table.setRows(rows);
            System.out.println("not duplicated id");
        }else{
            throw new IdValueDuplicationException("id value must be unique");
        }


        keys=  this.table.getRows().stream().flatMap(x->x.getRow().entrySet().stream().filter(e->e.getKey().equals(primaryKeyName()))).map(key->key.getValue()).collect(Collectors.toList());
        System.out.println("keys are " +keys);

    }
    public String primaryKeyName(){
        String name="";
        for(int i=0;i< this.table.getTableAttributes().size();i++){
            if(this.table.getTableAttributes().get(i).getPrimaryKey()){
                name=this.table.getTableAttributes().get(i).getName();
            }
        }
        return name;
    }
    public void SaveTabel() throws JSONException {
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
            FileWriter filewriter=new FileWriter("C:\\Users\\asaoud\\Desktop\\javaworkspace\\file.json");
            filewriter.write(tableDetails.toString());
            filewriter.flush();
            filewriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
