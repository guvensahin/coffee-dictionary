package com.guvensahin.coffeedictionary;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // title
        setTitle(R.string.all_about);

        // back button
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.about_header)
                .setDescription(getString(R.string.app_name))
                .addItem(new Element().setTitle(getString(R.string.about_developed_by)))
                .addItem(new Element().setTitle(getString(R.string.about_developed_by2)))
                .addItem(new Element().setTitle(getString(R.string.about_version) + " " + BuildConfig.VERSION_NAME))
                .addWebsite("http://guvensahin.com/privacy/coffeedictionary_privacy.html", getString(R.string.about_privacy))
                .addGroup(getString(R.string.about_contact))
                .addEmail("kahvesozlugudestek@gmail.com", getString(R.string.about_email))
                .addWebsite("http://guvensahin.com")
                .addPlayStore(this.getPackageName())
                .addGitHub("guvensahin")
                .create();

        FrameLayout container = (FrameLayout) findViewById(R.id.about_container);
        container.addView(aboutPage);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
