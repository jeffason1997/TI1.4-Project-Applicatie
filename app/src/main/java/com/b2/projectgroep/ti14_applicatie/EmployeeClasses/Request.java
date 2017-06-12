package com.b2.projectgroep.ti14_applicatie.EmployeeClasses;

import java.util.ArrayList;

/**
 * Created by dionb on 9-6-2017.
 */

public class Request {
    Name parent;
    String phoneNumber;
    Name child;

    public Request(Name parent, String phoneNumber, Name child) {
        this.parent = parent;
        this.phoneNumber = phoneNumber;
        this.child = child;
    }

    public Name getParent() {
        return parent;
    }

    public void setParent(Name parent) {
        this.parent = parent;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Name getChild() {
        return child;
    }

    public void setChild(Name child) {
        this.child = child;
    }
}
