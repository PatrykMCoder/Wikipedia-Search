package com.example.patry.wikipediasearch;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.patry.wikipediasearch.Enums.LanguageEnum;

public class MainActivity extends AppCompatActivity {

    private EditText editTextSearch;
    private TextView textViewTitle;

    private LanguageEnum languageEnum;

    private boolean normalWikipedia;

    public static String openUrl;
    public static final String TAG = "Main_Activity";
    public static String worldSearch;

    //language for random article
    String openUrlRandom[] = {
            "https://pl.wikipedia.org/wiki/Specjalna:Losowa_strona",
            "https://en.wikipedia.org/wiki/Special:Random",
            "https://ko.wikipedia.org/wiki/%ED%8A%B9%EC%88%98:%EC%9E%84%EC%9D%98%EB%AC%B8%EC%84%9C",
            "https://de.wikipedia.org/wiki/Spezial:Zuf%C3%A4llige_Seite",
            "https://fr.wikipedia.org/wiki/Sp%C3%A9cial:Page_au_hasard",
            "https://es.wikipedia.org/wiki/Especial:Aleatoria"
    };

    public static String url[] = {"https://pl.wikipedia.org/wiki/"};

    public NetworkInfo networkInfo;
    public ConnectivityManager connectivityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        languageEnum = LanguageEnum.NONE;

        CheckBox checkBoxPolish = findViewById(R.id.checkbox_polish);
        CheckBox checkBoxEnglish = findViewById(R.id.checkbox_english);
        CheckBox checkboxKorean = findViewById(R.id.checkbox_korean);
        CheckBox checkBoxGermany = findViewById(R.id.checkbox_germany);
        CheckBox checkBoxFrench = findViewById(R.id.checkbox_french);
        CheckBox checkBoxSpanish = findViewById(R.id.checkbox_spanish);

        editTextSearch = findViewById(R.id.edit_text_search);
        textViewTitle = findViewById(R.id.text_view_title);

        Button buttonSearch = findViewById(R.id.button_search);
        Button buttonRandom = findViewById(R.id.button_random_article);

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkInternetStatus();
            }
        });

        buttonRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                randomArticle();
            }
        });
    }

    public void checkInternetStatus(){
        if(isOnline()){
            lookWhatWiki();
        }else{
            Toast.makeText(this, "Please connect with Internet", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isOnline(){
         connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
             assert connectivityManager != null;
             networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }

    private void lookWhatWiki() {
        if(normalWikipedia){
            searchInWikipedia();
        }
    }

    private void randomArticle(){
        if(normalWikipedia) {
            if (languageEnum == LanguageEnum.POLISH) {
                openUrl = openUrlRandom[0];
                openWeb();
            } else if (languageEnum == LanguageEnum.ENGLISH) {
                openUrl = openUrlRandom[1];
                openWeb();
            }else if(languageEnum == LanguageEnum.KOREAN){
                openUrl = openUrlRandom[2];
                openWeb();
            }else if(languageEnum == LanguageEnum.GERMANY) {
                openUrl = openUrlRandom[3];
                openWeb();
            }else if(languageEnum == LanguageEnum.FRENCH){
                openUrl = openUrlRandom[4];
                openWeb();
            }else if(languageEnum == LanguageEnum.SPANISH) {
                openUrl = openUrlRandom[5];
                openWeb();
            }else{
                Toast.makeText(this, "Please select language", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void searchInWikipedia() {
        switch (languageEnum){
            case POLISH:
                worldSearch = editTextSearch.getText().toString();
                if(!worldSearch.isEmpty()){
                    openUrl = url[0] + worldSearch;
                    openWeb();
                }
                break;
        }
    }

    public void openWeb(){
        startActivity(new Intent(MainActivity.this, WebView.class));
    }

    public void checked(View v){
        boolean checked = ((CheckBox) v).isChecked();
        switch (v.getId()){
            case R.id.checkbox_polish:
                if(checked) {
                   languageEnum = LanguageEnum.POLISH;
                }
                break;
            case R.id.checkbox_english:
                if(checked) {
                    languageEnum = LanguageEnum.ENGLISH;
                }
                break;
            case R.id.checkbox_korean:
                if(checked){
                    languageEnum = LanguageEnum.KOREAN;
                }
                break;
            case R.id.checkbox_germany:
                if(checked){
                    languageEnum = LanguageEnum.GERMANY;
                }
                break;
            case R.id.checkbox_french:
                if(checked) {
                    languageEnum = LanguageEnum.FRENCH;
                }
                break;
            case R.id.checkbox_spanish:
                if(checked) {
                    languageEnum = LanguageEnum.SPANISH;
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.item_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.wikipedia_item: {
                textViewTitle.setText(R.string.title);
                normalWikipedia = true;
                return true;
            }
            case R.id.about_item: {
                try {
                    PackageInfo info = this.getPackageManager().getPackageInfo(getPackageName(), 0);
                    String version = info.versionName;
                    Toast.makeText(this, "Author: Patryk Marciszewski, version: " + version, Toast.LENGTH_LONG).show();
                    return true;
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                    return true;
                }
            }
            case R.id.web_test_item: {
                Toast.makeText(this, "This is test web", Toast.LENGTH_SHORT).show();

                openUrl = "https://www.google.com";
                openWeb();

                return true;
            }
            case R.id.send_feedback_item: {
                startActivity(new Intent(MainActivity.this, SendFeedbackActivity.class));

                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
