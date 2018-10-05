package com.guvensahin.coffeedictionary;

import java.io.Serializable;

public class Entry implements Serializable {

    public static final String TABLE_NAME = "Entry";
    public static final String COLUMN_ID = "Id";
    public static final String COLUMN_NAMEENG = "NameEng";
    public static final String COLUMN_NAMETUR = "NameTur";
    public static final String COLUMN_DESCRIPTION = "Description";
    public static final String COLUMN_CATEGORYID = "CategoryId";

    // create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY,"
                    + COLUMN_NAMEENG + " TEXT NOT NULL,"
                    + COLUMN_NAMETUR + " TEXT NOT NULL,"
                    + COLUMN_CATEGORYID + " INTEGER NOT NULL,"
                    + COLUMN_DESCRIPTION + " TEXT NOT NULL,"
                    + "FOREIGN KEY(CategoryId) REFERENCES Category(Id)"
                    + ")";


    private Integer id;
    private String nameEng;
    private String nameTur;
    private Integer categoryId;
    private String description;


    public void setId(Integer id) {
        this.id = id;
    }
    public void setNameEng(String _nameEng)
    {
        nameEng = _nameEng;
    }
    public void setNameTur(String _nameTur)
    {
        nameTur = _nameTur;
    }
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }
    public String getNameEng() {
        return nameEng;
    }
    public String getNameTur() {
        return nameTur;
    }
    public Integer getCategoryId() {
        return categoryId;
    }
    public String getDescription() {
        return description;
    }

    public String getName()
    {
        return nameEng.equals(nameTur) ? nameEng : nameEng + " (" + nameTur + ")";
    }
}