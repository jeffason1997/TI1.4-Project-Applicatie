package com.b2.projectgroep.ti14_applicatie.AsyncTaskClasses;

import com.b2.projectgroep.ti14_applicatie.CardClasses.Card;

/**
 * Created by Jeffrey on 12-6-2017.
 */

public interface GetCardsTask {
    void onCardAvailable(Card c);

    void onErrorMessage(String message);
}
