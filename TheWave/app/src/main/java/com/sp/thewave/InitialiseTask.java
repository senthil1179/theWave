package com.sp.thewave;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Raman Kannan on 7/14/2017.
 */

public class InitialiseTask extends AppCompatActivity {
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initialisetask_layout);
    }

    public void checkStatus(View view)
    {
        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
        Intent intent_checkStatus = new Intent(this, CheckStatus.class );
        intent_checkStatus.putExtra("userName", userName);
        startActivity(intent_checkStatus);
    }

    public void bookResource(View view)
    {
        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
        Intent intent_bookResource = new Intent(this, BookResource.class );
        intent_bookResource.putExtra("userName", userName);
        this.startActivity(intent_bookResource);
    }

    public void cancelBooking(View view)
    {
        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
        Intent intent_cancelBooking = new Intent(this, CancelBooking.class );
        intent_cancelBooking.putExtra("userName", userName);
        this.startActivity(intent_cancelBooking);
    }

    public void logOut(View view)
    {
        finish ();
    }
}
