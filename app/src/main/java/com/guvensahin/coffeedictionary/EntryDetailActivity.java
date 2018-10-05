package com.guvensahin.coffeedictionary;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class EntryDetailActivity extends AppCompatActivity {

    public Entry entry;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_detail);

        // title
        setTitle("");

        // back button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        // init
        TextView nameEngView = (TextView) findViewById(R.id.name_eng_view);
        TextView nameTurView = (TextView) findViewById(R.id.name_tur_view);
        TextView catView = (TextView) findViewById(R.id.cat_view);
        TextView descView = (TextView) findViewById(R.id.desc_view);

        // db init
        db = new DatabaseHelper(this);

        // intent
        Intent intent = getIntent();
        entry = (Entry)intent.getSerializableExtra("entryModel");

        nameEngView.setText(entry.getNameEng());
        nameTurView.setText(entry.getNameTur());
        catView.setText(db.getCategory(entry.getCategoryId()).getName());
        descView.setText(entry.getDescription());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
