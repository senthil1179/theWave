package com.sp.thewave;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raman Kannan on 7/15/2017.
 */

           import static com.sp.thewave.R.id.parent;

/**
 * Created by Raman Kannan on 7/12/2017.
 */


public class CancelBooking extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView TV_selectDate;
    Button BT_calendar;
    Spinner SP_selectResource, SP_selectSession;
    String selectResource, selectSession, selectDate, userName;



    // EditText ET_Name;
    //  String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cancelbooking_layout);
        //Intent intent = getIntent();
        // userName = intent.getStringExtra("userName").toString();
        // Toast.makeText(BookResource.this, "Inside BookResource"+userName, Toast.LENGTH_LONG).show();
        // setContentView(R.layout.activity_main);
        //  ET_Name=(EditText)findViewById(R.id.userName);
        // userName=ET_Name.getText().toString();

        //userName=usrName;
        //  Toast.makeText(BookResource.this, "Inside BookResource"+usrName, Toast.LENGTH_LONG).show();

        //Code for Date picker
        TV_selectDate = (TextView) findViewById(R.id.TV_selectDate);
        BT_calendar = (Button) findViewById(R.id.BT_calendar);
        Intent incomingIntent = getIntent();
        String date = incomingIntent.getStringExtra("Date");
        TV_selectDate.setText(date);
        BT_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent calendar_intent = new Intent(CancelBooking.this, CancelCalenderActivity.class);
                startActivity(calendar_intent);
            }
        });
        selectDate = TV_selectDate.getText().toString();

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

        SP_selectSession = (Spinner) findViewById(R.id.SP_selectSession);
        SP_selectSession.setOnItemSelectedListener(this);


        List<String> Session = new ArrayList<String>();
        Session.add("");
        Session.add("Session_1");
        Session.add("Session_2");
        Session.add("Session_3");
        Session.add("Session_4");


        ArrayAdapter<String> SessionAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Session);
        SessionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SP_selectSession.setAdapter(SessionAdapter);

    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

        Spinner spinner = (Spinner) parent;
        if (spinner.getId() == R.id.SP_selectResource) {
            selectResource = SP_selectResource.getSelectedItem().toString();
        } else if (spinner.getId() == R.id.SP_selectSession) {
            selectSession = SP_selectSession.getSelectedItem().toString();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {


    }


    public void cancelBooking(View view) {

//String userName = usrName;
        String method = "cancelBooking";
        //Toast.makeText(CancelBooking.this, "Inside book before m"+ userName, Toast.LENGTH_LONG).show();
        //userName="m";
        //Toast.makeText(CancelBooking.this, "Inside bookafter m"+ userName, Toast.LENGTH_LONG).show();
        if (selectResource.equals("")) {

            Toast.makeText(CancelBooking.this, "Select resource for booking", Toast.LENGTH_LONG).show();
        } else if (selectDate.equals("")) {

            Toast.makeText(CancelBooking.this, "Select date", Toast.LENGTH_LONG).show();
        } else if (selectSession.equals("")) {

            Toast.makeText(CancelBooking.this, "Select session", Toast.LENGTH_LONG).show();
        } else {
            // Toast.makeText(CancelBooking.this, "Inside book else"+ userName, Toast.LENGTH_LONG).show();
            BackgroundTask backgroundTask = new BackgroundTask((this));
            backgroundTask.execute(method, selectResource, selectDate, selectSession);
          //  Toast.makeText(CancelBooking.this, "Action Completed", Toast.LENGTH_LONG).show();
            Intent intent_initialiseTask = new Intent(this, InitialiseTask.class );
            this.startActivity(intent_initialiseTask);
        }


    }

    public void goback(View view) {

/*String userName = usrName;
        String method = "book";
        //Toast.makeText(CancelBooking.this, "Inside book before m"+ userName, Toast.LENGTH_LONG).show();
        userName="m";
        //Toast.makeText(CancelBooking.this, "Inside bookafter m"+ userName, Toast.LENGTH_LONG).show();
        if (selectResource.equals("")) {

            Toast.makeText(CancelBooking.this, "Select resource for booking", Toast.LENGTH_LONG).show();
        } else if (selectDate.equals("")) {

            Toast.makeText(CancelBooking.this, "Select date", Toast.LENGTH_LONG).show();
        } else if (selectSession.equals("")) {

            Toast.makeText(CancelBooking.this, "Select session", Toast.LENGTH_LONG).show();
        } else {
            // Toast.makeText(CancelBooking.this, "Inside book else"+ userName, Toast.LENGTH_LONG).show();
            BackgroundTask backgroundTask = new BackgroundTask((this));
            backgroundTask.execute(method, selectResource, selectDate, selectSession, userName);*/
        //Toast.makeText(CancelBooking.this, "Cancellation in process...", Toast.LENGTH_LONG).show();
        //Toast.makeText(CancelBooking.this, "Cancellation in process...", Toast.LENGTH_LONG).show();
        //Toast.makeText(CancelBooking.this, "Cancellation in process...", Toast.LENGTH_LONG).show();
        Toast.makeText(CancelBooking.this, "Going back....", Toast.LENGTH_LONG).show();
        Intent intent_initialiseTask = new Intent(this, InitialiseTask.class );
        this.startActivity(intent_initialiseTask);
    }



}
