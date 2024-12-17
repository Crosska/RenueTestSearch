package com.crosska;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class DataAnalyser {

    private static final String[] exclusiveWords = {"для", "о", "об", "на", "и", "по", "c", "в", "или", "без", "до", "из", "к", "от", "перед", "при", "через", "у", "за", "над", "под", "про"};

    private static final String regex = "(ности|ского|ских|ость|ости|овых|ий|его|аях|ого|ему|ому|ную|ей|ой|ый|яя|ое|ее|им|ым|ем|ом|ых|ов|ах|ые|ию|ём|у|я)$";

    public static String[] parseString(String request) {
        try {
            request = request.strip().replaceAll("[,.()!?;\\-*@#$%^&=\\[\\]{}_<>/`~]", "");
            ArrayList<String> dirtyString = new ArrayList<>();
            String[] dirtyMass = request.split(" ");
            for (String word : dirtyMass) {
                if (!Arrays.asList(exclusiveWords).contains(word)) {
                    word = word.replaceAll(regex, "");
                    dirtyString.add(word);
                }
            }
            return dirtyString.toArray(String[]::new);
        } catch (NullPointerException e) {
            throw new NullPointerException();
        }
    }

}
