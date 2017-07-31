package com.sp.thewave;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText ET_Name, ET_Pass;
    String userName, userPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ET_Name=(EditText)findViewById(R.id.userName);
        ET_Pass=(EditText)findViewById(R.id.userPass);
    }
    public void userRegister(View view)
    {
        startActivity(new Intent(this, Register.class));

    }
    public void userLogin(View view)
    {

        userName=ET_Name.getText().toString();
        userPass=ET_Pass.getText().toString();
        String method ="login";
        BackgroundTask backgroundTask=new BackgroundTask((this));
        backgroundTask.execute(method, userName, userPass);

        Intent intent_initialiseTask = new Intent(this, InitialiseTask.class );
       // intent_initialiseTask.putExtra("userName", userName);
        this.startActivity(intent_initialiseTask);


       /* Intent intent_bookResource = new Intent(this, BookResource.class);
        intent_bookResource.putExtra("userName", userName);
        //myIntent.putExtra("lastName", "Your Last Name Here");
        startActivity(intent_bookResource);*/
    }
}
