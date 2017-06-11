package com.ahn.tapit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ali on 10/27/2015.
 */
public class ListViewAdapter extends ArrayAdapter<Integer> {


    public ListViewAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ListViewAdapter(Context context, int resource, List<Integer> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // assign the view we are converting to a local variable
        View v = convertView;

        // first check to see if the view is null. if so, we have to inflate it.
        // to inflate it basically means to render, or show, the view.
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.score_list, null);
        }

        Integer i = getItem(position);

        if (i != null) {

            // This is how you obtain a reference to the TextViews.
            // These TextViews are created in the XML files we defined.

            TextView tv = (TextView) v.findViewById(R.id.score_text);

            // check to see if each individual textview is null.
            // if not, assign some text!
            if (tv != null){
                tv.setText(""+i.intValue());
            }

        }

        // the view must be returned to our activity
        return v;

    }

    @Override
    public int getCount(){
        return 10;
    }

}
