package com.ahn.tapit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ListViewAdapter extends ArrayAdapter<Integer> {
    private static final int ITEMS = 10;

    public ListViewAdapter(Context context, int resource, int textViewResourceId,
                           List<Integer> items) {
        super(context, resource, textViewResourceId, items);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.score_list, null);
        }

        Integer i = getItem(position);
        if (i != null) {
            TextView tv = (TextView) v.findViewById(R.id.score_text);
            if (tv != null) {
                tv.setText(String.valueOf(i));
            }
        }
        return v;
    }

    @Override
    public int getCount() {
        return ITEMS;
    }

}
