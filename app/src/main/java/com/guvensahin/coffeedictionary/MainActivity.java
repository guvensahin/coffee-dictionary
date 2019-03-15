package com.guvensahin.coffeedictionary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ListView lv;
    private NavigationView navigationView;
    private SearchView searchView;
    private AdView adView;

    private DatabaseHelper db;
    private EntryAdapter adapter;
    private ArrayList<Entry> entries = new ArrayList<Entry>();
    private ArrayList<Category> categories = new ArrayList<Category>();

    private Integer filterCategoryId = null;
    private String filterName = null;
    private Boolean refreshFilterName = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // db init
        db = new DatabaseHelper(this);

        // navigation drawer
        initNavMenu();

        // content
        initListView();

        // ads
        initAds();
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
        }
        else if (filterCategoryId != null) {
            // anasayfaya geç
            this.onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_home));
        }
        else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        // onNavigationItemSelected method'u activity içinden manuel tetiklendiği için bu satır eklendi.
        item.setChecked(true);

        if (id == R.id.nav_home) {
            filterCategoryId = null;
            clearFilterName();
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
            clearFilterName();
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
        searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                filterName = query;

                if (refreshFilterName) {
                    Log.d("güven", "onQueryTextChange");
                    refreshListForFilter();
                }
                return false;
            }
        });
        return true;
    }



    private void initNavMenu()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        {
            // drawer menu açıldığında, klavye açık ise kapatılır
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager != null
                    && getCurrentFocus() != null
                    && getCurrentFocus().getWindowToken() != null)
                {
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);

                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager != null
                    && getCurrentFocus() != null
                    && getCurrentFocus().getWindowToken() != null)
                {
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        // anasayfa menüsünü seçili hale getir
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);

        // kategori filtreleri yüklenir
        createNavMenuCategory();
    }

    private void createNavMenuCategory()
    {
        categories = db.getCategories();

        Menu menu = navigationView.getMenu();
        SubMenu subMenu = menu.findItem(R.id.nav_subtitle_cat).getSubMenu();
        MenuItem menuItem;

        for (Category category : categories)
        {
            menuItem = subMenu.add(Menu.NONE, category.getId(), Menu.NONE, category.getName());
            menuItem.setIcon(R.drawable.ic_nav_cat);
            menuItem.setCheckable(true);
        }
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

    private void initAds()
    {
        adView = (AdView) findViewById(R.id.ad_view);

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                //.addTestDevice("FDA3E6132CC71524BCE0F7BFA8A013FA") // asus
                .build();

        adView.loadAd(adRequest);
    }



    private void refreshListForFilter()
    {
        Log.d("güven", "refreshListForFilter");
        entries.clear();
        entries.addAll(db.getEntries(filterCategoryId, filterName));
        adapter.notifyDataSetChanged();
    }

    private void clearFilterName()
    {
        if (!TextUtils.isEmpty(filterName)) {

            // bu işlemden sonra zaten kategoride sıfırlanıp refresh edilecek.
            // 2 defa sql'e gitmemesi için listener içindeki yenileme kodu parametre ile devre dışı bırakılır.
            refreshFilterName = false;

            searchView.setQuery("", false);
            searchView.clearFocus();
            searchView.setIconified(true);

            refreshFilterName = true;
        }
    }
}