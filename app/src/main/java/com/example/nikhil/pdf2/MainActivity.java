package com.example.nikhil.pdf2;

import android.content.Context;
import android.content.Intent;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.WeakHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button btnpdf;
    WebView mWebView;

    private static final String REGEX = "https";

    int start1;
    int end1;
    String mediumurl;

    //todo 3. Add regular expression to get the url from medium post.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.passedURL);

        btnpdf = findViewById(R.id.pdf);

        //todo 1. First Add intent filter with "intent.ACTION.SEND"
        //Please see this is required to be added so your activity is shown when share event happen.


        //todo 2. Get the URL from intent that is passed from other app

        //NOTE: here we are going to handle intent passed.

        //Here we are storing the intent passed from other app to the intent object.
        Intent intent = getIntent();

        String action = intent.getAction();

        //Here we are storing the type of data passed, ex-image, string
        String type = intent.getType();

        final String text1 = intent.getStringExtra(Intent.EXTRA_TEXT);


        //I will send url after getting original url "medium"

      //  Pattern p = Pattern.compile(REGEX);
       // Matcher m = p.matcher(text1);   // get a matcher object
       // int count = 0;

/*
        //System.out.println("Length is " + text1.length());

        end1 = text1.length();

        while (m.find()) {

            System.out.println("inside while");
            count++;
            System.out.println("Match number " + count);
            System.out.println("start(): " + m.start());

            start1 = m.start();

            System.out.println("end(): " + m.end());
        }

        mediumurl = text1.substring(start1, end1);

        */

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent); // Handle text being sent
            } else {
                Toast.makeText(getApplication(), "Something unknown passed", Toast.LENGTH_LONG).show(); // Handle single image being sent
            }
        }

        btnpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printPDF(mediumurl);
            }
        });
    }

    private void printPDF(String text2) {

        doWebViewPrint(text2);


    }

    private void doWebViewPrint(String uop) {
        // Create a WebView object specifically for printing
        WebView webView = new WebView(MainActivity.this);
        webView.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // Log.i(TAG, "page finished loading " + url);
                Toast.makeText(getApplicationContext(), url, Toast.LENGTH_LONG).show();
                createWebPrintJob(view);
                mWebView = null;
            }
        });


        // Print an existing web page (remember to request INTERNET permission!):

        webView.loadUrl(uop);

        // Keep a reference to WebView object until you pass the PrintDocumentAdapter
        // to the PrintManager
        mWebView = webView;
    }


    private void createWebPrintJob(WebView webView) {

        // Get a PrintManager instance
        PrintManager printManager = (PrintManager) MainActivity.this
                .getSystemService(Context.PRINT_SERVICE);

        // Get a print adapter instance
        PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter();

        // Create a print job with name and adapter instance
        String jobName = getString(R.string.app_name) + " Document";
        PrintJob printJob = printManager.print(jobName, printAdapter,
                new PrintAttributes.Builder().build());

        // Save the job object for later status checking
        // mPrintJobs.add(printJob);
    }

    private void handleSendText(Intent intent) {

        String text = intent.getStringExtra(Intent.EXTRA_TEXT);

        if (text != null) {
            //textView.setText(text);

            Pattern p = Pattern.compile(REGEX);
            Matcher m = p.matcher(text);   // get a matcher object
            int count = 0;


            //System.out.println("Length is " + text1.length());

            end1 = text.length();

            while (m.find()) {

                System.out.println("inside while");
                count++;
                System.out.println("Match number " + count);
                System.out.println("start(): " + m.start());

                start1 = m.start();

                System.out.println("end(): " + m.end());
            }

            mediumurl = text.substring(start1, end1);
            Toast.makeText(getApplication(), "Updating tv from other class", Toast.LENGTH_LONG).show();
        } else {
            textView.setText("Shit this is not working");
        }
    }

}
