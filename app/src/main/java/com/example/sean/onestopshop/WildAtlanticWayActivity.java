package com.example.sean.onestopshop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class WildAtlanticWayActivity extends AppCompatActivity {

    private WebView wildWayWebView;
    private final String TAG = WildAtlanticWayActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wild_atlantic_way);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.wild_atlantic_activity_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        wildWayWebView = (WebView) findViewById(R.id.wildAtlanticWayWeb);
        wildWayWebView.loadUrl("http://www.wildatlanticway.com/home");
        wildWayWebView.setWebViewClient(new WebViewClient());
        wildWayWebView.canGoBack();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent mainIntent = new Intent(WildAtlanticWayActivity.this, MainActivity.class);
        startActivity(mainIntent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        switch (id){
//            case R.id.action_english:
//                Toast.makeText(this, "English Clicked", Toast.LENGTH_SHORT).show();
//                break;
//
//            case R.id.action_irish:
//                Toast.makeText(this, "Irish Clicked", Toast.LENGTH_SHORT).show();
//                break;
//        }
//
//        //return true;
//        return super.onOptionsItemSelected(item);
//    }
}
