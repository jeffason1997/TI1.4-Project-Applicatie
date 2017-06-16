package com.b2.projectgroep.ti14_applicatie.DiplomaClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.b2.projectgroep.ti14_applicatie.R;
import com.b2.projectgroep.ti14_applicatie.RideClasses.PersonalActivity;
import com.b2.projectgroep.ti14_applicatie.RideClasses.Ride;

import java.util.ArrayList;

/**
 * Created by dionb on 15-6-2017.
 */

public class AchievementAdapter extends ArrayAdapter<Achievement> {

    ArrayList<Achievement> achievements;

    public AchievementAdapter(Context context, ArrayList<Achievement> achievements) {
        super(context, 0, achievements);
        this.achievements = achievements;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Achievement achievement = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.lv_row_achievement, parent, false);
        }

        ImageView image = (ImageView) convertView.findViewById(R.id.achievement_row_image);
        image.setImageDrawable(convertView.getResources().getDrawable(achievement.image));

        TextView discription = (TextView) convertView.findViewById(R.id.achievement_row_discription);
        discription.setText(achievement.title);

        ProgressBar progressBar = (ProgressBar) convertView.findViewById(R.id.achievement_row_progressbar);
        progressBar.setClickable(false);
        progressBar.setMax(achievement.maxProgress);
        progressBar.setProgress(achievement.progress);

        TextView percentage = (TextView) convertView.findViewById(R.id.achievement_row_percentage);
        if(((double)(achievement.progress) / (double)(achievement.maxProgress)) > 1) {
            percentage.setText("100%");
        } else {
            percentage.setText((double)(achievement.progress) / (double)(achievement.maxProgress) * 100 + "%");
        }

        return convertView;
    }
}
