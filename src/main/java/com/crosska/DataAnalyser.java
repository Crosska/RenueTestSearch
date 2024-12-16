package com.crosska;

public class DataAnalyser {

    public static String[] parseString(String request) {
        try {
            request = request.strip().replaceAll("[,.()!?;\\-*@#$%^&=\\[\\]{}_<>/`~]", "");
            request = request.replaceAll("([а-яА-ЯёЁa-zA-Z0-9]{2,})([а-яА-ЯёЁa-zA-Z0-9]{2})", "$1");
            return request.split(" ");
        } catch (NullPointerException e) {
            throw new NullPointerException();
        }
    }

}
