package com.guvensahin.coffeedictionary;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.guvensahin.coffeedictionary.Utils.AppUtil;
import com.guvensahin.coffeedictionary.Utils.DatabaseHelper;

import com.guvensahin.coffeedictionary.Models.Entry;

public class EntryDetailActivity extends AppCompatActivity {

    public Entry entry;
    private AdView adView;


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
        DatabaseHelper db = new DatabaseHelper(this);

        // intent
        Intent intent = getIntent();
        entry = (Entry)intent.getSerializableExtra("entryModel");

        nameEngView.setText(entry.getNameEng());
        nameTurView.setText(entry.getNameEng().equals(entry.getNameTur()) ? "─" : entry.getNameTur());
        catView.setText(db.getCategory(entry.getCategoryId()).getName());
        descView.setText(entry.getDescription());

        // init search on list
        this.initListView();

        // ads
        initAds();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }

    private void initAds()
    {
        adView = (AdView) findViewById(R.id.entry_detail_ad_view);
        adView.loadAd(new AdRequest.Builder().build());
    }

    private void initListView()
    {
        ListView listView = (ListView) findViewById(R.id.list_view);
        final Context context = this;

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.list_item_search_on, android.R.id.text1, getResources().getStringArray(R.array.searchOnLabels));

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                AppUtil.clickSearchOn(context, entry, pos);
            }
        });
    }
}
