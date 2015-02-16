package com.persistent.esansad;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.persistent.esansad.helper.APIHelper;


public class AadharActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aadhar);
        new IntentIntegrator(this).initiateScan();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Log.w("QR" , "requestCode = " + requestCode + " resultCode " + resultCode);
        if (requestCode >= 0) {
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");


                Intent loginIntent = getIntent();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent mainActivity = new Intent(this,MainActivity.class);

                if(loginIntent.getStringExtra("name")!=null)
                    mainActivity.putExtra("name",loginIntent.getStringExtra("name"));
                if(loginIntent.getStringExtra("email")!=null)
                     mainActivity.putExtra("email",loginIntent.getStringExtra("email"));
                if(loginIntent.getStringExtra("profileImage")!=null)
                    mainActivity.putExtra("profileImage",loginIntent.getStringExtra("profileImage"));

                startActivity(mainActivity);

              //  APIHelper.postAadharData(this,contents,loginIntent);


//                Toast toast = Toast.makeText(this, "Content:" + contents + " Format:" + format, Toast.LENGTH_LONG);
//                toast.show();
//                TextView a = (TextView) findViewById(R.id.aadhar);
//                a.setText(contents);
                Toast.makeText(this,"Aadhar details Recorded..",Toast.LENGTH_LONG).show();


            }
        }
        finish();
    }
}
