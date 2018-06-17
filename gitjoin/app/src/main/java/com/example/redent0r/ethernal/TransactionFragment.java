package com.example.redent0r.ethernal;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author redent0r
 *

 */
public class TransactionFragment extends Fragment {

    private static final String TAG = TransactionFragment.class.getName();

    ListView lvHistory;
    TransactionAdapter transactionAdapter;

    List<Transaction> transactionList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_history, container, false);

        lvHistory = (ListView) v.findViewById(R.id.lvHistory);

        testLvHistory();

        transactionAdapter = new TransactionAdapter(getContext(), R.layout.item_transaction, transactionList);

        lvHistory.setAdapter(transactionAdapter);

        return v;
    }

    private void testLvHistory() {
        for(int i = 0; i < 10; ++i) {
            String id = new String(i + "");
            transactionList.add(
                    new Transaction(id, 5D, "w", 1520543308L));
        }
    }
}
