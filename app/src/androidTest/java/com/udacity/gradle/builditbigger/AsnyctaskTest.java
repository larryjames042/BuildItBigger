import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.udacity.gradle.builditbigger.AsyncTaskResponse;
import com.udacity.gradle.builditbigger.EndpointsAsyncTask;
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
        EndpointsAsyncTask task = new EndpointsAsyncTask(new AsyncTaskResponse() {
            @Override
            public String processFinish(String response) {
                return response;
            }

            @Override
            public void beforeProcess() {

            }
        });
        String result =  task.execute().get();
       for(String i : jokers.jokeList()){
           toCompare = i;
           if(toCompare.equals(result)){
               break;
           }
       }
       assertEquals(result, toCompare);

    }



}
