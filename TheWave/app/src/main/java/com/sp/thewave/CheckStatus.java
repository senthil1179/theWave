package com.sp.thewave;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.sp.thewave.R.id.parent;

/**
 * Created by Raman Kannan on 7/14/2017.
 */


public class CheckStatus extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner SP_selectResource;
    String selectResource;
    String userName;
    private static final String TAG= "CheckStatus";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkstatus_layout);
        userName = this.getIntent().getStringExtra("userName").toString();

        SP_selectResource = (Spinner) findViewById(R.id.SP_selectResource);
        SP_selectResource.setOnItemSelectedListener(this);
        List<String> resources = new ArrayList<String>();
        resources.add("");
        resources.add("Room_1");
        resources.add("Room_2");
        resources.add("Desk_1");
        resources.add("Label_printer_1");
        ArrayAdapter<String> ResourceAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, resources);
        ResourceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SP_selectResource.setAdapter(ResourceAdapter);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        selectResource = SP_selectResource.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }

    public void getStatus(View view) {
        String method = "getStatus";
        if (selectResource.equals("")) {
            Toast.makeText(CheckStatus.this, "Select resource for booking", Toast.LENGTH_LONG).show();
        }  else {
            BackgroundTask backgroundTask=new BackgroundTask(){
                @Override
                public void onPostExecuteCallback (JSONObject json) {
                    try {
                        // json success tag
                        int success = json.getInt("success");
                        if (success == 1) {
                            if (Integer.parseInt(json.getString("inUse")) == 1)
                                Toast.makeText(CheckStatus.this, "In Use", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(CheckStatus.this, "NOT In Use", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(CheckStatus.this, "Error in Connection.", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {

                    }
                }
            };
            backgroundTask.execute(method, selectResource);
        }
    }

    public void goBack(View view) {
        finish ();
    }



}
