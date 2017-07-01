package com.larry.osakwe.newsapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String url =
                "https://www.giantbomb.com/api/reviews/" +
                        "?api_key=837e532ede8d717222c1247baad46cb354fb9686&format=json&limit=5";

        String imageUrl = "https://www.giantbomb.com/api/game/3030-" + "gameId" +
                "/?api_key=837e532ede8d717222c1247baad46cb354fb9686&format=json";

    }
}
