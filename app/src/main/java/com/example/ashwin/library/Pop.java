package com.example.ashwin.library;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Pop extends Activity {
    private TextView d;
    private ProgressDialog progressDialog;
    public static final int progress_bar_type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_up);

        d = (TextView) findViewById(R.id.browser_opener);
        d.setText(getIntent().getStringExtra("URL"));
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permission_check();
            }
        });

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int)(width * 0.9), (int)(height * 0.3));


    }

    private void permission_check() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
                return;
            }
        }

        start_download();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 100 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            start_download();
        }
        else
        {
            Toast.makeText(this, "Accept the permission to download files first", Toast.LENGTH_LONG).show();
        }
    }

    private void start_download() {
        new DownloadFileFromURL().execute(getIntent().getStringExtra("URL"));
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case progress_bar_type:
                progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Downloading File...");
                progressDialog.setIndeterminate(false);
                progressDialog.setMax(100);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setCancelable(true);
                progressDialog.show();
                return progressDialog;

            default:
                return null;
        }
    }

    class DownloadFileFromURL extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(progress_bar_type);
        }

        @Override
        protected String doInBackground(String... f_url) {
            int count;

            try {
                URL url = new URL(f_url[0]);
                URLConnection urlConnection = url.openConnection();
                urlConnection.connect();

                int lengthOfFile = urlConnection.getContentLength();

                InputStream inputStream = new BufferedInputStream(url.openStream(), 8192);

                String storageDirectory = Environment.getExternalStorageDirectory().getAbsolutePath();
                String fileName = "/";
                fileName += getIntent().getStringExtra("fileName");
                String fileType = getIntent().getStringExtra("fileType");

                fileName += "." + fileType;

                File d_file = new File(storageDirectory + "/Download" + fileName);

                OutputStream outputStream = new FileOutputStream(d_file);

                byte data[] = new byte[1024];
                long total = 0;

                while((count = inputStream.read(data)) != -1) {
                    total += count;

                    publishProgress("" + (int)((total * 100) / lengthOfFile));

                    outputStream.write(data, 0, count);
                }

                outputStream.flush();
                outputStream.close();
                inputStream.close();

                Toast.makeText(getApplicationContext(), "Download Completed. Check your Download Manager", Toast.LENGTH_LONG).show();
            }
            catch (Exception e) {
                Log.d("Error: ", "e.printStackTrace():\n" + e.getMessage());
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(String... progress) {
            progressDialog.setProgress(Integer.parseInt(progress[0]));
        }

        @Override
        protected void onPostExecute(String file_url) {
            dismissDialog(progress_bar_type);
        }
    }
}
