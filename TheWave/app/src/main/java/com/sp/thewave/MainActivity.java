package com.sp.thewave;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    EditText ET_Name, ET_Pass;
    String userName, userPass;

    private static final String TAG_SUCCESS = "success";

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
        BackgroundTask backgroundTask=new BackgroundTask(){
            @Override
            public void onPostExecuteCallback (JSONObject json) {
                try {
                    // json success tag
                    int success = json.getInt("success");
                    if (success == 1) {
                        Intent intent_initialiseTask = new Intent(MainActivity.this, InitialiseTask.class );
                        intent_initialiseTask.putExtra("userName", userName);
                        MainActivity.this.startActivity(intent_initialiseTask);
                    } else {
                        Toast.makeText(MainActivity.this, "Login Failed.", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {

                }
            }
        };
        backgroundTask.execute(method, userName, userPass);
    }
}
