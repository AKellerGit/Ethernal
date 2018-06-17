package com.example.redent0r.ethernal4;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author redent0r
 *
 */

public class LotteryAdapter extends ArrayAdapter<Lottery> {

    private static final String TAG = LotteryAdapter.class.getSimpleName();

    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");

    public LotteryAdapter(@NonNull Context context, int resource, @NonNull List<Lottery> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {

        if (view == null)
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_lottery, parent, false);

        TextView tvTransactionId = view.findViewById(R.id.tvTransactionId);

        TextView tvAmount = view.findViewById(R.id.tvAmount);

        TextView tvTime = view.findViewById(R.id.tvTime);

        TextView tvCompleted = view.findViewById(R.id.tvCompleted);

        TextView tvMaxParticipants = view.findViewById(R.id.tvMaxParticipants);

        final Lottery lottery = getItem(position);

        Log.d(TAG, "getView: id: "+ lottery.getId());

        tvTransactionId.setText(lottery.getId());

        tvAmount.setText(lottery.getEntryAmount().toString() + " ETH");
        tvTime.setText(sdf.format(new Date(lottery.getTime())));

        tvMaxParticipants.setText(lottery.getMaxParticipants() + "");

        if(lottery.getCompleted()) tvCompleted.setText("Completed");
        else tvCompleted.setText("Not completed");

        return view;
    }
}
