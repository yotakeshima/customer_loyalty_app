package com.example.project4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity2 extends AppCompatActivity {
    String name;
    String points;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();
        String cid = intent.getStringExtra("cid");
        TextView textView2 = findViewById(R.id.textView2);
        TextView textViewPoints = findViewById(R.id.textViewPoints);
        ImageView imageView = findViewById(R.id.imageView);
        //buttons
        Button button_all_txns = findViewById(R.id.button_all_txns);
        Button button_txns_details = findViewById(R.id.button_txns_details);
        Button button_redmptn_details = findViewById(R.id.button_redmptn_details);
        Button button_percent = findViewById(R.id.button_percent);
        Button button_exit = findViewById(R.id.button_exit);
        RequestQueue queue = Volley.newRequestQueue(MainActivity2.this);
        String url = "http://10.0.2.2:8080/loyaltyfirst/Info.jsp?cid="+cid;

        //Requests CID and displays cname and points
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                    String[] customer = s.trim().split(",");
                    name = customer[0];
                    points = customer[1];
                    textView2.setText(name);
                    textViewPoints.setText(points);

            }
            // NULL is error listener
        }, null);
        queue.add(request);
        String url1 = "http://10.0.2.2:8080/loyaltyfirst/images/" + cid + ".jpg";
        ImageRequest request1 = new ImageRequest(url1, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
            }
        }, 0, 0, null, null);
        queue.add(request1);

        //                             ***Button Functions***

        //ALL TRANSACTIONS
        button_all_txns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                intent.putExtra("cid", cid);
                startActivity(intent);
            }
        });

        //TRANSACTION DETAILS
        button_txns_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, MainActivity4.class);
                intent.putExtra("cid", cid);
                startActivity(intent);
            }
        });

        //REDEMPTION DETAILS
        button_redmptn_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, MainActivity5.class);
                intent.putExtra("cid", cid);
                startActivity(intent);
            }
        });

        //ADD PERCENT POINTS
        button_percent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, MainActivity6.class);
                intent.putExtra("cid", cid);
                startActivity(intent);
            }
        });

        //EXIT
        button_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}