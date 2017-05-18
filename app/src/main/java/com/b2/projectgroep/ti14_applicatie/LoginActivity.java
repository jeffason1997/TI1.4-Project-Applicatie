package com.b2.projectgroep.ti14_applicatie;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    TextView tvHead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/arial-black.ttf");
        tvHead = (TextView) findViewById(R.id.textView_welkom);
        tvHead.setTypeface(type);
    }
}
