package com.b2.projectgroep.ti14_applicatie.AsyncTaskClasses;

import com.b2.projectgroep.ti14_applicatie.RideClasses.PersonalActivity;

/**
 * Created by dionb on 1-6-2017.
 */

public interface GetTableTaskListener {
    void onRideAvailable(PersonalActivity activity);

    void onErrorMessage(String s);
}
