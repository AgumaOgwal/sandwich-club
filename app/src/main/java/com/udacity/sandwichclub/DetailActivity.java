package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    //Declare TextView variables, to hold sandwich data on the UI
    private TextView mAlsoKnown;
    private TextView mDescription;
    private TextView mOrigin;
    private TextView mIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        //Set a reference to our views, using their ID. Casting to the appropriate type
        mAlsoKnown = (TextView) findViewById(R.id.also_known_tv);
        mDescription = (TextView) findViewById(R.id.description_tv);
        mOrigin = (TextView) findViewById(R.id.origin_tv);
        mIngredients = (TextView) findViewById(R.id.ingredients_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];

        Sandwich sandwich = null;

        //Android Studio wouldnt leave me in peace without this. Lol
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        //Bind our data to their respective views.
        mDescription.setText(sandwich.getDescription());


        /* A neat little trick of joining all elements of a list into one string.
        *
        * https://stackoverflow.com/a/7779427/9502061
        *
        * */
        List<String> alsoKnownAs = sandwich.getAlsoKnownAs();
        String finalAlsoKnownAs = TextUtils.join(",",alsoKnownAs);
        mAlsoKnown.setText(finalAlsoKnownAs);

        mOrigin.setText(sandwich.getPlaceOfOrigin());

        //Same neat little trick here too
        List<String> sandwichIngredients = sandwich.getIngredients();
        String finalIngredients = TextUtils.join(",",sandwichIngredients);
        mAlsoKnown.setText(finalIngredients);
    }
}
