package com.b2.projectgroep.ti14_applicatie.CardClasses;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.b2.projectgroep.ti14_applicatie.AsyncTaskClasses.GetTableTask;
import com.b2.projectgroep.ti14_applicatie.AsyncTaskClasses.GetTableTaskListener;
import com.b2.projectgroep.ti14_applicatie.AsyncTaskClasses.TableTaskListener;
import com.b2.projectgroep.ti14_applicatie.R;

import java.util.ArrayList;

/**
 * Created by jeffrey on 9-6-2017.
 */

public class SelectCardActivity extends AppCompatActivity implements TableTaskListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_selecter);

        String phoneNumber = getIntent().getExtras().getString("phoneNumber");







    }


    @Override
    public void onSuccesMessage(String s) {

    }

    @Override
    public void onErrorMessage(String s) {

    }
}
