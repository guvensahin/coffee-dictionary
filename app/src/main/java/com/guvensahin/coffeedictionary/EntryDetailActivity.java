package com.guvensahin.coffeedictionary;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class EntryDetailActivity extends AppCompatActivity {

    public Entry entry;

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
        TextView nameView = (TextView) findViewById(R.id.name_view);
        TextView descView = (TextView) findViewById(R.id.desc_view);
        TextView catView = (TextView) findViewById(R.id.cat_view);

        // intent
        Intent intent = getIntent();
        entry = (Entry)intent.getSerializableExtra("entryModel");

        nameView.setText(entry.name);
        descView.setText(entry.description);
        catView.setText(entry.category);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
