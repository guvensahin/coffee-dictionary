package com.guvensahin.coffeedictionary.Models;

public class Category {
    public static final String TABLE_NAME = "Category";
    public static final String COLUMN_ID = "Id";
    public static final String COLUMN_NAME = "Name";

    // create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY,"
                    + COLUMN_NAME + " TEXT NOT NULL"
                    + ")";


    private Integer id;
    private String name;


    public void setId(Integer id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
}
