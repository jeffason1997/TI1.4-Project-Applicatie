package com.b2.projectgroep.ti14_applicatie.AsyncTaskClasses;

import com.b2.projectgroep.ti14_applicatie.EmployeeClasses.Request;

/**
 * Created by dionb on 9-6-2017.
 */

public interface GetRequestedCardsListener {
    void onRequestAvailable(Request request);

    void onErrorMessage(String s);
}
