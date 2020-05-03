package com.example.covid19.ui.search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.covid19.R;
import com.example.covid19.Statics;
import com.example.covid19.api.APIClass;
import com.example.covid19.model.Country;
import com.example.covid19.ui.CustomItemClickListener;
import com.example.covid19.ui.detail.DetailCountry;
import java.util.ArrayList;
import java.util.List;


public class SearchActivity extends AppCompatActivity {
    APIClass apiClass;
    RecyclerView recyclerView;
    SearchAdapter searchAdapter;
   public List<Country> countryList;
   EditText search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Statics.actionbarcentertitle(this, getDrawable(R.drawable.ic_arrow_back),getString(R.string.arama), Color.RED,true,false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        search = findViewById(R.id.search_tv);
        recyclerView = findViewById(R.id.recyclerViewCountry);
        countryList = new ArrayList<>();
        loadList();
        apiClass = new APIClass();

        new Thread(new Runnable() {
            @Override
            public void run() {
                apiClass.getCountries(handler);
            }
        }).start();


        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchAdapter.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

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
                     countryList.addAll((ArrayList<Country>) msg.obj);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                          searchAdapter.notifyDataSetChanged();
                        }
                    });
                    break;
            }
        }
    };


    LinearLayoutManager linearLayoutManager;
    public void loadList() {

        searchAdapter = new SearchAdapter(countryList,this, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                countryList = SearchAdapter.filtered_list;
                if (countryList.get(position).getISO2() == null || countryList.get(position).getISO2().isEmpty()){
                    Toast.makeText(SearchActivity.this, getString(R.string.ulkeye_ait_veri_yok), Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(SearchActivity.this, DetailCountry.class);
                    intent.putExtra("Countryitem", (Parcelable) countryList.get(position));
                    startActivity(intent);
                }


            }
        });

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(searchAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }
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
}
