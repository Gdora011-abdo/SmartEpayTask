package com.altkamul.smarttask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONObject;

public class CharacterDetails extends AppCompatActivity {
    TextView name, location, gender, species;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_details);
        name = (TextView)findViewById(R.id.detNameId);
        location = (TextView)findViewById(R.id.detLocId);
        gender = (TextView)findViewById(R.id.detGenderId);
        species = (TextView)findViewById(R.id.detSpeciesId);
        image = (ImageView) findViewById(R.id.detImgId);
        String characterStr = getIntent().getExtras().getString("character");
        viewDetailedInfo(characterStr);

    }
    private void viewDetailedInfo(String characterStr){
        JSONObject Character = null;
        try {
            Character = new JSONObject(characterStr);
            JSONObject Location = Character.getJSONObject("location");
            String locName = Location.getString("name");
            String locUrl = Location.getString("url");
            String link = "<a href=\""+locUrl+"\">"+locName+"</a>";

            String imgUrl = Character.getString("image");
            Glide.with(this).load(imgUrl).into(image);
            name.setText(Character.getString("name"));

            location.setText(Html.fromHtml(link, Html.FROM_HTML_MODE_COMPACT));
            gender.setText(Character.getString("gender"));
            species.setText(Character.getString("species"));

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}