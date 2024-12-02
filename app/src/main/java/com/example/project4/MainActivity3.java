package com.example.project4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.project4.R;

public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);


        TextView textView = findViewById(R.id.textView8);
        textView.append("TXN Ref     Date                     Points         Total\n");
        RequestQueue queue = Volley.newRequestQueue(MainActivity3.this);
        Intent intent= getIntent();
        String cid = intent.getStringExtra("cid");
        String url = "http://10.0.2.2:8080/loyaltyfirst/Transactions.jsp?cid="+cid;
        System.out.println(url);

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                String[] transactions = s.trim().split("#");
                for(String transaction:transactions) {
                    String[] parts = transaction.split(",");
                    textView.append(parts[0]+"              "+parts[1]+"          "+parts[2]+"                "+parts[3]+"\n");
                }
            }
        }, null);
        queue.add(request);
    }
}