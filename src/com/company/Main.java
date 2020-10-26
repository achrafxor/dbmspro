package com.company;

import com.company.Exceptions.IdValueDuplicationException;
import com.company.Services.TableService;
import org.json.JSONException;

import java.sql.SQLOutput;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IdValueDuplicationException, ParseException, ClassCastException, JSONException {
        List<Object> values=new ArrayList<>();
        values.add(5);


        TableService ts=new TableService();


        System.out.println(ts.createTable( "xxxx"));
        ts.insertRow(values);

        ts.SaveTabel();
        System.out.println(ts.table);
    }
}
