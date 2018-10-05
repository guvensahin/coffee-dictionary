package com.guvensahin.coffeedictionary;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
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

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ListView lv;
    NavigationView navigationView;

    private AdView adView;
    DatabaseHelper db;
    EntryAdapter adapter;
    ArrayList<Entry> entries = new ArrayList<Entry>();
    ArrayList<Category> categories = new ArrayList<Category>();

    String filterName = null;
    Integer filterCategoryId = null;

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

        // db init
        db = new DatabaseHelper(this);

        initListView();
        updateNavMenu();

        // ads
        MobileAds.initialize(this, AppHelper.getProperty(this, "ADMOB_ID"));

        adView = (AdView) findViewById(R.id.ad_view);

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                // Check the LogCat to get your test device ID
                //.addTestDevice("C04B1BFFB0774708339BC273F8A43708")
                .build();

        /*adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
            }

            @Override
            public void onAdClosed() {
                Toast.makeText(getApplicationContext(), "Ad is closed!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Toast.makeText(getApplicationContext(), "Ad failed to load! error code: " + errorCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLeftApplication() {
                Toast.makeText(getApplicationContext(), "Ad left application!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }
        });*/

        adView.loadAd(adRequest);
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
            filterCategoryId = null;
            setTitle(R.string.app_name);
            refreshListForFilter();
        } else if (id == R.id.nav_about) {
            Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
            startActivity(intent);
        }
        // kategori filtreleri
        else
        {
            filterCategoryId = id;
            setTitle(db.getCategory(id).getName());
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


    private void initListView()
    {
        entries = db.getEntries();

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

    private void updateNavMenu()
    {
        categories = db.getCategories();

        Menu menu = navigationView.getMenu();
        SubMenu subMenu = menu.findItem(R.id.nav_subtitle_cat).getSubMenu();
        MenuItem menuItem;

        for (Category category : categories)
        {
            menuItem = subMenu.add(Menu.NONE, category.getId(), Menu.NONE, category.getName());
            menuItem.setIcon(R.drawable.ic_folder_open);
            menuItem.setCheckable(true);
        }
    }

    private void refreshListForFilter()
    {
        entries.clear();
        entries.addAll(db.getEntries(filterCategoryId, filterName));
        adapter.notifyDataSetChanged();
    }
}