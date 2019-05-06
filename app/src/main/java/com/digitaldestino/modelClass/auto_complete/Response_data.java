package com.digitaldestino.modelClass.auto_complete;

import java.util.ArrayList;

public class Response_data {

    private ArrayList<AutoCompleteData> autoCompleteData;

    public ArrayList<AutoCompleteData> getAutoCompleteData() {
        return autoCompleteData;
    }

    public void setAutoCompleteData(ArrayList<AutoCompleteData> autoCompleteData) {
        this.autoCompleteData = autoCompleteData;
    }

    @Override
    public String toString() {
        return "ClassPojo [autoCompleteData = " + autoCompleteData + "]";
    }
}
