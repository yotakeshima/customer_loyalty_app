package com.example.project4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class MainActivity4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        Intent intent= getIntent();
        String cid = intent.getStringExtra("cid");

        Spinner spinner = findViewById(R.id.spinner);
        TextView textView = findViewById(R.id.textView9);

        ArrayList<String> list = new ArrayList<String>();
        String url = "http://10.0.2.2:8080/loyaltyfirst/Transactions.jsp?cid="+cid;
        RequestQueue queue = Volley.newRequestQueue(MainActivity4.this);
        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                String[] result=s.trim().split("#");
                for(String transaction : result) {
                    String tref = transaction.split(",")[0];
                    list.add(tref);
                }
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(MainActivity4.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,list);
                spinner.setAdapter(adapter);

            }
        },null);
        queue.add(request);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                textView.setText("");
                String tref = adapterView.getSelectedItem().toString();

                String url = "http://10.0.2.2:8080/loyaltyfirst/TransactionDetails.jsp?tref=" + tref;
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        String[] rows = s.trim().split("#");
                        String date = rows[0].split(",")[0];
                        String tPoints = rows[0].split(",")[1];
                        textView.append(date+"            " + tPoints+" Points\n\n");
                        textView.append("Prod. Name            Quantity                  Points\n");
                        for(String product : rows) {
                            String[] parts = product.split(",");
                            textView.append(parts[2] + "                           " + parts[4]+ "                              " +parts[3]+"\n");
                        }
                    }
                }, null);
                queue.add(stringRequest);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}