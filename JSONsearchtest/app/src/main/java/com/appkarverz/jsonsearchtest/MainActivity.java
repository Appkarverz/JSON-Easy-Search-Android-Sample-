package com.appkarverz.jsonsearchtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import appkarverz.devapps.jsonsearch.AkZ_JsonSearcher;


public class MainActivity extends AppCompatActivity {

    private TextView akzTv_JsonView;
    private EditText akzET_URL;
    private EditText akzet_key;
    private EditText akzEt_JsonView;
    private Button akzBtn_LoadFullJson;
    private Button akzBtn_LoadResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        setListners();

        String youtubeJson = "http://api.openweathermap.org/data/2.5/weather?q=india";
        akzET_URL.setText(youtubeJson);

    }

    private void setListners() {

        akzBtn_LoadFullJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestCompleteJSON(akzET_URL.getText().toString());
            }
        });

        akzBtn_LoadResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestValueInJSON(akzET_URL.getText().toString(), akzet_key.getText().toString());
            }
        });

    }

    private void requestValueInJSON(String akzStr_URL, final String akzStr_key) {

        RequestQueue rq = Volley.newRequestQueue(this);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, akzStr_URL, null,
                //executes this method when request has no errors.
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        String res = AkZ_JsonSearcher.getAllKeyValues(response, akzStr_key).toString(); //Get all the values in the JSON
                        akzEt_JsonView.setText(res);
                        akzTv_JsonView.setText("Value in for key:"+akzStr_key);

                    }
                }, new Response.ErrorListener() { //for handling error
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
                akzEt_JsonView.setText("Error");
            }
        });
        rq.add(jsonRequest);
    }

    private void requestCompleteJSON(String akzStr_URL) {
        RequestQueue rq = Volley.newRequestQueue(this);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, akzStr_URL, null,
                //executes this method when request has no errors.
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        akzEt_JsonView.setText(response.toString());
                        akzTv_JsonView.setText("JSON Loaded");

                    }
                }, new Response.ErrorListener() { //for handling error
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
                akzEt_JsonView.setText("Error");
            }
        });
        rq.add(jsonRequest);
    }

    private void findViews() {

        akzTv_JsonView = (TextView)findViewById(R.id.akzTv_JsonView);
        akzET_URL = (EditText)findViewById(R.id.akzET_URL);
        akzet_key = (EditText)findViewById(R.id.akzet_key);
        akzEt_JsonView  = (EditText)findViewById(R.id.akzEt_JsonView);
        akzBtn_LoadFullJson = (Button)findViewById(R.id.akzBtn_LoadFullJson);
        akzBtn_LoadResult  = (Button)findViewById(R.id.akzBtn_LoadResult);

    }


}
