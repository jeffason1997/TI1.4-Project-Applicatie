package com.b2.projectgroep.ti14_applicatie.EmployeeClasses;

/**
 * Created by dionb on 9-6-2017.
 */

public class Name {
    String firstname;
    String lastname;
    public Name(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String toString() {
        return firstname + " " + lastname;
    }
}
