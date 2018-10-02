import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.udacity.gradle.builditbigger.MainActivity;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.Pair;

import junit.framework.TestCase;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import mirror.co.larry.androidjokerlib.JokeActivity;
import mirror.co.larry.javajokes.Jokers;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
@LargeTest
public class AsnyctaskTest {
    @Test
    public void asyntaskTest_returnValue_isCorrect() throws ExecutionException, InterruptedException {
       Jokers jokers = new Jokers();
       String toCompare = "";
       assertEquals(jokers.jokeList().size(), 6);
       String result = new EndpointsAsyncTask().execute().get();
       for(String i : jokers.jokeList()){
           toCompare = i;
           if(toCompare.equals(result)){
               break;
           }
       }
       assertEquals(result, toCompare);
    }

    class EndpointsAsyncTask extends AsyncTask<Void, Void, String> {
        private MyApi myApiService = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(Void... params) {
            if(myApiService == null) {  // Only do this once
                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://build-it-bigger-217807.appspot.com/_ah/api/");
                // end options for devappserver

                myApiService = builder.build();
            }

            try {
                return myApiService.sayHi().execute().getData();
            } catch (IOException e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {

        }
    }
}
