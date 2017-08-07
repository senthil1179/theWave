package com.sp.thewave;

        import android.content.Intent;
        import android.nfc.Tag;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.LinearLayoutCompat;
        import android.util.Log;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.CalendarView;
        import android.widget.EditText;
        import android.widget.Spinner;
        import android.widget.TextView;
        import android.widget.Toast;
        import android.widget.AdapterView.OnItemSelectedListener;

        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.ArrayList;
        import java.util.List;

        import static com.sp.thewave.R.id.parent;

/**
 * Created by Raman Kannan on 7/14/2017.
 */


public class BookResource extends AppCompatActivity implements OnItemSelectedListener {

    Button BT_calendar;
    Spinner SP_selectResource, SP_selectSession;
    String selectResource, selectSession, selectDate, userName;
    private CalendarView myCalendarView;
    private static final String TAG= "CalendarActivity";
   // EditText ET_Name;
  //  String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookresource_layout);
        //Intent intent = getIntent();
       // userName = intent.getStringExtra("userName").toString();
       // Toast.makeText(BookResource.this, "Inside BookResource"+userName, Toast.LENGTH_LONG).show();
       // setContentView(R.layout.activity_main);
      //  ET_Name=(EditText)findViewById(R.id.userName);
       // userName=ET_Name.getText().toString();

        //userName=usrName;
     //  Toast.makeText(BookResource.this, "Inside BookResource"+usrName, Toast.LENGTH_LONG).show();

        //Code for Date picker
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
                Toast.makeText(BookResource.this, "Inside calendar"+ selectDate, Toast.LENGTH_LONG).show();
                Log.d(TAG, "onSelectedDayChange: date "+selectDate);
            }
        });

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


    public void book(View view) {

//String userName = usrName;
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
            BackgroundTask backgroundTask=new BackgroundTask(){
                @Override
                public void onPostExecuteCallback (JSONObject json) {
                    try {
                        // json success tag
                        int success = json.getInt("success");
                        if (success == 1) {
                            Toast.makeText(BookResource.this, "Booking Successful", Toast.LENGTH_LONG).show();
                            Intent intent_initialiseTask = new Intent(BookResource.this, InitialiseTask.class );
                            BookResource.this.startActivity(intent_initialiseTask);
                        } else {
                            Toast.makeText(BookResource.this, "Booking Already available.", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {

                    }
                }
            };
            backgroundTask.execute(method, selectResource, selectDate, selectSession, userName);
        }


    }

    public void bookCancel(View view) {

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
        Toast.makeText(BookResource.this, "Cancellation in process...", Toast.LENGTH_LONG).show();
        //Toast.makeText(BookResource.this, "Cancellation in process...", Toast.LENGTH_LONG).show();
        //Toast.makeText(BookResource.this, "Cancellation in process...", Toast.LENGTH_LONG).show();
       // Toast.makeText(BookResource.this, "Cancellation in process...", Toast.LENGTH_LONG).show();
            Intent intent_initialiseTask = new Intent(this, InitialiseTask.class );
            this.startActivity(intent_initialiseTask);
        }



}
