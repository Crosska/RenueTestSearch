package com.crosska;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class DataAnalyser {

    private static final String[] exclusiveWords = {"для", "о", "об", "на", "и", "по", "c", "в", "или", "без", "до", "из", "к", "от", "перед", "при", "через", "у", "за", "над", "под", "про"};

    private static final String[] wordAdjectivalEndings = {"ий", "ый", "ая", "яя", "ое", "ее", "его", "ого", "ей", "ой", "ему", "ому",
                                                "им", "ым", "ем", "ом"};

    private static final String[] wordNounEndings = {"а", "я", "о", "е", "ы", "и", "е", "у", "ю", "ой", "ей", "ом", "ем", "ём"};

    public static String[] parseString(String request) {
        try {
            System.out.println("\n" + request);
            request = request.strip().replaceAll("[,.()!?;\\-*@#$%^&=\\[\\]{}_<>/`~]", "");
            request = request.replaceAll("([а-яА-ЯёЁa-zA-Z0-9]{4,})([а-яА-ЯёЁa-zA-Z0-9]{3})", "$1");
            ArrayList<String> dirtyString = new ArrayList<>();
            String[] dirtyMass = request.split(" ");
            for (String word : dirtyMass) {
                System.out.println(word);
                if (!Arrays.asList(exclusiveWords).contains(word)) {
                    dirtyString.add(word);
                }
            }
            return dirtyString.toArray(String[]::new);
        } catch (NullPointerException e) {
            throw new NullPointerException();
        }
    }

}
