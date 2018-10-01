package com.guvensahin.coffeedictionary;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // title
        setTitle(R.string.title_activity_about);

        // back button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_clear_white);

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.img_about)
                .setDescription("Kahve Sözlüğü")
                .addItem(new Element().setTitle("Geliştirme ve Tasarım: Güven Şahin"))
                .addItem(new Element().setTitle("İçerik ve Tasarım: Uğur Yazıcı"))
                .addItem(new Element().setTitle("Version: 1.0"))
                .addGroup("İletişime geç")
                .addEmail("guvensahin@outlook.com", "Email")
                .addWebsite("http://guvensahin.com")
                .addPlayStore("com.guvensahin.coffeedictionary")
                .addGitHub("guvensahin")
                .create();

        setContentView(aboutPage);
}


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
