package com.company.Entities;

import java.util.HashMap;
import java.util.Map;

public class Row {
    Map<String,Object> row=new HashMap<>();
    public Row(){

    }
    public Row(Map<String, Object> row) {

        this.row = row;
    }

    public Map<String, Object> getRow() {
        return row;
    }

    public void setRow(Map<String, Object> row) {
        this.row = row;
    }

    @Override
    public String toString() {
        return "Row{" +
                "row=" + row +
                '}';
    }
}
