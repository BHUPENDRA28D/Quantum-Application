package com.example.quantumclasses;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Study_Material extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_material);

        webView = findViewById(R.id.idWV_SM);

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://bhupendra28d.github.io/quantumclassesweb/studymaterials.html");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);


        bottomNavigationView =findViewById(R.id.SM_navigation);
        bottomNavigationView.setSelectedItemId(R.id.study_material);
// use of setOnItemSelectedListener methode
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.home:startActivity(new Intent(getApplication(),
                            Home_Activity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.course:startActivity(new Intent(getApplication(),
                            Course.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.faculty:startActivity(new Intent(getApplication(),
                            Teacher.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.study_material:startActivity(new Intent(getApplication(),
                            Study_Material.class));
                        overridePendingTransition(0,0);
                        return true;


                    case R.id.profile:startActivity(new Intent(getApplication(),
                            UserProfile.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

//        bottomNavigationView.setSelectedItemId(R.id.course);
//        bottomNavigationView.setSelectedItemId(R.id.study_material);
//        bottomNavigationView.setSelectedItemId(R.id.profile);
    }

    private class MyWebClient  extends WebViewClient{

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }
    }

    @Override
    public void onBackPressed() {
        if(webView.isFocused() && webView.canGoBack()){
            webView.goBack();
        }else {
            super.onBackPressed();}
    }
}