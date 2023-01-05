package com.example.onlineshopping;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class chart extends AppCompatActivity {
    ArrayList<String> nameProducts;
    ArrayList<BarEntry> barChartArray;
    ArrayList Products;
    ProductHelper databaseProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        databaseProduct = new ProductHelper(this);
        Products = new ArrayList();
        barChartArray = new ArrayList<BarEntry>();
        nameProducts = new ArrayList<String>();
        String  id=getIntent().getStringExtra("categoryId");
        getAllproduct(String.valueOf(id));
        for (int i = 0; i < Products.size(); i++) {
            product s = (product) Products.get(i);
            String product = ((product) Products.get(i)).getName();
            int count = Integer.parseInt(((product) Products.get(i)).getCountSale());
            barChartArray.add(new BarEntry(i, count));
            nameProducts.add(product);
        }
        BarChart barChart = findViewById(R.id.BarChart);

        BarDataSet barDataSet = new BarDataSet(barChartArray, "Products");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(20f);

        BarData barData = new BarData(barDataSet);
        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("Products");

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(nameProducts));
        xAxis.setPosition(XAxis.XAxisPosition.TOP);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(nameProducts.size());
        xAxis.setLabelRotationAngle(270);
        barChart.animateY(2000);
        barChart.invalidate();
    }

    void getAllproduct(String id){
        Products=databaseProduct.getProductOfCat(id);
    }

}