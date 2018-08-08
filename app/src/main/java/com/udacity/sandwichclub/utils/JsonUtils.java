package com.udacity.sandwichclub.utils;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.udacity.sandwichclub.model.Sandwich;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json)
    throws JSONException{

        //Create variables for the JSON key values
        final String MAIN_NAME = "mainName";
        final String ALSO_KNOWN_AS = "alsoKnownAs";
        final String ORIGIN = "placeOfOrigin";
        final String DESCRIPTION = "description";
        final String IMAGE = "image";
        final String INGREDIENTS = "ingredients";

        //Get a new sandwich object
        Sandwich sandwich = new Sandwich();

        JSONObject sandwichData = new JSONObject(json);

        //Extract values from the JSON
        sandwich.setPlaceOfOrigin(sandwichData.get(ORIGIN).toString());
        sandwich.setImage(sandwichData.getString(IMAGE));
        sandwich.setDescription(sandwichData.getString(DESCRIPTION));
        sandwich.setMainName(sandwichData.getJSONObject("name").getString(MAIN_NAME));


        List<String> alsoKnownAs = extractListFromJSON(sandwichData.getJSONObject("name").getJSONArray(ALSO_KNOWN_AS));
        sandwich.setAlsoKnownAs(alsoKnownAs);

        List<String> ingredients = extractListFromJSON(sandwichData.getJSONArray(INGREDIENTS));
        sandwich.setIngredients(ingredients);


        return sandwich;
    }

    private static List<String> extractListFromJSON(JSONArray arrayJson) throws JSONException {
        List<String> list = new ArrayList<>();
        for (int i = 0; i <arrayJson.length(); i++) {
            String str = arrayJson.getString(i);
            list.add(str);
        }
        return list;
    }
}
