package com.larry.osakwe.newsapp;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Review>> {

    public static final String LOG_TAG = MainActivity.class.getName();
    private static final int REVIEW_LOADER_ID = 1;
    private static final String REVIEW_JSON_RESPONSE = "https://www.giantbomb.com/api/reviews/" +
            "?api_key=837e532ede8d717222c1247baad46cb354fb9686&format=json&limit=10";
    private ReviewAdapter mAdapter;
    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView reviewListView = (ListView) findViewById(R.id.list);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);

        mAdapter = new ReviewAdapter(this, new ArrayList<Review>());

        reviewListView.setEmptyView(mEmptyStateTextView);
        reviewListView.setAdapter(mAdapter);

        reviewListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Review currentReview = mAdapter.getItem(position);

                Uri reviewUri = Uri.parse(currentReview.getURL());

                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, reviewUri);

                startActivity(websiteIntent);
            }
        });

        if (isNetworkOnline()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(REVIEW_LOADER_ID, null, this);
        } else {
            mEmptyStateTextView.setText(R.string.no_internet);
            View loadSpinner = findViewById(R.id.loading_spinner);
            loadSpinner.setVisibility(View.GONE);
        }

    }

    public boolean isNetworkOnline() {
        boolean status = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                status = true;
            } else {
                netInfo = cm.getActiveNetworkInfo();
                if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED)
                    status = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return status;

    }

    @Override
    public Loader<List<Review>> onCreateLoader(int id, Bundle args) {
        return new ReviewLoader(this, REVIEW_JSON_RESPONSE);
    }

    @Override
    public void onLoadFinished(Loader<List<Review>> loader, List<Review> reviews) {
        mEmptyStateTextView.setText(R.string.no_reviews);
        mAdapter.clear();

        if (reviews != null && !reviews.isEmpty()) {
            mAdapter.addAll(reviews);
        }

        ProgressBar loadSpinner = (ProgressBar) findViewById(R.id.loading_spinner);
        loadSpinner.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onLoaderReset(Loader<List<Review>> loader) {
        mAdapter.clear();
    }

    public static class ReviewLoader extends AsyncTaskLoader<List<Review>> {

        private String mUrl;


        public ReviewLoader(Context context, String url) {
            super(context);
            this.mUrl = url;
        }

        @Override
        protected void onStartLoading() {
            forceLoad();
        }

        @Override
        public List<Review> loadInBackground() {
            if (mUrl == null) {
                return null;
            }
            List<Review> reviews = QueryUtils.extractReviews(mUrl);
            return reviews;
        }
    }
}
