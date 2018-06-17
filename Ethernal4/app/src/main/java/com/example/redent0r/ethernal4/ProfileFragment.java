package com.example.redent0r.ethernal4;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author redent0r
 *
 */

public class ProfileFragment extends Fragment {

    TextView tvAddress1;

    public TextView tvWallet1;

    EditText etAddress;
    EditText etAmount;

    String userAddress;

    Button btnWithdraw;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        tvAddress1 = (TextView)v.findViewById(R.id.tvAddress1);

        tvWallet1 = (TextView)v.findViewById(R.id.tvWallet1);

        etAddress = (EditText)v.findViewById(R.id.etAddress);

        etAmount = (EditText)v.findViewById(R.id.etWithdrawAmount);

        btnWithdraw = (Button)v.findViewById(R.id.btnWithdraw);

        Bundle bundle = getArguments();

        userAddress = bundle.getString(MainActivity.KEY_USER_ID, "0x0234975057039570752750957573475092389028");

        tvAddress1.setText(userAddress);

        getBalance();

        btnWithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnWithdraw.setEnabled(false);
                withdraw();
            }
        });

        return v;
    }

    private void getBalance() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, MainActivity.serverUrlGetUserInfo, null  ,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the first 500 characters of the response string.
                        if(response.has("success"))
                            try {
                            tvWallet1.setText(response.getDouble("success") + " ETH");
                            } catch (JSONException e) {e.printStackTrace();}
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        Volley.newRequestQueue(getActivity().getApplicationContext()).add(jsonObjectRequest);
    }

    private void withdraw() {

        String address = etAddress.getText().toString();
        String amount = etAmount.getText().toString();

        JSONObject jsonObject = new JSONObject();
        try {
            //jsonObject.put("userId", MainActivity.userId);
            jsonObject.put("amount", amount);
            jsonObject.put("address", address);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, MainActivity.serverUrlWithdraw, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        btnWithdraw.setEnabled(true);
                        Toast.makeText(getActivity().getApplicationContext(),"Your withdrawal has been executed succesfully", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        Volley.newRequestQueue(getActivity().getApplicationContext()).add(jsonObjectRequest);
    }

}