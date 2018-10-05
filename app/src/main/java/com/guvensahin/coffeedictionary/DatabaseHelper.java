package com.guvensahin.coffeedictionary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "coffee_dictionary_db";
    private Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    // creating tables
    // will be called only once when the app is installed
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Category.CREATE_TABLE);
        db.execSQL(Entry.CREATE_TABLE);

        // initialize data
        new DataInitializer(this, db).run();
    }

    // upgrading database
    // called when an update is released. You need to modify the DATABASE_VERSION in order to execute this method
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Category.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Entry.TABLE_NAME);

        // create tables again
        onCreate(db);
    }

    // helper methods
    public String javaToSqlDate(Date date)
    {
        if (date == null) return null;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    public String javaToSqlDatetime(Date date)
    {
        if (date == null) return null;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }

    public Date sqlToJavaDate(String stringDate)
    {
        Date date;
        SimpleDateFormat dateFormat;

        if (TextUtils.isEmpty(stringDate))
        {
            return null;
        }

        try {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            date = dateFormat.parse(stringDate);
        }
        catch (ParseException e)
        {
            date = null;
        }

        return date;
    }

    public Context getContext() {
        return context;
    }

    // category
    public long insertCategory(SQLiteDatabase db, Integer id, String name)
    {
        boolean closeConn = false;

        // get writable database as we want to write data
        if (db == null) {
            db = this.getWritableDatabase();
            closeConn = true;
        }

        ContentValues values = new ContentValues();
        values.put(Category.COLUMN_ID, id);
        values.put(Category.COLUMN_NAME, name);

        // insert
        long recordId = db.insert(Category.TABLE_NAME, null, values);

        if (closeConn) {
            db.close();
        }

        return recordId;
    }

    public Category getCategory(int id) {
        Category category = null;

        // query
        String selectQuery = "SELECT  * FROM " + Category.TABLE_NAME
                + " WHERE " + Category.COLUMN_ID + " = " + id;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null
            && cursor.moveToFirst())
        {
            category = new Category();
            category.setId(cursor.getInt(cursor.getColumnIndex(Category.COLUMN_ID)));
            category.setName(cursor.getString(cursor.getColumnIndex(Category.COLUMN_NAME)));
        }

        db.close();
        return category;
    }

    public ArrayList<Category> getCategories() {
        ArrayList<Category> list = new ArrayList<Category>();

        // query
        String selectQuery = "SELECT  * FROM " + Category.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst())
        {
            do {
                Category category = new Category();
                category.setId(cursor.getInt(cursor.getColumnIndex(Category.COLUMN_ID)));
                category.setName(cursor.getString(cursor.getColumnIndex(Category.COLUMN_NAME)));

                list.add(category);
            } while (cursor.moveToNext());
        }

        db.close();
        return list;
    }



    // entry
    public long insertEntry(SQLiteDatabase db,
                            String nameEng,
                            String nameTur,
                            Integer categoryId,
                            String description)
    {
        boolean closeConn = false;

        // get writable database as we want to write data
        if (db == null) {
            db = this.getWritableDatabase();
            closeConn = true;
        }

        ContentValues values = new ContentValues();
        values.put(Entry.COLUMN_NAMEENG, nameEng);
        values.put(Entry.COLUMN_NAMETUR, nameTur);
        values.put(Entry.COLUMN_CATEGORYID, categoryId);
        values.put(Entry.COLUMN_DESCRIPTION, description);

        // insert
        long recordId = db.insert(Entry.TABLE_NAME, null, values);

        if (closeConn) {
            db.close();
        }

        return recordId;
    }

    public Entry getEntry(int id) {
        Entry entry = null;

        // query
        String selectQuery = "SELECT  * FROM " + Entry.TABLE_NAME
                + " WHERE " + Entry.COLUMN_ID + " = " + id;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null
                && cursor.moveToFirst())
        {
            entry = new Entry();
            entry.setId(cursor.getInt(cursor.getColumnIndex(Entry.COLUMN_ID)));
            entry.setNameEng(cursor.getString(cursor.getColumnIndex(Entry.COLUMN_NAMEENG)));
            entry.setNameTur(cursor.getString(cursor.getColumnIndex(Entry.COLUMN_NAMETUR)));
            entry.setCategoryId(cursor.getInt(cursor.getColumnIndex(Entry.COLUMN_CATEGORYID)));
            entry.setDescription(cursor.getString(cursor.getColumnIndex(Entry.COLUMN_DESCRIPTION)));
        }

        db.close();
        return entry;
    }

    public ArrayList<Entry> getEntries() {
        ArrayList<Entry> list = new ArrayList<Entry>();

        // query
        String selectQuery = "SELECT  * FROM " + Entry.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst())
        {
            do {
                Entry entry = new Entry();
                entry.setId(cursor.getInt(cursor.getColumnIndex(Entry.COLUMN_ID)));
                entry.setNameEng(cursor.getString(cursor.getColumnIndex(Entry.COLUMN_NAMEENG)));
                entry.setNameTur(cursor.getString(cursor.getColumnIndex(Entry.COLUMN_NAMETUR)));
                entry.setCategoryId(cursor.getInt(cursor.getColumnIndex(Entry.COLUMN_CATEGORYID)));
                entry.setDescription(cursor.getString(cursor.getColumnIndex(Entry.COLUMN_DESCRIPTION)));

                list.add(entry);
            } while (cursor.moveToNext());
        }

        db.close();
        return list;
    }

    public ArrayList<Entry> getEntries(Integer categoryId, String name) {
        ArrayList<Entry> list = new ArrayList<Entry>();

        // query
        String selectQuery = "SELECT  * FROM " + Entry.TABLE_NAME;


        boolean firstCondition = true;

        if (categoryId != null) {
            firstCondition = false;

            selectQuery += " WHERE (" + Entry.COLUMN_CATEGORYID + " = " + categoryId.toString() + ")";
        }

        if (!TextUtils.isEmpty(name)) {
            if (firstCondition)
            {
                selectQuery += " WHERE";
            }
            else
            {
                selectQuery += " AND";
            }

            selectQuery += " (" + Entry.COLUMN_NAMEENG + " LIKE '%" + name + "%' OR "
                    + Entry.COLUMN_NAMETUR + " LIKE '%" + name + "%')";
        }


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst())
        {
            do {
                Entry entry = new Entry();
                entry.setId(cursor.getInt(cursor.getColumnIndex(Entry.COLUMN_ID)));
                entry.setNameEng(cursor.getString(cursor.getColumnIndex(Entry.COLUMN_NAMEENG)));
                entry.setNameTur(cursor.getString(cursor.getColumnIndex(Entry.COLUMN_NAMETUR)));
                entry.setCategoryId(cursor.getInt(cursor.getColumnIndex(Entry.COLUMN_CATEGORYID)));
                entry.setDescription(cursor.getString(cursor.getColumnIndex(Entry.COLUMN_DESCRIPTION)));

                list.add(entry);
            } while (cursor.moveToNext());
        }

        db.close();
        return list;
    }
}