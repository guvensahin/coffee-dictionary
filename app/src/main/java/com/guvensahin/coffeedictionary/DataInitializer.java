package com.guvensahin.coffeedictionary;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.text.TextUtils;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class DataInitializer {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;

    public DataInitializer(DatabaseHelper databaseHelper, SQLiteDatabase db)
    {
        this.databaseHelper = databaseHelper;
        this.db = db;
    }

    public void run()
    {
        createCategory();
        createEntry();
    }

    public void createCategory()
    {
        AssetManager assetManager = databaseHelper.getContext().getAssets();
        InputStream inputStream = null;

        try {
            inputStream = assetManager.open("data_category.csv");

            CSVReader reader = new CSVReaderBuilder(new InputStreamReader(inputStream))
                    .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                    .build();

            List<String[]> rows = reader.readAll();

            // delete first line for header
            rows.remove(0);

            for (String[] row : rows) {

                databaseHelper.insertCategory(db,
                        Integer.parseInt(row[0].trim()),
                        row[1].trim());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createEntry()
    {
        AssetManager assetManager = databaseHelper.getContext().getAssets();
        InputStream inputStream = null;

        try {
            inputStream = assetManager.open("data_entry.csv");

            CSVReader reader = new CSVReaderBuilder(new InputStreamReader(inputStream))
                    .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                    .build();

            List<String[]> rows = reader.readAll();

            // delete first line for header
            rows.remove(0);

            for (String[] row : rows) {

                databaseHelper.insertEntry(db,
                        row[0].trim(),
                        row[1].trim(),
                        Integer.parseInt(row[2].trim()),
                        row[3].trim());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
