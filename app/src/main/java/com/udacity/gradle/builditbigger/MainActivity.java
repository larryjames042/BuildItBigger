package com.udacity.gradle.builditbigger;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

import mirror.co.larry.androidjokerlib.JokeActivity;
import mirror.co.larry.javajokes.Jokers;


public class MainActivity extends AppCompatActivity {
    String jokes;
    PublisherInterstitialAd  mPublisherInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(BuildConfig.FLAVOR.equals("free")){
            mPublisherInterstitialAd = new PublisherInterstitialAd(this);
            mPublisherInterstitialAd.setAdUnitId("/6499/example/interstitial");
            mPublisherInterstitialAd.loadAd(new PublisherAdRequest.Builder().build());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {
        if(NetUtil.isConnected(this)){
            if(BuildConfig.FLAVOR.equals("free")){
                mPublisherInterstitialAd.show();
                mPublisherInterstitialAd.setAdListener(new AdListener(){
                    @Override
                    public void onAdClosed() {
                        super.onAdClosed();
                        new EndpointsAsyncTask().execute(new Pair<Context, String>(getApplicationContext(), "there!"));
                    }

                    @Override
                    public void onAdFailedToLoad(int i) {
                        super.onAdFailedToLoad(i);
                        new EndpointsAsyncTask().execute(new Pair<Context, String>(getApplicationContext(), "there!"));
                    }
                });
            }else{
                new EndpointsAsyncTask().execute(new Pair<Context, String>(this, "there!"));
            }

        }else{
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

    }

    class EndpointsAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {
        private  MyApi myApiService = null;
        private Context context;
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Fetching Joke");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Pair<Context, String>... params) {
            if(myApiService == null) {  // Only do this once
                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://build-it-bigger-217807.appspot.com/_ah/api/");
                // end options for devappserver

                myApiService = builder.build();
            }

            context = params[0].first;
            String name = params[0].second;
            try {
                return myApiService.sayHi().execute().getData();
            } catch (IOException e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            jokes = result;
            Intent launchJokerActivity = new Intent(MainActivity.this, JokeActivity.class);
            launchJokerActivity.putExtra("joke_extra", jokes);
            startActivity(launchJokerActivity);
        }
    }


}




