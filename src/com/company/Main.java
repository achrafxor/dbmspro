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
        values.add(5);
        values.add(8);
        DbService db=new DbService();
        db.buildDataBaseTable("person");
        db.buildDataBaseTable("location");
        db.buildDataBaseTable("job");
    }
}
