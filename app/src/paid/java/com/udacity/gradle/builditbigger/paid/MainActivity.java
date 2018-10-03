package com.udacity.gradle.builditbigger.paid;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.udacity.gradle.builditbigger.AsyncTaskResponse;
import com.udacity.gradle.builditbigger.EndpointsAsyncTask;
import com.udacity.gradle.builditbigger.NetUtil;
import com.udacity.gradle.builditbigger.R;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

import mirror.co.larry.androidjokerlib.JokeActivity;

public class MainActivity extends AppCompatActivity {
    static String jokes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void tellJoke(View view) {
        if(NetUtil.isConnected(this)){
            loadAsynctask();
        }else{
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

    }
    private void loadAsynctask(){
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        EndpointsAsyncTask endpointsAsyncTask = new EndpointsAsyncTask(new AsyncTaskResponse() {
            @Override
            public String processFinish(String response) {
                progressDialog.dismiss();
                String jokes = response;
                Intent launchJokerActivity = new Intent(MainActivity.this, JokeActivity.class);
                launchJokerActivity.putExtra("joke_extra", jokes);
                startActivity(launchJokerActivity);
                return jokes;
            }

            @Override
            public void beforeProcess() {
                progressDialog.setMessage("Fetching Joke");
                progressDialog.show();
            }
        });
        endpointsAsyncTask.execute();
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
}
