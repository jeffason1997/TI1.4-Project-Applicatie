package com.b2.projectgroep.ti14_applicatie.EmployeeClasses;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.b2.projectgroep.ti14_applicatie.R;
import com.b2.projectgroep.ti14_applicatie.RideClasses.PersonalActivity;

import java.util.ArrayList;

/**
 * Created by dionb on 9-6-2017.
 */

public class RequestListAdapter extends ArrayAdapter {
    public RequestListAdapter(Context context, ArrayList<Request> requests) {
        super(context, 0, requests);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.request_list_row, parent, false);
        }

        TextView parentText = (TextView) convertView.findViewById(R.id.request_list_Parentname);
        parentText.setText(((Request)getItem(position)).getParent().toString());

        TextView phone = (TextView) convertView.findViewById(R.id.request_list_phonenumber);
        phone.setText(((Request)getItem(position)).getPhoneNumber());

        TextView childname = (TextView) convertView.findViewById(R.id.request_list_childname);
        childname.setText(((Request) getItem(position)).getChild().toString());

        return convertView;
    }
}
