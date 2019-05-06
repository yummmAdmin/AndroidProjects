package com.digitaldestino.modelClass.book_table;

import java.util.ArrayList;

public class Response_data
{
    private ArrayList<TableData>tableData;

    public ArrayList<TableData> getTableData() {
        return tableData;
    }

    public void setTableData(ArrayList<TableData> tableData) {
        this.tableData = tableData;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [tableData = "+tableData+"]";
    }
}