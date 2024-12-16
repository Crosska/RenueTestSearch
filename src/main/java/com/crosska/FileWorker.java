package com.crosska;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FileWorker {

    public LinkedHashMap<String, Report> readDataFile(String dataFile) {
        LinkedHashMap<String, Report> map = new LinkedHashMap<>();
        try (Scanner scanner = new Scanner(new File(dataFile))) {
            String line;
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                if (!line.isEmpty()) { // Пропускаем если строка пуста
                    String[] s = line.split("\\|"); // Делим столбцы, используя '|' как разделитель
                    for (int i = 0; i < s.length; i++) {
                        s[i] = s[i].strip(); // Убираем пробелы
                        Report report = new Report(s[2]);
                        map.put(s[0], report);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Ошибка. Файл с данными об отчетах не найден");
            throw new RuntimeException(e);
        }
        return map;
    }

    public LinkedList<String> readInputFile(String inputFile) {
        LinkedList<String> requestList;
        try (Scanner scanner = new Scanner(new File(inputFile))) {
            String line;
            requestList = new LinkedList<>();
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                if (!line.isEmpty()) { // Пропускаем если строка пуста
                    requestList.add(line);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Ошибка. Файл с данными об запросах не найден");
            throw new RuntimeException(e);
        }
        return requestList;
    }

}
