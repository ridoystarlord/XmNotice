package com.ridoy.xmnotice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class PDFViewActivity extends AppCompatActivity {

    private WebView webView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_d_f_view);

        Toolbar unit_toolbar=findViewById(R.id.pdfview_toolbarid);
        setSupportActionBar(unit_toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Toast.makeText(this, getIntent().getStringExtra("unit"), Toast.LENGTH_LONG).show();

        webView=findViewById(R.id.webviewid);
        progressBar=findViewById(R.id.progressbarid);

        progressBar.setVisibility(View.VISIBLE);
        //String url="https://students.iusb.edu/academic-success-programs/academic-centers-for-excellence/docs/Basic%20Math%20Review%20Card.pdf";
        String url=getIntent().getStringExtra("uniturl");
        String finalurl="https://drive.google.com/viewerng/viewer?embedded=true&url="+url;
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                getSupportActionBar().setTitle("Loading...");
                if (newProgress==100){
                    progressBar.setVisibility(View.GONE);
                    getSupportActionBar().setTitle(getIntent().getStringExtra("unit")+" Unit Notice");
                }
            }
        });
        webView.loadUrl(finalurl);

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()== android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}