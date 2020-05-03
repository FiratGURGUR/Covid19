package com.example.covid19.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import com.example.covid19.R;
import com.example.covid19.Statics;
import com.example.covid19.api.APIClass;
import com.example.covid19.model.ResponseGeneral;
import com.example.covid19.ui.search.SearchActivity;

public class MainActivity extends AppCompatActivity {
    APIClass apiClass;
    TextView vakaSayisi;
    TextView iyilesenVakaSayisi;
    TextView olenHastaSayisi;

    CardView searchCV;
    CardView haberCV;
    CardView protetCV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Statics.actionbarcentertitle(this, getDrawable(R.drawable.ic_arrow_back),getString(R.string.korunavirus_bilgi), Color.RED,false,false);

        searchCV = findViewById(R.id.card_search);
        haberCV = findViewById(R.id.haber);
        protetCV = findViewById(R.id.howcanprotect);

        vakaSayisi = findViewById(R.id.tv_vaka_sayisi);
        iyilesenVakaSayisi = findViewById(R.id.tv_iyilesen_vaka_sayisi);
        olenHastaSayisi = findViewById(R.id.tv_olen_hasta_sayisi);
        apiClass = new APIClass();

        new Thread(new Runnable() {
            @Override
            public void run() {
                apiClass.getWorldCounts(handler);
            }
        }).start();


        searchCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
        haberCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });
        protetCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HowCanProtectedActivity.class);
                startActivity(intent);
            }
        });

    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    break;
                case 1:
                    ResponseGeneral model = (ResponseGeneral) msg.obj;

                    vakaSayisi.setText(String.format("%,.0f", Float.valueOf(model.getConfirmed().getValue())));
                    iyilesenVakaSayisi.setText(String.format("%,.0f", Float.valueOf(model.getRecovered().getValue())));
                    olenHastaSayisi.setText(String.format("%,.0f", Float.valueOf(model.getDeaths().getValue())));

                    break;
            }
        }
    };


}
