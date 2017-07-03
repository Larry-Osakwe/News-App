package com.larry.osakwe.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Larry Osakwe on 7/2/2017.
 */

public class ReviewAdapter extends ArrayAdapter<Review> {

    private Review currentReview;


    public ReviewAdapter(Context context, List<Review> reviews) {
        super(context, 0, reviews);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.review_list_item, parent, false);

        }

        currentReview = getItem(position);

        ImageView imageView = (ImageView)listItemView.findViewById(R.id.game_image);
        Picasso.with(getContext()).load(currentReview.getImage()).into(imageView);

        TextView gameTitle = (TextView) listItemView.findViewById(R.id.game_title);
        gameTitle.setText(currentReview.getGameTitle());

        TextView date = (TextView) listItemView.findViewById(R.id.review_date);
        date.setText(dateFormat());

        TextView author = (TextView) listItemView.findViewById(R.id.author);
        author.setText(currentReview.getAuthor());

        TextView platform = (TextView) listItemView.findViewById(R.id.game_platform);
        platform.setText(currentReview.getPlatform());

        RatingBar rating = (RatingBar) listItemView.findViewById(R.id.ratingBar);
        rating.setRating(currentReview.getRating());


        return listItemView;
    }

    public String dateFormat() {
        long timeInMilliseconds = currentReview.getDate();
        Date dateObject = new Date(timeInMilliseconds);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM dd, yyyy");

        return dateFormatter.format(dateObject);
    }
}
