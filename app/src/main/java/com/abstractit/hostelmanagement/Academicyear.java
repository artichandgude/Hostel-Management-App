package com.abstractit.hostelmanagement;

public enum Academicyear {

    Year("Select Year"),
    FIRST_YEAR("I Year"),
    SECOND_YEAR("II Year"),
    THIRD_YEAR("III Year"),
    FOURTH_YEAR("IV Year"),
    FIFTH_YEAR("V Year"),
    SIXTH_YEAR("VI Year"),

    Month("Jan"),
    Month2("Feb"),
    Month3("Mar"),
    Month4("Apr"),
    Month5("May"),
    Month6("Jun"),
    Month7("July"),
    Month8("Aug"),
    Month9("Sep"),
    Month10("Oct"),
    Month11("Nov"),
    Month12("Dec");







    private String stringValue;

    Academicyear(String toString) {
        stringValue = toString;
    }

    @Override
    public String toString() {
        return stringValue;
    }


}
