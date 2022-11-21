package com.eshop.test.dto;

import java.util.ArrayList;

public class MonthlyDataDTO {
    private ArrayList<String> labels;
    private ArrayList<Integer> data;

    public MonthlyDataDTO() {
    }

    public MonthlyDataDTO(ArrayList<String> labels, ArrayList<Integer> data) {
        this.labels = labels;
        this.data = data;
    }

    public ArrayList<String> getLabels() {
        return labels;
    }

    public void setLabels(ArrayList<String> labels) {
        this.labels = labels;
    }

    public ArrayList<Integer> getData() {
        return data;
    }

    public void setData(ArrayList<Integer> data) {
        this.data = data;
    }
}
