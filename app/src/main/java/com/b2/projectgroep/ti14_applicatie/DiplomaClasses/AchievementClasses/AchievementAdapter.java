package com.b2.projectgroep.ti14_applicatie.DiplomaClasses.AchievementClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.b2.projectgroep.ti14_applicatie.DiplomaClasses.AchievementClasses.Achievement;
import com.b2.projectgroep.ti14_applicatie.R;

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
        image.setImageDrawable(convertView.getResources().getDrawable(achievement.getImage()));

        TextView discription = (TextView) convertView.findViewById(R.id.achievement_row_discription);
        discription.setText(achievement.getTitle());

        ProgressBar progressBar = (ProgressBar) convertView.findViewById(R.id.achievement_row_progressbar);
        progressBar.setClickable(false);
        progressBar.setMax(achievement.getMaxProgress());
        progressBar.setProgress(achievement.getProgress());

        TextView percentage = (TextView) convertView.findViewById(R.id.achievement_row_percentage);
        if(((double)(achievement.getProgress()) / (double)(achievement.getMaxProgress())) >= 1) {
            percentage.setText("100%");
        } else {
            percentage.setText( (int) ((double)(achievement.getProgress()) / (double)(achievement.getMaxProgress()) * 100)  + "%");
        }

        return convertView;
    }
}
