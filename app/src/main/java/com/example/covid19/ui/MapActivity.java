package com.example.covid19.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.covid19.R;
import com.example.covid19.Statics;
import com.example.covid19.api.APIClass;
import com.example.covid19.model.Country;
import com.example.covid19.model.ResponseAllData;
import com.example.covid19.model.ResponseDetay;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity {
    APIClass apiClass;
    public List<ResponseAllData> countryList;



    private GoogleMap googleMap;
    public static String tag = "";
    public static String name = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Statics.actionbarcentertitle(this, getDrawable(R.drawable.ic_arrow_back),getString(R.string.harita), Color.RED,true,false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.onCreate(savedInstanceState);
        mapFragment.onResume();

        countryList = new ArrayList<>();
        apiClass = new APIClass();

        new Thread(new Runnable() {
            @Override
            public void run() {
                apiClass.getAllData(handler);
            }
        }).start();


        try {
            MapsInitializer.initialize(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    public void onMapClick(LatLng point) {
                        // Drawing marker on the map
                    }
                });

                googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        // TODO Auto-generated method stub
                    }
                });

                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(final Marker marker) {

                                DetailData(String.valueOf(marker.getTag()));
                                tag = String.valueOf(marker.getTag());
                                name = marker.getTitle();


                        return false;
                    }
                });


            }
        });


    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    break;
                case 10:
                    countryList.addAll((ArrayList<ResponseAllData>)msg.obj);
                    //gelen veriyi işle

                    for (int i=0;i<countryList.size();i++){

                        int height = 100;
                        int width = 100;
                        BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.virus);
                        Bitmap b = bitmapdraw.getBitmap();
                        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

                        MarkerOptions markerop = new MarkerOptions().
                                position(new LatLng(countryList.get(i).getLan(), countryList.get(i).getLon()))
                                .title(countryList.get(i).getName())
                                .icon(BitmapDescriptorFactory.fromBitmap(smallMarker));

                        Marker marker = googleMap.addMarker(markerop);
                        marker.setTag(countryList.get(i).getCode());
                    }
                    break;
                case 1:
                    ResponseDetay detayModel = (ResponseDetay) msg.obj;
                    showDetailView(name,tag,
                            String.format("%,.0f", Float.valueOf(detayModel.getConfirmed().getValue())),
                            String.format("%,.0f", Float.valueOf(detayModel.getRecovered().getValue())),
                            String.format("%,.0f", Float.valueOf(detayModel.getDeaths().getValue())));
                    break;

            }
        }
    };




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void  DetailData(final String code){
        new Thread(new Runnable() {
            @Override
            public void run() {
                apiClass.getCountriesDetail(handler,code);
            }
        }).start();
    }

    AlertDialog dialog;
    public void showDetailView(String name,String foto,String vaka,String iyilesen,String olum){
        AlertDialog.Builder builder = new AlertDialog.Builder(MapActivity.this);
        View view = getLayoutInflater().inflate(R.layout.map_detail_fragment, null);
        builder.setView(view);
        dialog = builder.create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams wmlpLogin = dialog.getWindow().getAttributes();
        wmlpLogin.gravity = Gravity.CENTER;
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView Mname = view.findViewById(R.id.textView22);
        TextView Mvaka = view.findViewById(R.id.textView23);
        TextView Miyilesen = view.findViewById(R.id.textView24);
        TextView Molum = view.findViewById(R.id.textView25);
        ImageView mimage = view.findViewById(R.id.imageView11);

        Mname.setText(name);
        Mvaka.setText(getString(R.string.vaka)+" : " + vaka);
        Miyilesen.setText(getString(R.string.iyilesen)+" : " +iyilesen);
        Molum.setText(getString(R.string.olen)+" : "+ olum);
        Glide
                .with(this)
                // .load("https://raw.githubusercontent.com/hjnilsson/country-flags/master/png1000px/"+ model.getISO2().toLowerCase() +".png" ) big image
                .load("https://www.countryflags.io/" + foto +"/flat/64.png" )
                .centerCrop()
                .placeholder(R.drawable.coronavirus)
                .into(mimage);

    }

}
