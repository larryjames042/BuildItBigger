package mirror.co.larry.androidjokerlib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeActivity extends AppCompatActivity {
    TextView mJoke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        mJoke = findViewById(R.id.tv_joke);
        if(getIntent().hasExtra("joke_extra")){
            String joke = getIntent().getStringExtra("joke_extra");
            mJoke.setText(joke);
        }


    }
}
