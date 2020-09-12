package com.example.to_lettracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// API View
public class ToLetActivity extends AppCompatActivity {

    private RecyclerView apiRecycleView;
    private RecyclerView.Adapter adapter;

    private List<APIModel> flatListingArrayList;

    private RequestQueue requestQueue;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tolet);

        mAuth = FirebaseAuth.getInstance();
        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user == null) {
                    Intent profileActivity = new Intent(ToLetActivity.this, ProfileActivity.class);
                    startActivity(profileActivity);
                    finish();

                }
            }
        };

        flatListingArrayList = new ArrayList<>();

        apiRecycleView = (RecyclerView) findViewById(R.id.recycler);
        apiRecycleView.setLayoutManager(new LinearLayoutManager(ToLetActivity.this));

        requestQueue = Volley.newRequestQueue(this);

        jsonParse();

    }

    // Parses Data From Web
    private void jsonParse(){
        String url = "http://10.0.2.2:8000/graphql/query";

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("all_flatlistinginfo");
                            for (int i=0; i<jsonArray.length(); i++){
                                JSONObject flatListing = jsonArray.getJSONObject(i);

                                APIModel apiModel = new APIModel(flatListing.getString("id"),
                                        flatListing.getString("phone_number"),
                                        flatListing.getString("location"),
                                        flatListing.getString("flat_description"),
                                        flatListing.getString("flat_size"),
                                        flatListing.getString("total_rent"),
                                        flatListing.getString("post_time"),
                                        flatListing.getString("is_active"));
                                flatListingArrayList.add(apiModel);

                                adapter = new CustomAdapter(flatListingArrayList, getApplicationContext());

                                apiRecycleView.setAdapter(adapter);

                              Toast.makeText(ToLetActivity.this,"All The Listings Loaded", Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(ToLetActivity.this, "Couldn't Show The List!!!", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(request);

    }

    public void onClickMap(View view) {
        Intent mapIntent = new Intent(ToLetActivity.this, MapsActivity.class);
        startActivity(mapIntent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthStateListener);
    }
}
