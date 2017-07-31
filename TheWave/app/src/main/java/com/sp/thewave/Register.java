package com.sp.thewave;

        import android.app.Activity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.EditText;

/**
 * Created by Raman Kannan on 7/6/2017.
 */

public class Register extends Activity {
    EditText ET_name, ET_nric, ET_userName, ET_userPass, ET_phoneNumber, ET_email;
    String name, nric, userName, userPass, phoneNumber, email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        ET_name = (EditText)findViewById(R.id.ET_name);
        ET_nric = (EditText)findViewById(R.id.ET_nric);
        ET_userName = (EditText)findViewById(R.id.ET_userName);
        ET_userPass = (EditText)findViewById(R.id.ET_userPass);
        ET_phoneNumber = (EditText)findViewById(R.id.ET_phoneNumber);
        ET_email = (EditText)findViewById(R.id.ET_email);
    }
    public void userRegister(View view)
    {

        name=ET_name.getText().toString();
        nric=ET_nric.getText().toString();
        userName=ET_userName.getText().toString();
        userPass=ET_userPass.getText().toString();
        phoneNumber=ET_phoneNumber.getText().toString();
        email=ET_email.getText().toString();
        String method = "register";
        BackgroundTask backgroundTask=new BackgroundTask(this);
        backgroundTask.execute(method, name, nric, userName, userPass, phoneNumber, email);
        finish();
    }
}

