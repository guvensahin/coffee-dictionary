package com.guvensahin.coffeedictionary;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
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
                .setDescription(getString(R.string.app_name))
                .addItem(new Element().setTitle("Geliştirme ve Tasarım: Güven Şahin"))
                .addItem(new Element().setTitle("İçerik ve Tasarım: Uğur Yazıcı"))
                .addItem(new Element().setTitle("Version: " + BuildConfig.VERSION_NAME))
                .addWebsite("http://guvensahin.com/privacy/coffeedictionary_privacy.html", getString(R.string.privacy_policy))
                .addGroup("İletişime geç")
                .addEmail("kahvesozlugudestek@gmail.com", "Email")
                .addWebsite("http://guvensahin.com")
                .addPlayStore(this.getPackageName())
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
