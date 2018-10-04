package com.guvensahin.coffeedictionary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ListView lv;
    EntryAdapter adapter;
    ArrayList<Entry> entries = new ArrayList<Entry>();
    ArrayList<String> categories = new ArrayList<String>();

    NavigationView navigationView;

    String filterName = "";
    String filterCategory = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // ilk menüyü otomatik seçili hale getir
        navigationView.getMenu().getItem(0).setChecked(true);

        readEntriesFromFile();
        initListView();
        updateNavMenuForFilters();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            filterCategory = "";
            setTitle(R.string.app_name);
            refreshListForFilter();
        } else if (id == R.id.nav_about) {
            Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
            startActivity(intent);
        }
        // kategori filtreleri
        else
        {
            filterCategory = item.getTitle().toString();
            setTitle(filterCategory);
            refreshListForFilter();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    public boolean onCreateOptionsMenu( Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_search, menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.menu_search_view);
        SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String queryText) {
                filterName = queryText;
                refreshListForFilter();
                return false;
            }
        });
        return true;
    }



    // read list data
    private void readEntriesFromFile()
    {
        try {
            CSVReader reader = new CSVReaderBuilder(new InputStreamReader(getAssets().open("data.csv")))
                    .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                    .build();

            List<String[]> rows = reader.readAll();

            // delete first line for header
            rows.remove(0);

            for (String[] row : rows) {

                Entry entry         = new Entry();
                entry.setNameEng(row[0].trim());
                entry.setNameTur(row[1].trim());
                entry.category      = row[2].trim();
                entry.description   = row[3].trim();

                entries.add(entry);

                // kategoriler kaydedilir
                if (!TextUtils.isEmpty(entry.category)
                    && !categories.contains(entry.category))
                {
                    categories.add(entry.category);
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initListView()
    {
        lv = (ListView) findViewById(R.id.list_view_entry);
        adapter = new EntryAdapter(this, R.layout.list_view_entry, entries);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                      @Override
                                      public void onItemClick (AdapterView<?> adapter, View view, int position, long arg) {
                                          Entry game = entries.get(position);

                                          Intent intent = new Intent(getApplicationContext(), EntryDetailActivity.class);
                                          intent.putExtra("entryModel", game);
                                          startActivity(intent);
                                      }
                                  }
        );
    }

    private void updateNavMenuForFilters()
    {
        Menu menu = navigationView.getMenu();
        SubMenu subMenu = menu.findItem(R.id.nav_subtitle_cat).getSubMenu();
        MenuItem menuItem;

        int counter = 0;

        // todo türkçe karakter desteklemiyor
        Collections.sort(categories);

        for (String cat : categories)
        {
            counter++;
            menuItem = subMenu.add(Menu.NONE, counter, Menu.NONE, cat);
            menuItem.setIcon(R.drawable.ic_folder_open);
            menuItem.setCheckable(true);
        }
    }

    private void refreshListForFilter()
    {
        adapter.filter(filterName, filterCategory);
    }
}