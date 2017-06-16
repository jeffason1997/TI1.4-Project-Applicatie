package com.b2.projectgroep.ti14_applicatie.EmployeeClasses;

/**
 * Created by tmbro on 16-6-2017.
 */

public class Employee_Read_Info {
    static String parentName,parentSur,phoneNumber,childName,childSur,cardNumber = null;

    public static String getParentName() {
        return parentName;
    }

    public static void setParentName(String parentName) {
        Employee_Read_Info.parentName = parentName;
    }

    public static String getParentSur() {
        return parentSur;
    }

    public static void setParentSur(String parentSur) {
        Employee_Read_Info.parentSur = parentSur;
    }

    public static String getPhoneNumber() {
        return phoneNumber;
    }

    public static void setPhoneNumber(String phoneNumber) {
        Employee_Read_Info.phoneNumber = phoneNumber;
    }

    public static String getChildName() {
        return childName;
    }

    public static void setChildName(String childName) {
        Employee_Read_Info.childName = childName;
    }

    public static String getChildSur() {
        return childSur;
    }

    public static void setChildSur(String childSur) {
        Employee_Read_Info.childSur = childSur;
    }

    public static String getCardNumber() {
        return cardNumber;
    }

    public static void setCardNumber(String cardNumber) {
        Employee_Read_Info.cardNumber = cardNumber;
    }
}
