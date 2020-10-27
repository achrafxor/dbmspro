package com.company.Views;

import com.company.Entities.Table;

import java.util.Scanner;

public class DisplayerDb {
    public Table dbCreateTableView(){
        Scanner sc=new Scanner(System.in);
        String tableName;
        System.out.println("give the table name");
        tableName=sc.nextLine();
        return new Table(tableName);
    }
}
