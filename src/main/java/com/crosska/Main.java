package com.crosska;

import org.apache.commons.cli.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();

        Options options = new Options();
        options.addOption("d", "data", true, "Path to the .csv text data file (including filename and extension)");
        options.addOption("i", "input-file", true, "Path to the text file with search lines (including filename and extension)");
        options.addOption("o", "output-file", true, "Path to the JSON file with results data (including filename and extension)");

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("data") && cmd.hasOption("input-file") && cmd.hasOption("output-file")) {
                startSearchProcess(cmd.getOptionValue("data"), cmd.getOptionValue("input-file"), cmd.getOptionValue("output-file"), startTime);
            } else {
                System.err.println("Ошибка. Необходимые параметры для запуска:\ndata (d) $путь\ninput-file (i) $путь\noutput-file (o) $путь");
            }
        } catch (ParseException e) {
            e.printStackTrace();
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("search.jar", options);
        }

    }

    private static void startSearchProcess(String dataPath, String inputPath, String outputPath, long startTime) {
        FileWorker fileWorker = new FileWorker(); // Обьект работы с файлами
        DataAnalyser dataAnalyser = new DataAnalyser(); // Обьект для анализа данных
        List<ResultElem> resArray = new ArrayList<>(); // Список обьектов для JSON файла

        LinkedList<String> requestList = fileWorker.readInputFile(inputPath); // Считываем данные о запросах в список

        long initTime = System.currentTimeMillis() - startTime;

        LinkedHashMap<String, Report> dataMap = fileWorker.readDataFile(dataPath); // Считываем данные об отчетах в мапу


        for (String currentRequest : requestList) { // Цикл по запросам в списке запросов
            long searchStartTime = System.currentTimeMillis();

            String[] parsedRequest = dataAnalyser.parseRequest(currentRequest);

            for (Map.Entry<String, Report> entry : dataMap.entrySet()) {
                entry.getValue().resetPrecision();
            }

            for (String seekWord : parsedRequest) { // Цикл по словам в разобранном запросе
                System.out.println("Ищем \"" + seekWord + "\" в отчетах");

                for (Map.Entry<String, Report> entry : dataMap.entrySet()) {
                    Pattern pattern = Pattern.compile("\\b\\w*" + seekWord + "\\w*\\b");
                    Matcher matcher = pattern.matcher(dataAnalyser.parseReport(entry.getValue().getReportName()));

                    if (matcher.find()) {
                        System.out.println(entry.getValue().getReportName() + " содержит " + seekWord);
                        entry.getValue().increasePrecision();
                    }

                }

                System.out.println("\n");
            }

            ArrayList<String> GUIDList = new ArrayList<>();
            int max = 0;
            for (Map.Entry<String, Report> entry : dataMap.entrySet()) {
                if (entry.getValue().getQueryPrecision() > max) {
                    GUIDList.clear();
                    GUIDList.add(entry.getKey());
                } else if (entry.getValue().getQueryPrecision() == max && entry.getValue().getQueryPrecision() != 0) {
                    GUIDList.add(entry.getKey());
                }
            }

            long searchTime = System.currentTimeMillis() - searchStartTime;

            resArray.add(new ResultElem(currentRequest, GUIDList.toArray(String[]::new), searchTime));
        }

        JSONObject jsonObject = new JSONObject(initTime, outputPath);
        for (ResultElem resultElem : resArray) {
            jsonObject.addNewResult(resultElem);
        }
        JSONWriter jsonWriter = new JSONWriter();
        jsonWriter.writeResult(jsonObject);
    }
}