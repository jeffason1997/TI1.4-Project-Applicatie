package com.b2.projectgroep.ti14_applicatie.DiplomaClasses;

import com.b2.projectgroep.ti14_applicatie.R;
import com.b2.projectgroep.ti14_applicatie.RideClasses.PersonalActivity;

import java.util.ArrayList;

/**
 * Created by dionb on 15-6-2017.
 */

public class Achievement {
    int title;
    int progress;
    int maxProgress;
    int image;

    public Achievement(int title, int progress, int maxProgress, int image) {
        this.title = title;
        this.progress = progress;
        this.image = image;
        this.maxProgress = maxProgress;
    }



    public static ArrayList<Achievement> calculateAchievements(ArrayList<PersonalActivity> personalActivities) {
        int amountBlackMamba = 0;
        int amountSpirritRealm = 0;
        int amountFireMachine = 0;
        int amountDreamworld = 0;
        int countRide = 0;

        for(PersonalActivity pa : personalActivities) {
            if(pa.getRide().getName() == R.string.testRide_black_mamba_title) {
                amountBlackMamba++;
            } else if(pa.getRide().getName() == R.string.testRide_spirit_realm_title) {
                amountSpirritRealm++;
            } else if(pa.getRide().getName() == R.string.testRide_fire_machine_title) {
                amountFireMachine++;
            } else if(pa.getRide().getName() == R.string.testRide_dreamtown_title) {
                amountDreamworld++;
            }
        }

        if(amountBlackMamba > 0) {
            countRide++;
        }
        if(amountDreamworld > 0) {
            countRide++;
        }
        if(amountFireMachine > 0) {
            countRide++;
        }
        if(amountSpirritRealm > 0) {
            countRide++;
        }

        Achievement blackmambaez = new Achievement(R.string.achievement_blackmamba_ez, amountBlackMamba, 1, R.drawable.black_mamba);
        Achievement blackmamba = new Achievement(R.string.achievement_blackmamba, amountBlackMamba, 5, R.drawable.black_mamba);
        Achievement spiritrealmez = new Achievement(R.string.achievement_spirit_ez, amountSpirritRealm, 1, R.drawable.spirit_realm);
        Achievement spiritrealm = new Achievement(R.string.achievement_spirit, amountSpirritRealm, 5, R.drawable.spirit_realm);
        Achievement firemachineez = new Achievement(R.string.achievement_fire_ez, amountFireMachine, 1, R.drawable.fire_machine);
        Achievement firemachine = new Achievement(R.string.achievement_fire, amountFireMachine, 5, R.drawable.fire_machine);
        Achievement dreamworldez = new Achievement(R.string.achievement_dream_ez, amountDreamworld, 1, R.drawable.draaimolen_nostalgisch);
        Achievement dreamworld = new Achievement(R.string.achievement_dream, amountDreamworld, 5, R.drawable.draaimolen_nostalgisch);
        Achievement allrides = new Achievement(R.string.achievement_allrides, countRide, 4, R.drawable.logo_essteling);

        ArrayList<Achievement> toReturn = new ArrayList<>();
        toReturn.add(blackmambaez);
        toReturn.add(blackmamba);
        toReturn.add(spiritrealmez);
        toReturn.add(spiritrealm);
        toReturn.add(firemachineez);
        toReturn.add(firemachine);
        toReturn.add(dreamworldez);
        toReturn.add(dreamworld);
        toReturn.add(allrides);
        return toReturn;
    }

    public static ArrayList<Achievement> getCompletedAchievements(ArrayList<PersonalActivity> personalActivities) {
        ArrayList<Achievement> allAchievements = Achievement.calculateAchievements(personalActivities);
        ArrayList<Achievement> completed = new ArrayList<>();
        for(int x = 0; x < allAchievements.size(); x++) {
            if(allAchievements.get(x).progress >= allAchievements.get(x).maxProgress) {
                completed.add(allAchievements.get(x));
            }
        }
        return completed;
    }
}
