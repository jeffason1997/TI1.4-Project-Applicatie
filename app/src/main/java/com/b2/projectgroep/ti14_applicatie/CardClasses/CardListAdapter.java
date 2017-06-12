package com.b2.projectgroep.ti14_applicatie.CardClasses;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.b2.projectgroep.ti14_applicatie.R;

import java.util.ArrayList;

/**
 * Created by jeffrey on 9-6-2017.
 */

public class CardListAdapter extends ArrayAdapter<Card> {

    public CardListAdapter(@NonNull Context context,  ArrayList<Card> cards) {
        super(context,0,cards);
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        Card card = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.lv_row_cards, parent, false);
        }

        TextView nameKid = (TextView) convertView.findViewById(R.id.card_child_name_id);
        nameKid.setText(card.getName());

        TextView surnameKid = (TextView) convertView.findViewById(R.id.card_child_surname_id);
        surnameKid.setText(card.getSurName());

        TextView cardId = (TextView) convertView.findViewById(R.id.card_cardNumber_id);
        cardId.setText(card.getId());

        ImageView view = (ImageView) convertView.findViewById(R.id.imageView3);
        view.setImageDrawable(convertView.getResources().getDrawable(R.drawable.logo_essteling));

        return convertView;
    }
}
