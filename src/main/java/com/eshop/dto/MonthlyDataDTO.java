package com.eshop.dto;

import java.util.ArrayList;

public class MonthlyDataDTO {
    private ArrayList<String> labels;
    private ArrayList<Long> data;

    public MonthlyDataDTO() {
    }

    public MonthlyDataDTO(ArrayList<String> labels, ArrayList<Long> data) {
        this.labels = labels;
        this.data = data;
    }

    public ArrayList<String> getLabels() {
        return labels;
    }

    public void setLabels(ArrayList<String> labels) {
        this.labels = labels;
    }

    public ArrayList<Long> getData() {
        return data;
    }

    public void setData(ArrayList<Long> data) {
        this.data = data;
    }
}
