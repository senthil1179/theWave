package com.sp.thewave;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.Toast;

/**
 * Created by Raman Kannan on 7/15/2017.
 */


public class CancelCalenderActivity extends AppCompatActivity {

    private static final String TAG= "CancelCalenderActivity";
    private CalendarView myCalendarView;
    String date;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_layout);
        myCalendarView = (CalendarView) findViewById(R.id.calendarView);
        myCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2){
                if(i1<=9){
                    date=i+"-0"+i1+"-"+i2;

                }else{
                    date=i+"-"+i1+"-"+i2;
                }
                Toast.makeText(CancelCalenderActivity.this, "Inside calendar"+ date, Toast.LENGTH_LONG).show();
                Log.d(TAG, "onSelectedDayChange: date "+date);

                Intent intent = new Intent(CancelCalenderActivity.this, CancelBooking.class);
                intent.putExtra("Date", date);
                startActivity(intent);

            }
        });
    }
}
