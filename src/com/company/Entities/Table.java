package com.company.Entities;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private List<Row> rows =new ArrayList<>();
    private List<Attribute> tableAttributes=new ArrayList<>();
    private String tableName;

    public Table(List<Attribute> tableAttributes, String tableName) {
        this.tableAttributes = tableAttributes;
        this.tableName = tableName;
    }
    public Table(String tableName){
        this.tableName=tableName;
    }

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

    public List<Attribute> getTableAttributes() {
        return tableAttributes;
    }

    public void setTableAttributes(List<Attribute> tableAttributes) {
        this.tableAttributes = tableAttributes;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public String toString() {
        return "Table{" +
                "rows=" + rows +
                ", tableAttributes=" + tableAttributes +
                ", tableName='" + tableName + '\'' +
                '}';
    }
}
