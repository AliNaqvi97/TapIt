package com.ahn.tapit;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by Ali on 11/12/2015.
 */
public class MyButton extends Button {

    public MyButton(Context context) {
        super(context);
        setFont();
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public MyButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFont();
    }
    private void setFont() {
        Typeface normal = Typeface.createFromAsset(getContext().getAssets(),"fonts/atariFull.ttf");
        setTypeface(normal);
    }
}
