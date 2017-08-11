package com.sp.thewave;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

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

    private Spinner SP_selectResource, SP_selectSession;
    private CalendarView myCalendarView;
    private static final String TAG= "CancelBooking";
    String selectResource, selectSession, selectDate, userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cancelbooking_layout);
        Intent intent = getIntent();
        userName = intent.getStringExtra("userName").toString();

        // code for Date picker
        myCalendarView = (CalendarView) findViewById(R.id.calendarView);
        myCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2){
                if(i1<=9){
                    i1=i1+1;
                    selectDate=i+"-0"+i1+"-"+i2;
                }else{
                    i1=i1+1;
                    selectDate=i+"-"+i1+"-"+i2;
                }
                Toast.makeText(CancelBooking.this, "Inside calendar"+ selectDate, Toast.LENGTH_LONG).show();
                Log.d(TAG, "onSelectedDayChange: date "+selectDate);
            }
        });

        // code for Resource selection
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

        // code for Session selection
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
        String method = "cancelBooking";
        if (selectResource.equals("")) {
            Toast.makeText(CancelBooking.this, "Select resource for booking", Toast.LENGTH_LONG).show();
        } else if (selectDate.equals("")) {

            Toast.makeText(CancelBooking.this, "Select date", Toast.LENGTH_LONG).show();
        } else if (selectSession.equals("")) {

            Toast.makeText(CancelBooking.this, "Select session", Toast.LENGTH_LONG).show();
        } else {
            BackgroundTask backgroundTask=new BackgroundTask(){
                @Override
                public void onPostExecuteCallback (JSONObject json) {
                    try {
                        int success = json.getInt("success");
                        if (success == 0) {;
                            finish ();
                        } else {
                            Toast.makeText(CancelBooking.this, json.getString("message"), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {

                    }
                }
            };
            backgroundTask.execute(method, selectResource, selectDate, selectSession, userName);
        }
    }

    public void goBack(View view) {
        finish();
    }
}
