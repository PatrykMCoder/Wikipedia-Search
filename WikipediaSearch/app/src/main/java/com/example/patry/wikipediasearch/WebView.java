package com.example.patry.wikipediasearch;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebViewClient;
import android.webkit.WebSettings;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import static com.example.patry.wikipediasearch.MainActivity.openUrl;

public class WebView extends AppCompatActivity {

    //variables for webView
    private android.webkit.WebView webViewPage;
    private Toolbar toolbarLinkView;
    private TextView textViewLinkView;


    public String url;

    //tags
     public static final String TAG = "WEB_TEST";

   @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_web_view);

        webViewPage = findViewById(R.id.web_view_page);
        toolbarLinkView = findViewById(R.id.toolbar_link_view);
        textViewLinkView = findViewById(R.id.text_view_link_view);

        checkInternetConnection();
        
    }

    //my method

    public boolean isOnline(){
        //variable connective status
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        assert connectivityManager != null; //this is test
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();


        if(networkInfo != null && networkInfo.isConnected()){
            return true;
        }else {
            return false;
        }
    }
    
    public void checkInternetConnection(){
        if(isOnline()){
            Toast.makeText(this, "Internet available ", Toast.LENGTH_SHORT).show();
            loadSettingsWebView();
        }else {
            Toast.makeText(this, "Internet not available", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadSettingsWebView(){
        webViewPage.setWebViewClient(new WebViewClient());

        webViewPage.setScrollbarFadingEnabled(false);

        webViewPage.loadUrl(openUrl);

        WebSettings webSettings = webViewPage.getSettings();
        webSettings.getJavaScriptEnabled();
    }

    private void copyToClipboard() {
        String copy = webViewPage.getOriginalUrl();

        ClipboardManager clipboardManager = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        if(clipboardManager!=null){
            try {
                ClipData clipData = ClipData.newPlainText(copy, copy);
                clipboardManager.setPrimaryClip(clipData);
            }catch (NullPointerException npe){
                npe.printStackTrace();
            }
        }
   }

//override method

    @Override
    public void onBackPressed() {
        if(webViewPage.canGoBack())
            webViewPage.goBack();
        else
            Toast.makeText(this, "Page can't go back", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.web_item_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.close_web_item: {
                Intent intent = new Intent(WebView.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                return true;
            }
            case R.id.back_web_item: {
                if(webViewPage.canGoBack()) {
                    webViewPage.goBack();
                    return true;
                }
                else {
                    Toast.makeText(this, "Page can't go back", Toast.LENGTH_SHORT).show();
                    return true;
                }
            }
            case R.id.forward_web_item: {
                if(webViewPage.canGoForward()) {
                    webViewPage.goForward();
                    return true;
                }
                else {
                    Toast.makeText(this, "Page can't go forward", Toast.LENGTH_SHORT).show();
                    return true;
                }
            }
            case R.id.reload_web_item: {
                webViewPage.reload();
                return true;
            }
            case R.id.go_page_up_web_item:{
                webViewPage.pageUp(true);
                return true;
            }
            case R.id.get_link_web_item:{
                copyToClipboard();
                return true;
            }
            case R.id.share_page_web_item:{
                //add share context
                ShareActionProvider shareActionProvider = (ShareActionProvider) item.getActionProvider();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }


}
