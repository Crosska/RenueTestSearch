package com.crosska;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FileWorker {

    public HashMap<String, String> readFile(String dataFile) {
        HashMap<String, String> map = new HashMap<>();
        try (Scanner scanner = new Scanner(new File(dataFile));) {
            String line;
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                if(!line.isEmpty()) { // Пропускаем если строка пуста
                    String[] s = line.split("\\|"); // Делим столбцы, используя '|' как разделитель
                    for (int i = 0; i < s.length; i++) {
                        s[i] = s[i].strip(); // Убираем пробелы
                        map.put(s[0],s[2]);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Ошибка. Файл с данными об отчетах не найден");
            throw new RuntimeException(e);
        }
        return map;
    }

}
