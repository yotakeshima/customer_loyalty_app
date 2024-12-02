package com.example.project4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class MainActivity6 extends AppCompatActivity {
    String fid = "";
    int tpoints = 0;
    int fpercent = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        Intent intent= getIntent();
        String cid = intent.getStringExtra("cid");

        Spinner spinner = findViewById(R.id.spinner2);
        TextView textView = findViewById(R.id.textView7);
        Button button = findViewById(R.id.button2);


        ArrayList<String> list = new ArrayList<String>();
        String url = "http://10.0.2.2:8080/loyaltyfirst/Transactions.jsp?cid="+cid;
        RequestQueue queue = Volley.newRequestQueue(MainActivity6.this);
        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                String[] result=s.trim().split("#");
                for(String transaction : result) {
                    String tref = transaction.split(",")[0];
                    list.add(tref);
                }
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(MainActivity6.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,list);
                spinner.setAdapter(adapter);

            }
        },null);
        queue.add(request);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                textView.setText("");
                String tref = adapterView.getSelectedItem().toString();

                String url = "http://10.0.2.2:8080/loyaltyfirst/SupportFamilyIncrease.jsp?cid="+cid+"&tref=" + tref;
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        String[] parts = s.trim().split("#")[0].split(",");
                        tpoints = Integer.parseInt(parts[2]);
                        fid = parts[0];
                        fpercent = Integer.parseInt(parts[1]);

                        textView.append("TXT POINTS: "+ tpoints+"\n");
                        textView.append("Family ID: "+ fid + "\n");
                        textView.append("Family Percent: "+ fpercent+ "\n");
                    }
                }, null);
                queue.add(stringRequest);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int npoints = (int) ((double)tpoints * ((double)fpercent/100.0));
                String url = "http://10.0.2.2:8080/loyaltyfirst/FamilyIncrease.jsp?cid="+cid+"&fid=" + fid+"&npoints="+npoints;
                StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if(s.trim().equals("yes")) {
                            String message = npoints + " Points added to the members of Family ID " + fid;
                            Toast.makeText(MainActivity6.this, message, Toast.LENGTH_LONG).show();
                        }
                    }
                },null);
                queue.add(request);
            }
        });

    }
}