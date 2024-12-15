package com.crosska;

public class DataAnalyser {

    public String[] parseRequest(String request) {
        try {
            request = request.strip().replaceAll("[,.()!?]", "");
            return request.split(" ");
        } catch (NullPointerException e) {
            throw new NullPointerException();
        }
    }

    public  String parseReport(String request) {
        try {
            request = request.strip().replaceAll("[,.()!?]", "");
            return request;
        } catch (NullPointerException e) {
            throw new NullPointerException();
        }
    }

}
