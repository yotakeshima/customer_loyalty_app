package com.example.project4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import java.util.ArrayList;

public class MainActivity5 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        Intent intent = getIntent();
        String cid = intent.getStringExtra("cid");
        Spinner spinner3 = findViewById(R.id.spinner3);

        TextView prize_desc = findViewById(R.id.prize_desc);
        TextView points_need = findViewById(R.id.points_need);
        TextView r_date = findViewById(R.id.r_date);
        TextView e_center = findViewById(R.id.e_center);


        String url = "http://10.0.2.2:8080/loyaltyfirst/PrizeIds.jsp?cid=" + cid;
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                ArrayList<String> myArrList = new ArrayList<String>();
                String[] result = s.trim().split("#");
                for (String str : result) {
                    String prize_id = str;
                    myArrList.add(prize_id);
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity5.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, myArrList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner3.setAdapter(adapter);
            }
        }, null);
        queue.add(request);

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                r_date.setText("");
                e_center.setText("");
                String prize_id = adapterView.getSelectedItem().toString();

                String url = "http://10.0.2.2:8080/loyaltyfirst/RedemptionDetails.jsp?prizeid=" + prize_id + "&cid=" + cid;
                StringRequest request2 = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        String[] rows = s.trim().split("#");
                        String[] firstRow = rows[0].split(",");
                        prize_desc.setText(firstRow[0]);
                        points_need.setText(firstRow[1]);
                        for (String str : rows) {
                            String[] cols = str.split(",");
                            r_date.append(cols[2] + "\n");
                            e_center.append(cols[3] + "\n");
                        }
                    }
                }, null);
                queue.add(request2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}