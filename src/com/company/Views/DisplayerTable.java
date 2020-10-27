package com.company.Views;


import com.company.Exceptions.IdValueDuplicationException;

import java.sql.SQLOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DisplayerTable {
    public void display(Object obj) {
        System.out.println(obj);
    }

    public List createTableAttributesNameView() {
        System.out.println("invoked view");
        List<String> attributes = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        Scanner sc2 = new Scanner(System.in);
        String attribute;
        char choice;
        Boolean complete = true;
        do {
            System.out.println("you wanna add an attribute y ?");
            choice = sc.next().charAt(0);
            if (choice == 'y') {
                System.out.println("tap the attribute name?");
                attribute = sc2.nextLine();
                attribute = attribute.toUpperCase();
                if (attributes.contains(attribute)) {
                    System.out.println("attribute name must be unique !");

                } else
                    attributes.add(attribute);

            } else {
                complete = false;
            }

        } while (complete);
        return attributes;

    }

    public List createTableAttributesTypeView(List<String> names) {
        List<String> types = new ArrayList<>();
        Boolean increment = true;
        String type;
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < names.size(); i++) {
            do {
                System.out.println("give the type of " + names.get(i));
                type = sc.nextLine();
                type = type.toUpperCase();
                System.out.println(type);
                if ((type.equals("STRING") || type.equals("INT") || type.equals("DATE"))) {
                    System.out.println("TRUE TYPE");
                    increment = true;
                } else {
                    System.out.println("unsupported type :check the JSQL docs");
                    increment = false;
                }

            } while (!increment);
            types.add(type);
        }
        return types;
    }

    public String definePrimaryKeyView(List<String> names) {
        String primaryKey;
        Boolean valid = false;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("add a primary key");
            primaryKey = sc.nextLine();
            primaryKey = primaryKey.toUpperCase();
            if (names.contains(primaryKey)) {
                valid = true;

            } else
                System.out.println(primaryKey + " field does not exist");
        } while (!valid);
        return primaryKey;
    }
    public String defineTableReferenceName(String key){
        Scanner sc=new Scanner(System.in);
        String tabelReferenceName;
        System.out.println("define the table reference name");
        tabelReferenceName=sc.nextLine();
        return tabelReferenceName;
    }

    public List defineForeignKeysView(List<String> names) {
        List<String> foreignKeys = new ArrayList<>();
        String foreignKey;
        char choice = 'y';
        Boolean valid = false;
        Scanner sc = new Scanner(System.in);
        Scanner sc2 = new Scanner(System.in);
        do {
            System.out.println("add a foreign key ?");
            choice = sc.next().charAt(0);
            if (choice != 'y') {
                break;
            }
            System.out.println("tap the name of foreign key ");
            foreignKey = sc2.nextLine();
            foreignKey = foreignKey.toUpperCase();
            if (names.contains(foreignKey)) {
                valid = true;
                foreignKeys.add(foreignKey);
            } else
                System.out.println(foreignKey + " field does not exist");
        } while (!valid || choice == 'y');

        return foreignKeys;
    }


    public List<Object> createTableRowView(Map typesAndNamsOftableAttributes,List<Object> keys,int indexOfPrimaryKey) throws InputMismatchException, ParseException, IdValueDuplicationException, ParseException {
        List<Object> values=new ArrayList<>();
        Iterator mapIterator = typesAndNamsOftableAttributes.entrySet().iterator();
        int intReceiver=0;
        String stringReceiver="";
        String dateReceiver=null;
        Date date;
        Scanner intSc = new Scanner(System.in);
        Scanner stringSc = new Scanner(System.in);
        Scanner dateSc = new Scanner(System.in);
        while (mapIterator.hasNext()) {
            Map.Entry pair = (Map.Entry) mapIterator.next();


            if ("INT".equals(pair.getValue())) {
                System.out.println("tap the value of "+pair.getKey());
                intReceiver=intSc.nextInt();
                values.add(intReceiver);
            } else if ("STRING".equals(pair.getValue())) {
                System.out.println("tap the value of "+pair.getKey());
                stringReceiver=stringSc.nextLine();
                values.add(stringReceiver);
            } else if ("DATE".equals(pair.getValue())) {
                System.out.println("tap the value of "+pair.getKey());
                dateReceiver=dateSc.nextLine();
                SimpleDateFormat format=new SimpleDateFormat("MM-dd-yyyy");
                date=format.parse(dateReceiver);
                values.add(date);
            }



        }
        if(keys.contains(values.get(indexOfPrimaryKey)))
            throw new IdValueDuplicationException("id value must be unique");

        return values;
    }
}
