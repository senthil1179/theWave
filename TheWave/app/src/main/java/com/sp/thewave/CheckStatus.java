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

import java.util.ArrayList;
import java.util.List;

import static com.sp.thewave.R.id.parent;

/**
 * Created by Raman Kannan on 7/14/2017.
 */


public class CheckStatus extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //TextView TV_selectDate;
   // Button BT_calendar;
    Spinner SP_selectResource;
    String selectResource;
    Button BT_getStatus, BT_OK;
    TextView TV_selectResource;
    EditText ET_display;




    // EditText ET_Name;
    //  String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkstatus_layout);
        BT_getStatus=(Button)findViewById(R.id.BT_getStatus);
        BT_OK=(Button)findViewById(R.id.BT_OK);
        TV_selectResource=(TextView)findViewById(R.id.TV_selectResource);
        ET_display=(EditText)findViewById(R.id.ET_display);

        //Intent intent = getIntent();
        // userName = intent.getStringExtra("userName").toString();
        // Toast.makeText(BookResource.this, "Inside BookResource"+userName, Toast.LENGTH_LONG).show();
        // setContentView(R.layout.activity_main);
        //  ET_Name=(EditText)findViewById(R.id.userName);
        // userName=ET_Name.getText().toString();

        //userName=usrName;
        //  Toast.makeText(BookResource.this, "Inside BookResource"+usrName, Toast.LENGTH_LONG).show();

        SP_selectResource = (Spinner) findViewById(R.id.SP_selectResource);
        SP_selectResource.setOnItemSelectedListener(this);

        final List<String> resources = new ArrayList<String>();
        resources.add("");
        resources.add("Room_1");
        resources.add("Room_2");
        resources.add("Desk_1");
        resources.add("Label_printer_1");
        resources.add("printer_1");
        resources.add("dock");

        ArrayAdapter<String> ResourceAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, resources);
        ResourceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SP_selectResource.setAdapter(ResourceAdapter);
       //selectResource = SP_selectResource.getSelectedItem().toString();
           }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {


            selectResource = SP_selectResource.getSelectedItem().toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {


    }


    public void getStatus(View view) {

//String userName = usrName;
        String method = "getStatus";
        //Toast.makeText(BookResource.this, "Inside book before m"+ userName, Toast.LENGTH_LONG).show();
       // userName="m";
        //Toast.makeText(BookResource.this, "Inside bookafter m"+ userName, Toast.LENGTH_LONG).show();
        if (selectResource.equals("")) {

            Toast.makeText(CheckStatus.this, "Select resource for booking", Toast.LENGTH_LONG).show();
        }  else {
            // Toast.makeText(BookResource.this, "Inside book else"+ userName, Toast.LENGTH_LONG).show();
            BackgroundTask backgroundTask = new BackgroundTask((this));
            backgroundTask.execute(method, selectResource);


          Toast.makeText(CheckStatus.this, "Action Completed", Toast.LENGTH_LONG).show();
           //Intent intent_initialiseTask = new Intent(this, InitialiseTask.class );
            //this.startActivity(intent_initialiseTask);
        }


    }

    public void OK(View view) {

/*String userName = usrName;
        String method = "book";
        //Toast.makeText(BookResource.this, "Inside book before m"+ userName, Toast.LENGTH_LONG).show();
        userName="m";
        //Toast.makeText(BookResource.this, "Inside bookafter m"+ userName, Toast.LENGTH_LONG).show();
        if (selectResource.equals("")) {

            Toast.makeText(BookResource.this, "Select resource for booking", Toast.LENGTH_LONG).show();
        } else if (selectDate.equals("")) {

            Toast.makeText(BookResource.this, "Select date", Toast.LENGTH_LONG).show();
        } else if (selectSession.equals("")) {

            Toast.makeText(BookResource.this, "Select session", Toast.LENGTH_LONG).show();
        } else {
            // Toast.makeText(BookResource.this, "Inside book else"+ userName, Toast.LENGTH_LONG).show();
            BackgroundTask backgroundTask = new BackgroundTask((this));
            backgroundTask.execute(method, selectResource, selectDate, selectSession, userName);*/
        //Toast.makeText(BookResource.this, "Cancellation in process...", Toast.LENGTH_LONG).show();
        //Toast.makeText(BookResource.this, "Cancellation in process...", Toast.LENGTH_LONG).show();
        //Toast.makeText(BookResource.this, "Cancellation in process...", Toast.LENGTH_LONG).show();
      //  Toast.makeText(BookResource.this, "Cancellation in process...", Toast.LENGTH_LONG).show();


        Intent intent_initialiseTask = new Intent(this, InitialiseTask.class );
        this.startActivity(intent_initialiseTask);
    }



}
