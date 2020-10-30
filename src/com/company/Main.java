package com.company;

import com.company.Exceptions.IdValueDuplicationException;
import com.company.Exceptions.TableReferenceNameNotExistException;
import com.company.Services.DbService;
import com.company.Services.TableService;
import org.json.JSONException;

import java.sql.SQLOutput;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IdValueDuplicationException, ParseException, ClassCastException, JSONException, TableReferenceNameNotExistException {
        List<Object> values=new ArrayList<>();
        List<Object> values2=new ArrayList<>();
        values.add(10);
        DbService db=new DbService();
        System.out.println("new table 1");
        db.buildDataBaseTable("person");
        db.showDataBase();
        db.insertIntoTable("person",values);
        db.showDataBase();
    }
}
