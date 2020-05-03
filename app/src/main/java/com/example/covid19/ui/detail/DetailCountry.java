package com.example.covid19.ui.detail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.anychart.AnyChartView;
import com.bumptech.glide.Glide;
import com.example.covid19.R;
import com.example.covid19.Statics;
import com.example.covid19.api.APIClass;
import com.example.covid19.model.ChartModel;
import com.example.covid19.model.Country;
import com.example.covid19.model.ResponseDetay;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;


public class DetailCountry extends AppCompatActivity {
    ImageView flag;
    TextView name;
    Country model;
    APIClass apiClass;
    TextView vakaSayisi;
    TextView iyilesenVakaSayisi;
    TextView olenHastaSayisi;
    TextView updateDate;
    AnyChartView anyChartView;
    List<ChartModel> chartlist;
    List<ChartModel> last7item;
    TextView charthata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_country);

        Statics.actionbarcentertitle(this, getDrawable(R.drawable.ic_arrow_back), getString(R.string.detay), Color.RED, true, false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        anyChartView = findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));
        charthata = findViewById(R.id.textView26);
        updateDate = findViewById(R.id.updateDate);
        flag = findViewById(R.id.imageView7);
        name = findViewById(R.id.textView8);
        vakaSayisi = findViewById(R.id.textView9);
        iyilesenVakaSayisi = findViewById(R.id.textView10);
        olenHastaSayisi = findViewById(R.id.textView11);
        chartlist = new ArrayList<>();
        last7item = new ArrayList<>();

        if (this.getIntent().getParcelableExtra("Countryitem") != null) {
            model = this.getIntent().getParcelableExtra("Countryitem");

            name.setText(model.getCountry());
            Glide
                    .with(this)
                    // .load("https://raw.githubusercontent.com/hjnilsson/country-flags/master/png1000px/"+ model.getISO2().toLowerCase() +".png" ) big image
                    .load("https://www.countryflags.io/" + model.getSlug() + "/flat/64.png")
                    .centerCrop()
                    .placeholder(R.drawable.coronavirus)
                    .into(flag);
            apiClass = new APIClass();


            new Thread(new Runnable() {
                @Override
                public void run() {
                    apiClass.getCountriesDetail(handler, model.getSlug());
                }
            }).start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    apiClass.getChartData(handler, model.getSlug());
                }
            }).start();
        }


    }

    public String getDate(String mDate) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US);
            Date date = dateFormat.parse(mDate);
            dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
            return dateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getTime(String mCreatedAt) {

        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US);
            Date date = dateFormat.parse(mCreatedAt);
            dateFormat = new SimpleDateFormat("h:mm a", Locale.US);
            return dateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    ResponseDetay detayModel;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 0:
                    break;
                case 1:
                    detayModel = (ResponseDetay) msg.obj;

                    vakaSayisi.setText(String.format("%,.0f", Float.valueOf(detayModel.getConfirmed().getValue())));
                    iyilesenVakaSayisi.setText(String.format("%,.0f", Float.valueOf(detayModel.getRecovered().getValue())));
                    olenHastaSayisi.setText(String.format("%,.0f", Float.valueOf(detayModel.getDeaths().getValue())));


                    try {
                        String date = getDate(detayModel.getLastUpdate());
                        String saat = getTime(detayModel.getLastUpdate());
                        updateDate.setText(date + " saat: " + saat + " 'de güncellendi");
                    } catch (Exception e) {

                    }

                    break;
                case 10:
                    charthata.setVisibility(View.GONE);
                    anyChartView.setVisibility(View.VISIBLE);
                    chartlist.addAll((ArrayList<ChartModel>) msg.obj);
                    last7item = chartlist.subList(chartlist.size()-7, chartlist.size());
                    loadChart();
                    break;
                case 100:
                    anyChartView.setVisibility(View.GONE);
                    charthata.setVisibility(View.VISIBLE);
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

    public void loadChart(){
        Cartesian cartesian = AnyChart.line();

        cartesian.animation(true);

        cartesian.padding(10d, 20d, 5d, 20d);

        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                // TODO ystroke
                .yStroke((Stroke) null, null, null, (String) null, (String) null);

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

        cartesian.title("Son yedi gündeki veriler");

        cartesian.yAxis(0).title("Sayı");
        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

        List<DataEntry> seriesData = new ArrayList<>();

        for (int i=0;i<last7item.size();i++){
            String date = last7item.get(i).getDate().substring(6,10).replace("-","/");
            seriesData.add(new CustomDataEntry(date, last7item.get(i).getVaka(), last7item.get(i).getIyilesen(), last7item.get(i).getOlen()));
        }

        Set set = Set.instantiate();
        set.data(seriesData);
        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
        Mapping series2Mapping = set.mapAs("{ x: 'x', value: 'value2' }");
        Mapping series3Mapping = set.mapAs("{ x: 'x', value: 'value3' }");

        Line series1 = cartesian.line(series1Mapping);
        series1.name("Vaka");
        series1.hovered().markers().enabled(true);
        series1.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series1.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        Line series2 = cartesian.line(series2Mapping);
        series2.name("İyileşen");
        series2.hovered().markers().enabled(true);
        series2.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series2.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        Line series3 = cartesian.line(series3Mapping);
        series3.name("Ölüm");
        series3.hovered().markers().enabled(true);
        series3.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series3.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        cartesian.legend().enabled(true);
        cartesian.legend().fontSize(13d);
        cartesian.legend().padding(0d, 0d, 10d, 0d);

        anyChartView.setChart(cartesian);
    }

    private class CustomDataEntry extends ValueDataEntry {

        CustomDataEntry(String x, Number value, Number value2, Number value3) {
            super(x, value);
            setValue("value2", value2);
            setValue("value3", value3);
        }

    }
}
