package com.altkamul.smarttask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewCharacter extends Activity implements MyRecyclerViewAdapter.ItemClickListener {
    MyRecyclerViewAdapter adapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_character);
        String url = "https://rickandmortyapi.com/api/character";
        sendRequest(url);
    }

    private void sendRequest(String url){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray results = jsonObject.getJSONArray("results");
                            buildCharacterList(results);
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ViewCharacter","Error!!");
                    }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
    private void buildCharacterList(JSONArray charactersArray){
        ArrayList<JSONObject> characters = new ArrayList<>();
        int cLength = charactersArray.length();
        int cCounter;

        //Build The Question Objects
        for (cCounter = 0; cCounter < cLength; cCounter++) {
            try {
                JSONObject character = charactersArray.getJSONObject(cCounter);
                characters.add(character);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        viewCharactersOnList(characters);
    }
    private void viewCharactersOnList(ArrayList<JSONObject> characters){
        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvCharacter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new MyRecyclerViewAdapter(this, characters);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void onItemClick(View view, int position) {
        Intent characterDetails = new Intent(this,CharacterDetails.class);
        characterDetails.putExtra("character",adapter.getItem(position).toString());
        startActivity(characterDetails);
    }
}