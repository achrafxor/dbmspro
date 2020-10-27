package com.company.Exceptions;

public class TableReferenceNameNotExistException extends Exception {
    public TableReferenceNameNotExistException(){
        super("table reference name does not exist");
    }

}
