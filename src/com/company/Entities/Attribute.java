package com.company.Entities;

public class Attribute {
    private String type;
    private String name;
    private Boolean isPrimaryKey;
    private Boolean isForeignKey;
    private String tableReferenceName;

    public Attribute( String name,String type, Boolean isPrimaryKey, Boolean isForeignKey, String tableReferenceName) {
        this.type = type;
        this.name = name;
        this.isPrimaryKey = isPrimaryKey;
        this.isForeignKey = isForeignKey;
        this.tableReferenceName = tableReferenceName;
    }
    public Attribute( String name,String type, Boolean isPrimaryKey, Boolean isForeignKey) {
        this.type = type;
        this.name = name;
        this.isPrimaryKey = isPrimaryKey;
        this.isForeignKey = isForeignKey;

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getPrimaryKey() {
        return isPrimaryKey;
    }

    public void setPrimaryKey(Boolean primaryKey) {
        isPrimaryKey = primaryKey;
    }

    public Boolean getForeignKey() {
        return isForeignKey;
    }

    public void setForeignKey(Boolean foreignKey) {
        isForeignKey = foreignKey;
    }

    public String getTableReferenceName() {
        return tableReferenceName;
    }

    public void setTableReferenceName(String tableReferenceName) {
        this.tableReferenceName = tableReferenceName;
    }

    @Override
    public String toString() {
        return "Attribute{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", isPrimaryKey=" + isPrimaryKey +
                ", isForeignKey=" + isForeignKey +
                ", tableReferenceName='" + tableReferenceName + '\'' +
                '}';
    }
}
