package com.example.redent0r.ethernal4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class LotterySummary extends AppCompatActivity {

    TextView tvYourId;
    TextView tvUsername;
    TextView tvParticipants;
    Button btnJoin;
    String lotteryId;
    TextView tvLotteryAddress;

    private static final String TAG = LotterySummary.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery_summary);

        tvYourId = (TextView) findViewById(R.id.tvYourId);
        tvUsername = (TextView)findViewById(R.id.tvWinnerName);
        tvLotteryAddress = (TextView)findViewById(R.id.tvLotteryAddress);
        tvParticipants = (TextView)findViewById(R.id.tvParticipants);

        btnJoin = (Button) findViewById(R.id.btnJoin);
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnJoin.setEnabled(false);
                Toast.makeText(getApplicationContext(),"Please hold 45 seconds for the ethereum contract to take place", Toast.LENGTH_LONG).show();
                joinLottery(lotteryId);
            }
        });

        lotteryId = getIntent().getStringExtra(LotteryFragment.KEY_LOTTERY_ID);

        tvLotteryAddress.setText(lotteryId);

        refreshLotterySummary();
    }

    private void refreshLotterySummary() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("contract", lotteryId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonRequest = new JsonObjectRequest(MainActivity.serverUrlGetUserContracts, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        /*
                        if (response.has("winnerId")) {
                            try {
                                String winnerId = response.getString("winnerId");
                                tvYourId.setText(winnerId);
                            }
                            catch (JSONException e) {e.printStackTrace();}
                        }
                        */
                        Log.d(TAG, "onResponse: " + response);
                        Integer participants=0;
                        try {
                            Iterator<String> iterator = response.keys();

                            while (iterator.hasNext()) {
                                participants++;
                                JSONArray jsonArray = response.getJSONArray(iterator.next());
                                Log.d(TAG, "onResponse: " + jsonArray);
                                String username = jsonArray.getString(0);
                                String userAddress = jsonArray.getString(1);
                                Boolean winner = jsonArray.getBoolean(3);

                                if(winner)tvUsername.setText(username);

                                tvYourId.setText(userAddress);
                            }
                        } catch (JSONException e) {e.printStackTrace();}
                        tvParticipants.setText(participants + "");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(getApplicationContext()).add(jsonRequest);
    }

    private void joinLottery(String lotteryId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("contract", lotteryId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonRequest = new JsonObjectRequest(MainActivity.serverUrlPlayLottery, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        refreshLotterySummary();
                        Log.d(TAG, "onResponse: response received");
                        btnJoin.setEnabled(true);
                        if (response.has("success")) {
                            Toast.makeText(getApplicationContext(), "You have been added to this lottery", Toast.LENGTH_LONG).show();
                            Log.d(TAG, "onResponse: success = true" + response);
                        } else {
                            Toast.makeText(getApplicationContext(), "You couldn't join the lottery", Toast.LENGTH_LONG).show();
                            Log.d(TAG, "onResponse: success = false" + response);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        jsonRequest.setRetryPolicy(new DefaultRetryPolicy((60 * 1000), 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getApplicationContext()).add(jsonRequest);
    }
}