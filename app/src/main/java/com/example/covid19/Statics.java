package com.example.covid19;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;

public class Statics {

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    //Action bar olusturan fonksiyon geri butonu ve baslık ile birlikte olusur
    public static void actionbarcentertitle(AppCompatActivity act, Drawable icon, String title, int color, boolean left, boolean right) {
        ActionBar ab = act.getSupportActionBar();
        ab.setTitle(title);
        ab.setHomeAsUpIndicator(icon);
        Window window = act.getWindow();
        View decor = window.getDecorView();
        ArrayList<View> views = new ArrayList<View>();
        ViewGroup vv = (ViewGroup)  decor;
        decor.findViewsWithText(views, title, View.FIND_VIEWS_WITH_TEXT);
        TextView textView = (TextView) views.get(0);
        Toolbar.LayoutParams layoutParams = (Toolbar.LayoutParams) textView.getLayoutParams();
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.width = Toolbar.LayoutParams.MATCH_PARENT;
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(color);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        Typeface tf = ResourcesCompat.getFont(act,R.font.ubuntu_medium);
        textView.setTypeface(tf);
        int leftpadding=0;
        int rightpadding=0;
        if (!left && !right){
            //değerler 0
        }else if(right && left){
            //değerler 0

        }else if (left && !right){
            rightpadding = dpToPx(56);


        }else if(right && !left){
            leftpadding = dpToPx(56);
        }

        textView.setPadding(leftpadding,0,rightpadding,0);
        textView.setLayoutParams(layoutParams);

        for (View view : views) {

            ViewGroup viewGroup = (ViewGroup) view.getParent();//imagei ekledik.
        }
    }

}
