package com.b2.projectgroep.ti14_applicatie.CardClasses;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.b2.projectgroep.ti14_applicatie.R;
import com.b2.projectgroep.ti14_applicatie.RideClasses.PersonalActivity;

/**
 * Created by jeffrey on 9-6-2017.
 */

public class CardListAdapter extends ArrayAdapter<SelectCardActivity> {
    public CardListAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        SelectCardActivity SelectCardActivity = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.lv_row_cards, parent, false);
        }

        return convertView;
    }
}
