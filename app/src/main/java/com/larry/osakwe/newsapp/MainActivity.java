package com.larry.osakwe.newsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String url =
                "https://www.giantbomb.com/api/reviews/" +
                        "?api_key=837e532ede8d717222c1247baad46cb354fb9686&format=json&limit=5";

    }
}
